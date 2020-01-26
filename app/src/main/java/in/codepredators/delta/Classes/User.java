package in.codepredators.delta.Classes;

import java.util.HashMap;
import java.util.List;

public class User {
    private String UID;
    private String userName;
    private String userNumber;
    private String userBio;
    private String userCountry;
    private String userProfilePic;                          //firebase reference
    private HashMap<String, String> userLanguages;          //Languages used by user - Selected at the time of sign in -(Java,SigninTime)
    private List<String> userArchiveChats;                  //Archives chats by user - (vhats id  - pid or  gid
    private List<String> userCode;        //code message id
    private List<String> userPinChats;
    private String userLastSeen;
    private HashMap<String, String> userLastSeenVisibility;        //(visibility type, time of changing setting)
    private String userTime;
    private String userProfilePicLocalAddress;

    private HashMap<String,String> userActivatedId; // pid or gid which helps us to know which pid(/gid) has new messge or which pid(/gid) is newly created
    //<PID,MID>

    public HashMap<String,String> getUserActivatedId() {
        return userActivatedId;
    }

    public void setUserActivatedId(HashMap<String, String> userActivatedId) {
        this.userActivatedId = userActivatedId;
    }

    public String getUserProfilePicLocalAddress() {
        return userProfilePicLocalAddress;
    }

    public void setUserProfilePicLocalAddress(String userProfilePicLocalAddress) {
        this.userProfilePicLocalAddress = userProfilePicLocalAddress;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getUID() {
        return UID;
    }

    public User(String UID, String userName, String userNumber, String userBio, String userCountry, String userProfilePic, HashMap<String, String> userLanguages, List<String> userArchiveChats, List<String> userCode, List<String> userPinChats, String userLastSeen, HashMap<String, String> userLastSeenVisibility, String userTime, String userProfilePicLocalAddress) {
        this.UID = UID;
        this.userName = userName;
        this.userNumber = userNumber;
        this.userBio = userBio;
        this.userCountry = userCountry;
        this.userProfilePic = userProfilePic;
        this.userLanguages = userLanguages;
        this.userArchiveChats = userArchiveChats;
        this.userCode = userCode;
        this.userPinChats = userPinChats;
        this.userLastSeen = userLastSeen;
        this.userLastSeenVisibility = userLastSeenVisibility;
        this.userTime = userTime;
        this.userProfilePicLocalAddress = userProfilePicLocalAddress;
    }

    public User() {
    }
    public User(String userName)
    {

        this.userName=userName;
//        this.textViewTimeOfMessage = textViewTimeOfMessage;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(String userProfilePic) {
        this.userProfilePic = userProfilePic;
    }

    public HashMap<String, String> getUserLanguages() {
        return userLanguages;
    }

    public void setUserLanguages(HashMap<String, String> userLanguages) {
        this.userLanguages = userLanguages;
    }

    public List<String> getUserArchiveChats() {
        return userArchiveChats;
    }

    public void setUserArchiveChats(List<String> userArchiveChats) {
        this.userArchiveChats = userArchiveChats;
    }

    public List<String> getUserCode() {
        return userCode;
    }

    public void setUserCode(List<String> userCode) {
        this.userCode = userCode;
    }

    public List<String> getUserPinChats() {
        return userPinChats;
    }

    public void setUserPinChats(List<String> userPinChats) {
        this.userPinChats = userPinChats;
    }

    public String getUserLastSeen() {
        return userLastSeen;
    }

    public void setUserLastSeen(String userLastSeen) {
        this.userLastSeen = userLastSeen;
    }

    public HashMap<String, String> getUserLastSeenVisibility() {
        return userLastSeenVisibility;
    }

    public void setUserLastSeenVisibility(HashMap<String, String> userLastSeenVisibility) {
        this.userLastSeenVisibility = userLastSeenVisibility;
    }



}