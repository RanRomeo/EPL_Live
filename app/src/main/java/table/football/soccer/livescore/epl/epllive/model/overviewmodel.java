package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 25-Aug-18.
 */

public class overviewmodel  {

    String type;
    String time;
    String score;
    String className;

    String playerImageID;
    String playerlink;
    String playerName;

    String assitplayerName;
    String assitplayerlink;

    String subplayerImageID;
    String subplayerlink;
    String subplayerName;

    public overviewmodel() {
    }

    public overviewmodel(String type, String time, String score, String className, String playerImageID, String playerlink, String playerName, String assitplayerName, String assitplayerlink, String subplayerImageID, String subplayerlink, String subplayerName) {
        this.type = type;
        this.time = time;
        this.score = score;
        this.className = className;
        this.playerImageID = playerImageID;
        this.playerlink = playerlink;
        this.playerName = playerName;
        this.assitplayerName = assitplayerName;
        this.assitplayerlink = assitplayerlink;
        this.subplayerImageID = subplayerImageID;
        this.subplayerlink = subplayerlink;
        this.subplayerName = subplayerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPlayerImageID() {
        return playerImageID;
    }

    public void setPlayerImageID(String playerImageID) {
        this.playerImageID = playerImageID;
    }

    public String getPlayerlink() {
        return playerlink;
    }

    public void setPlayerlink(String playerlink) {
        this.playerlink = playerlink;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAssitplayerName() {
        return assitplayerName;
    }

    public void setAssitplayerName(String assitplayerName) {
        this.assitplayerName = assitplayerName;
    }

    public String getAssitplayerlink() {
        return assitplayerlink;
    }

    public void setAssitplayerlink(String assitplayerlink) {
        this.assitplayerlink = assitplayerlink;
    }

    public String getSubplayerImageID() {
        return subplayerImageID;
    }

    public void setSubplayerImageID(String subplayerImageID) {
        this.subplayerImageID = subplayerImageID;
    }

    public String getSubplayerlink() {
        return subplayerlink;
    }

    public void setSubplayerlink(String subplayerlink) {
        this.subplayerlink = subplayerlink;
    }

    public String getSubplayerName() {
        return subplayerName;
    }

    public void setSubplayerName(String subplayerName) {
        this.subplayerName = subplayerName;
    }
}
/*
    String playerImageID2;
    String playerLink2;
    String playerName2;
 */