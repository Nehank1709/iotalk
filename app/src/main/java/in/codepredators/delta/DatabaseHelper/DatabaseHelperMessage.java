package in.codepredators.delta.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.codepredators.delta.Classes.Message;

public class DatabaseHelperMessage extends SQLiteOpenHelper {

    private static final String dbName="IOTalkMessage";
    public static final String MessagesTable="Messages";
    public String colDatabaseMessageTime = "MessageTime";
    public String colDatabaseMessageText = "MessageText";
    public String colDatabaseMessageType = "MessageType";
    public String colDatabaseMessagesenderUID = "MessagesenderUID";
    public String colDatabaseMessageFileLocalAddress = "MessageFile";//store address of file
    public String colDatabaseMessageFileFirebaseReference = "MessageFirebaseFile";//to store firebase reference
    public String colDatabaseMessageImageLocalAddress = "MessageImage";//store address of Image
    public String colDatabaseMessageImageFirebaseReference = "MessageFirebaseImage";
    public String colDatabaseMessageID = "MessageID";
    public String colDatabaseMessageStatus = "MessageStatus";
    public String colDatabaseMessageStarred = "MessageStarred";
    public String colDatabaseMessagePGID = "MessagePGID";
    public String colDatabaseMessageContact = "MessageContact";


    public DatabaseHelperMessage(Context context)
    {
        super(context,dbName,null,33);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists " + MessagesTable + "(" + colDatabaseMessageID + " String, " + colDatabaseMessageType + " String, "
                + colDatabaseMessageTime + " String, " + colDatabaseMessagesenderUID + " String, " + colDatabaseMessageText + " String, " +
                colDatabaseMessageImageFirebaseReference + " String, " + colDatabaseMessageFileFirebaseReference + " String, " +
                colDatabaseMessageFileLocalAddress + " String, " + colDatabaseMessageImageLocalAddress + " String, " + colDatabaseMessageContact + " String, " +
                colDatabaseMessagePGID + " String, " + colDatabaseMessageStarred + " String, " + colDatabaseMessageStatus + " String " + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void updateMessage(Message SqlMessage , String StarredMessage , String StatusMessage , String ContactMessage , String PGID) {
        String MID= SqlMessage.getMID();
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colDatabaseMessageText,SqlMessage.getMessageText());
        values.put(colDatabaseMessageID, SqlMessage.getMID());
        values.put(colDatabaseMessageTime,SqlMessage.getMessageTime());
        String Type = "T" + SqlMessage.getMessageType();
        values.put(colDatabaseMessageType,Type);
        values.put(colDatabaseMessageText,SqlMessage.getMessageText());
        values.put(colDatabaseMessagesenderUID, SqlMessage.getMessagesenderUID());
        values.put(colDatabaseMessageFileLocalAddress, SqlMessage.getMessageFileLocalAddress());
        values.put(colDatabaseMessageFileFirebaseReference,SqlMessage.getMessageFile());
        values.put(colDatabaseMessageImageFirebaseReference,SqlMessage.getMessageImage());
        values.put(colDatabaseMessageImageLocalAddress,SqlMessage.getMessageImageLocalAddress());
        values.put(colDatabaseMessageContact,ContactMessage);
        values.put(colDatabaseMessagePGID,PGID);
        values.put(colDatabaseMessageStarred,StarredMessage);
        values.put(colDatabaseMessageStatus,StatusMessage);
        DB.update(DatabaseHelperMessage.MessagesTable, values,
                "MessageID" + " =?" , new String[]{MID});
    }
    public long insertMessage(Message SqlMessage , String StarredMessage , String StatusMessage , String ContactMessage , String PGID)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colDatabaseMessageID, SqlMessage.getMID());
        values.put(colDatabaseMessageTime,SqlMessage.getMessageTime());
        String Type = "T" + SqlMessage.getMessageType();
        values.put(colDatabaseMessageType,Type);
        values.put(colDatabaseMessageText,SqlMessage.getMessageText());
        values.put(colDatabaseMessagesenderUID, SqlMessage.getMessagesenderUID());
        values.put(colDatabaseMessageFileLocalAddress, SqlMessage.getMessageFileLocalAddress());
        values.put(colDatabaseMessageFileFirebaseReference,SqlMessage.getMessageFile());
        values.put(colDatabaseMessageImageFirebaseReference,SqlMessage.getMessageImage());
        values.put(colDatabaseMessageImageLocalAddress,SqlMessage.getMessageImageLocalAddress());
        values.put(colDatabaseMessageContact,ContactMessage);
        values.put(colDatabaseMessagePGID,PGID);
        values.put(colDatabaseMessageStarred,StarredMessage);
        values.put(colDatabaseMessageStatus,StatusMessage);
        long ID = DB.insert(MessagesTable, null, values);
        DB.close();
        return ID;
    }

    public Message getMessageByMID(String MID){
        Message message = new Message();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor =  DB.rawQuery( "select * from UserTable where id=?"+ MID +"", null );
        if(cursor.moveToFirst()){
            do {
                message.setMessageFileLocalAddress(cursor.getString(cursor.getColumnIndex(colDatabaseMessageFileLocalAddress)));
                //  Log.i("DatabaseFileAddress",message.getMessageFileLocalAddress());
                message.setMessageImageLocalAddress(cursor.getString(cursor.getColumnIndex(colDatabaseMessageImageLocalAddress)));
                //  Log.i("Databsase",message.getMessageImageLocalAddress());
                message.setMessagesenderUID(cursor.getString(cursor.getColumnIndex(colDatabaseMessagesenderUID)));
                message.setMessageStarredStatus(cursor.getString(cursor.getColumnIndex(colDatabaseMessageStarred)));
                message.setMessageStatus(cursor.getString(cursor.getColumnIndex(colDatabaseMessageStatus)));
                message.setMessageText(cursor.getString(cursor.getColumnIndex(colDatabaseMessageText)));
                message.setMessageTime(cursor.getString(cursor.getColumnIndex(colDatabaseMessageTime)));
                message.setMessageFile(cursor.getString(cursor.getColumnIndex(colDatabaseMessageFileFirebaseReference)));
                message.setMessageImage(cursor.getString(cursor.getColumnIndex(colDatabaseMessageImageFirebaseReference)));
                String Type = cursor.getString(cursor.getColumnIndex(colDatabaseMessageType));
                message.setMessageType(Type.substring(1));
                message.setMID(cursor.getString(cursor.getColumnIndex(colDatabaseMessageID)));
                HashMap<String, String> contactHashmap = new HashMap<>();
                if (message.getMessageType().charAt(2) == '1') {
                    String contact = cursor.getString(cursor.getColumnIndex(colDatabaseMessageContact)).substring(4);//null is saved in starting position after it is improved u have to remove this
                    String[] splited = contact.split("~");
                    //create a hashmap
                    for (int i = 0; i < splited.length - 1; i++) {
                        contactHashmap.put(splited[i], splited[i + 1]);
                        i++;
                    }
                    message.setMessageContact(contactHashmap);
                }
            }while (cursor.moveToNext());
        }
        DB.close();
        cursor.close();
        return message;
    }



    public void deleteDownload(Message message, int a)
    {
        Log.i("clearChat","entered");
        SQLiteDatabase DB = this.getWritableDatabase();
        if(a==1 && (message.getMessageType().charAt(1) == '1' || message.getMessageType().charAt(3) == '1'))
        {
            Log.i("clearChat","withmedia");
//            String WHERE = "MessageID"+ "=?";
//            Cursor cursor = DB.query(DatabaseHelperMessage.MessagesTable, null, WHERE, new String[]{message.getMID()}, null, null, null);
            File file;
            if(message.getMessageType().charAt(1) == '1') {
                file = new File(message.getMessageImageLocalAddress(), message.getMessageImage());
            }
            else {
                file = new File(message.getMessageFileLocalAddress(), message.getMessageFile());
            }if(file.exists()) {
            boolean ans = file.delete();
            Log.i("clearChat", String.valueOf(ans));
        }
        }
        DB.delete(MessagesTable, colDatabaseMessageID + " = ?",
                new String[]{String.valueOf(message.getMID())});

        DB.close();
    }
    public List<Message> getAllMessages(String type) {

        List<Message> messages = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + MessagesTable + " ORDER BY " +
                colDatabaseMessageTime + " DESC";

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do
            {
                if(type.equals("all")   ||  (type.equals("starred") && cursor.getString(cursor.getColumnIndex(colDatabaseMessageStarred)).equals("true"))   ) {
                    Message message = new Message();
                    message.setMessageFileLocalAddress(cursor.getString(cursor.getColumnIndex(colDatabaseMessageFileLocalAddress)));
                    //  Log.i("DatabaseFileAddress",message.getMessageFileLocalAddress());
                    message.setMessageImageLocalAddress(cursor.getString(cursor.getColumnIndex(colDatabaseMessageImageLocalAddress)));
                    //  Log.i("Databsase",message.getMessageImageLocalAddress());
                    message.setMessagesenderUID(cursor.getString(cursor.getColumnIndex(colDatabaseMessagesenderUID)));
                    message.setMessageStarredStatus(cursor.getString(cursor.getColumnIndex(colDatabaseMessageStarred)));
                    message.setMessageStatus(cursor.getString(cursor.getColumnIndex(colDatabaseMessageStatus)));
                    message.setMessageText(cursor.getString(cursor.getColumnIndex(colDatabaseMessageText)));
                    message.setMessageTime(cursor.getString(cursor.getColumnIndex(colDatabaseMessageTime)));
                    message.setMessageFile(cursor.getString(cursor.getColumnIndex(colDatabaseMessageFileFirebaseReference)));
                    message.setMessageImage(cursor.getString(cursor.getColumnIndex(colDatabaseMessageImageFirebaseReference)));
                    String Type = cursor.getString(cursor.getColumnIndex(colDatabaseMessageType));
                    message.setMessageType(Type.substring(1));
                    message.setMID(cursor.getString(cursor.getColumnIndex(colDatabaseMessageID)));
                    HashMap<String, String> contactHashmap = new HashMap<>();
                    if (message.getMessageType().charAt(2) == '1') {
                        String contact = cursor.getString(cursor.getColumnIndex(colDatabaseMessageContact)).substring(4);//null is saved in starting position after it is improved u have to remove this
                        String[] splited = contact.split("~");
                        //create a hashmap
                        for (int i = 0; i < splited.length - 1; i++) {
                            contactHashmap.put(splited[i], splited[i + 1]);
                            i++;
                        }
                        message.setMessageContact(contactHashmap);
                    }
                    messages.add(message);
                }
            }
            while (cursor.moveToNext());
        }

        DB.close();

        cursor.close();
        return messages;
    }
    public void updateMessageImageAddress(String MID, String address) {
        Log.i("Databaseupdate",MID + address);
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(colDatabaseMessageImageLocalAddress,address);
        DB.update(DatabaseHelperMessage.MessagesTable, cvUpdate,
                "MessageID" + " =?" , new String[]{MID});
    }
    public void updateMessageFileAddress(String MID, String address)
    {
        Log.i("underupdate",MID + address);
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(colDatabaseMessageFileLocalAddress,address);
        DB.update(DatabaseHelperMessage.MessagesTable, cvUpdate,
                "MessageID" + " =?" , new String[]{MID});
    }
    public void updateMessageStarredstatus(String MID, String Status) //status - true or false
    {
        Log.i("starresstatuschanging",MID + " " + Status);
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(colDatabaseMessageStarred,Status);
        DB.update(DatabaseHelperMessage.MessagesTable, cvUpdate,
                "MessageID" + " =?" , new String[]{MID});
    }
}
