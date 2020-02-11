package in.codepredators.delta.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.IDNA;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.codepredators.delta.Activities.ChatList;
import in.codepredators.delta.Classes.Message;
import in.codepredators.delta.Classes.PersonalMessage;
import in.codepredators.delta.Classes.User;

public class DatabaseHelperPersonDetail extends SQLiteOpenHelper {
    private static final String dbName="IOTalkPersonDetail";
    private static final String UserTable="UserTable";
    private static final String PersonalChatTable = "PersonalTable";
    private static final String GroupChatTable = "GroupTable";
    private String colDatabaseID = "ID";
    private String colDatabaseParticipant = "Participant";//UID of users in group and personal
    private String colDatabaseName = "Name";
    private String colDatabaseDescription = "Discription";
    private String colDatabaseCountry = "Country";
    private String colDatabaseTime_Creator = "Time_Creator";//creating time and creator id
    private String colDatabaseImage = "Image";//User and image - image //Personal - id of opposite user
    private String colDatabaseLanguage = "Language";
    private String colDatabaseArchievePinChat = "ArchievePinChat";
    private String colDatabaseAdmin = "Admin";
    private String colDatabaseUnseenMessage = "UnseenMessage";
    private String colDatabaseLastMessageId = "LastMessageId";

    //u r not saving phone number but u have to do that


    public DatabaseHelperPersonDetail(Context context)
    {
        super(context,dbName,null,33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists " + UserTable + "(" + colDatabaseID + " String, " + colDatabaseCountry + " String, "
                + colDatabaseName + " String, " + colDatabaseLanguage + " String, " +
                colDatabaseImage + " String, " + colDatabaseDescription + " String " + ");");
        db.execSQL("Create table if not exists " + PersonalChatTable + "(" + colDatabaseID + " String, "
                + colDatabaseArchievePinChat + " String, " + colDatabaseTime_Creator + " String, "
                + colDatabaseParticipant  + " String, " + colDatabaseUnseenMessage + " String, " + colDatabaseLastMessageId + " String " + ");");
        db.execSQL("Create table if not exists " + GroupChatTable + "(" + colDatabaseID + " String, " + colDatabaseAdmin + " String, "
                + colDatabaseArchievePinChat + " String, " + colDatabaseTime_Creator + " String, "
                + colDatabaseParticipant  + " String, " + colDatabaseImage + " String, " + colDatabaseDescription + " String, "
                + colDatabaseUnseenMessage + "String, " + colDatabaseLastMessageId + "String " + ");");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
//about personal chat icon image
    // in chat list the image will come from database
    // when user tap on image than it will download from firebase and it will update database image
    //and same work will be done when personal chat is open by user

    public long insertUserDetail(User user, String Image, String language)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colDatabaseID,user.getUID());
        values.put(colDatabaseName,user.getUserName());
        values.put(colDatabaseDescription,user.getUserBio());
        values.put(colDatabaseTime_Creator,user.getUserTime());
        values.put(colDatabaseImage,Image);
        values.put(colDatabaseLanguage,language);
        long ID = DB.insert(UserTable, null, values);
        DB.close();
        return ID;
    }
    public long insertPersonalChatDetail(ChatList.ChatListItem chatListItem)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(colDatabaseID,chatListItem.getPersonalMessage().getPID());
        values.put(colDatabaseParticipant,chatListItem.getPersonalMessage().getPersonalUserOne() + "|" + chatListItem.getPersonalMessage().getPersonalUserTwo());
        values.put(colDatabaseTime_Creator,"useneededornot");
        values.put(colDatabaseArchievePinChat,"false|false");
        values.put(colDatabaseLastMessageId,chatListItem.getMessage().getMID());
        values.put(colDatabaseUnseenMessage,chatListItem.getNoOfUnseenMessage());
        long ID = DB.insert(PersonalChatTable,null,values);
        long id2 = insertUserDetail(chatListItem.getUser(),"image","languages");
        DB.close();
        return ID;
    }
    public void insertGroupChatDetail()
    {
        //these is remaining
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
    }
    public User getUserByUID(String UID){
        User user = new User();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor =  DB.rawQuery( "select * from UserTable where id=?"+ UID +"", null );
      if(cursor.moveToFirst()){
            do {
                user.setUID(cursor.getString(cursor.getColumnIndex(colDatabaseID)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(colDatabaseName)));
                user.setUserBio(cursor.getString(cursor.getColumnIndex(colDatabaseDescription)));
                user.setUserNumber("98989898989");
            }while (cursor.moveToNext());
        }
        DB.close();
        cursor.close();
        return user;
    }
    public List<ChatList.ChatListItem> getAllChatList() {

        List<ChatList.ChatListItem> chatListItemsList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + PersonalChatTable + " ORDER BY " +
                colDatabaseID + " DESC";

        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do
            {
                ChatList.ChatListItem  chatListItem = new ChatList.ChatListItem();
                PersonalMessage personalMessage = new PersonalMessage();
                personalMessage.setPID(cursor.getString(cursor.getColumnIndex(colDatabaseID)));
                String usersID[] = cursor.getString(cursor.getColumnIndex(colDatabaseParticipant)).split("|",2);
                personalMessage.setPersonalUserOne(usersID[0]);
                personalMessage.setPersonalUserTwo(usersID[1]);
                Message m = new Message();
                m.setMID(cursor.getString(cursor.getColumnIndex(colDatabaseLastMessageId)));
                chatListItem.setMessage(m);
                chatListItem.setPersonalMessage(personalMessage);
                String archievepinstatus[] = cursor.getString(cursor.getColumnIndex(colDatabaseArchievePinChat)).split("|",2);
                if (archievepinstatus[1].equals("true"))
                    chatListItem.setPinStatus(true);
                else
                    chatListItem.setPinStatus(false);
                if(archievepinstatus[0].equals("false"))
                    chatListItem.setArchive(false);
                else
                    chatListItem.setArchive(true);
                User user = new User();
                if(usersID[0].equals(ChatList.UID))
                    user = getUserByUID(usersID[1]);
                else
                    user = getUserByUID(usersID[0]);
                chatListItem.setUser(user);
                chatListItemsList.add(chatListItem);
            }
            while (cursor.moveToNext());
        }

        DB.close();

        cursor.close();
        selectQuery = "SELECT  * FROM " + GroupChatTable + " ORDER BY " +
                colDatabaseID + " DESC";
        DB = this.getWritableDatabase();
        cursor = DB.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do
            {
                ChatList.ChatListItem  chatListItem = new ChatList.ChatListItem();
                chatListItemsList.add(chatListItem);
            }
            while (cursor.moveToNext());
        }

        DB.close();

        cursor.close();
        return chatListItemsList;
    }
    public void updateUserDetails( User user)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(colDatabaseID,user.getUID());
        cvUpdate.put(colDatabaseName,user.getUserName());
        cvUpdate.put(colDatabaseDescription,user.getUserBio());
        DB.update(UserTable, cvUpdate, colDatabaseID + " = ?",
                new String[] { String.valueOf(user.getUID()) });

    }
    public void updatePersonalChatDetails(ChatList.ChatListItem chatListItem)
    {
        //updateUserDetails(user);
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        String state = null;
        try {
            if(chatListItem.getArchive()){
                state = "true|false";
            }
            if (chatListItem.getPinStatus()){
                state = "false|true";
            }
            cvUpdate.put(colDatabaseArchievePinChat,state);
        }catch(Exception e){

        }
        cvUpdate.put(colDatabaseUnseenMessage,chatListItem.getNoOfUnseenMessage());
        cvUpdate.put(colDatabaseLastMessageId,chatListItem.getMessage().getMID());
        Log.i("update",chatListItem.getPersonalMessage().getPID());
        int df = DB.update(PersonalChatTable, cvUpdate, colDatabaseID + " = ?",
                new String[] { String.valueOf(chatListItem.getPersonalMessage().getPID())});
        Log.i("update", String.valueOf(df));
        updateUserDetails(chatListItem.getUser());
    }
    public void updateGroupChatDetails( User user)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();

        DB.update(GroupChatTable, cvUpdate, colDatabaseID + " = ?",
                new String[] { String.valueOf(user.getUID()) });

    }
}
