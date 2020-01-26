package in.codepredators.delta.Classes;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.HashMap;

public class Message {

    private String messageTime;
    private String messageText;
    private String messagesenderUID;
    private String MID;//(ID of personal message by mixing id of user one and two) (id of groupmessage by )
    private String messageType;//(Different messages contain different integer value which will help us to make changes in message xml body)
    // image - 1 , text - 0 , file - 2 , contact - 3 possible combination(without reply) - 0,1,2,01,02
    //possible combination (with reply) - 0RmessageId(replied message id)     (Similarly for other 4 type)
    private String messageImage;//contain reference of image that is shared
    private String messageFile;//contain reference of code of user
    private HashMap<String,String> messageContact;   //(contact,contact Name)
    private Bitmap messageImageBitmap;
//this four is used to store data from sqldatabase ,not for storing data on firebase
    private String messageFileLocalAddress;
    private String messageStarredStatus;
    private String messageStatus;
    private String messageImageLocalAddress;


    public String getMessageImageLocalAddress() {
        return messageImageLocalAddress;
    }

    public void setMessageImageLocalAddress(String messageImageLocalAddress) {
        this.messageImageLocalAddress = messageImageLocalAddress;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageStarredStatus() {
        return messageStarredStatus;
    }

    public void setMessageStarredStatus(String messageStarredStatus) {
        this.messageStarredStatus = messageStarredStatus;
    }

    public String getMessageFileLocalAddress() {
        return messageFileLocalAddress;
    }

    public void setMessageFileLocalAddress(String messageFileLocalAddress) {
        this.messageFileLocalAddress = messageFileLocalAddress;
    }

    public Bitmap getMessageImageBitmap() {
        return messageImageBitmap;
    }

    public void setMessageImageBitmap(Bitmap messageImageBitmap) {
        this.messageImageBitmap = messageImageBitmap;
    }

    public HashMap<String, String> getMessageContact() {
        return messageContact;
    }

    public void setMessageContact(HashMap<String, String> messageContact) {
        this.messageContact = messageContact;
    }

    public String getMessageFile()
    {
        return messageFile;
    }

    public void setMessageFile(String messageFile)
    {
        this.messageFile = messageFile;
    }

    public String getMessageType()
    {
        return messageType;
    }

    public void setMessageType(String messageType)
    {
        this.messageType = messageType;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }


    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessagesenderUID() {
        return messagesenderUID;
    }

    public void setMessagesenderUID(String messagesenderUID) {
        this.messagesenderUID = messagesenderUID;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

  public Message() {
    }
}
