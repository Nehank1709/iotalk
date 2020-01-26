package in.codepredators.delta.Classes;

public class PersonalMessage extends Message
{
    private String personalUserOne;//Contain uid of one user (first message sender)
    private String personalUserTwo;//contain uid of second user
    private String PID;
    private String personalUserOneStatus;
    private String personalUserTwoStatus;


    public PersonalMessage() {
    }


    public String getPersonalUserOneStatus() {
        return personalUserOneStatus;
    }

    public void setPersonalUserOneStatus(String personalUserOneStatus) {
        this.personalUserOneStatus = personalUserOneStatus;
    }

    public String getPersonalUserTwoStatus() {
        return personalUserTwoStatus;
    }

    public void setPersonalUserTwoStatus(String personalUserTwoStatus) {
        this.personalUserTwoStatus = personalUserTwoStatus;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getPersonalUserOne() {
        return personalUserOne;
    }

    public void setPersonalUserOne(String personalUserOne) {
        this.personalUserOne = personalUserOne;
    }

    public String getPersonalUserTwo() {
        return personalUserTwo;
    }

    public void setPersonalUserTwo(String personalUserTwo) {
        this.personalUserTwo = personalUserTwo;
    }

}
