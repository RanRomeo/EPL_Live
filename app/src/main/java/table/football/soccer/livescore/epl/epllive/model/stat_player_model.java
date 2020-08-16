package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 20-Sep-18.
 */

public class stat_player_model {

    int value;
    int playerId;
    String imageId;
    String clubname;
    String Playername;


    public stat_player_model() {
    }

    public stat_player_model(int value, int playerId, String imageId, String clubname, String playername) {
        this.value = value;
        this.playerId = playerId;
        this.imageId = imageId;
        this.clubname = clubname;
        Playername = playername;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public String getPlayername() {
        return Playername;
    }

    public void setPlayername(String playername) {
        Playername = playername;
    }
}
