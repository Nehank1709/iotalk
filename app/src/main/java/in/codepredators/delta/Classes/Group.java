package in.codepredators.delta.Classes;

import java.util.HashMap;

public class Group extends  Message{
    private String groupName;
    private String groupDescription;
    private String groupPic;
    private String GID;
    private HashMap<String,String> groupParticipant;
    private String groupFormationTime;
    private String groupCreater;
    public Group() {
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(String groupPic) {
        this.groupPic = groupPic;
    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public HashMap<String, String> getGroupParticipant() {
        return groupParticipant;
    }

    public void setGroupParticipant(HashMap<String, String> groupParticipant) {
        this.groupParticipant = groupParticipant;
    }

    public String getGroupFormationTime() {
        return groupFormationTime;
    }
    public void setGroupFormationTime(String groupFormationTime) {
        this.groupFormationTime = groupFormationTime;
    }

    public String getGroupCreater() {
        return groupCreater;
    }

    public void setGroupCreater(String groupCreater) {
        this.groupCreater = groupCreater;
    }

}
