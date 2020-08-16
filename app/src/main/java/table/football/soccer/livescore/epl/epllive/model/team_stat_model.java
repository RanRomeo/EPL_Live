package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 21-Sep-18.
 */

public class team_stat_model {

    String clubName;
    int  ClubId;
    int Value;


    public team_stat_model() {
    }

    public team_stat_model(String clubName, int clubId, int value) {
        this.clubName = clubName;
        ClubId = clubId;
        Value = value;
    }


    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public int getClubId() {
        return ClubId;
    }

    public void setClubId(int clubId) {
        ClubId = clubId;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }
}
