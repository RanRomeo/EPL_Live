package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 19-Aug-18.
 */

public class commentry_model  {


    String PlayerID;
    String ImageType;
    String CommentryText;
    String Time;


    public commentry_model(String playerID, String imageType, String commentryText, String time) {
        PlayerID = playerID;
        ImageType = imageType;
        CommentryText = commentryText;
        Time = time;
    }


    public commentry_model() {
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public String getCommentryText() {
        return CommentryText;
    }

    public void setCommentryText(String commentryText) {
        CommentryText = commentryText;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}



