package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 29-Sep-18.
 */

public class notification_team_id {


    int TeamId;
    String TeamName;

    public notification_team_id() {
    }

    public notification_team_id(int teamId, String teamName) {
        TeamId = teamId;
        TeamName = teamName;

    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int teamId) {
        TeamId = teamId;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }
}
