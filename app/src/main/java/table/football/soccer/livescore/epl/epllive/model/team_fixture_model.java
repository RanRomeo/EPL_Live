package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 10-Sep-18.
 */

public class team_fixture_model  {


    String matchDate;
    String matchStatdium;
    String matchTime;
    String matchTeamA;
    String matchTeamB;
    String matchLink;


    public team_fixture_model() {
    }


    public team_fixture_model(String matchDate, String matchStatdium, String matchTime, String matchTeamA, String matchTeamB, String matchLink) {
        this.matchDate = matchDate;
        this.matchStatdium = matchStatdium;
        this.matchTime = matchTime;
        this.matchTeamA = matchTeamA;
        this.matchTeamB = matchTeamB;
        this.matchLink = matchLink;
    }


    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchStatdium() {
        return matchStatdium;
    }

    public void setMatchStatdium(String matchStatdium) {
        this.matchStatdium = matchStatdium;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getMatchTeamA() {
        return matchTeamA;
    }

    public void setMatchTeamA(String matchTeamA) {
        this.matchTeamA = matchTeamA;
    }

    public String getMatchTeamB() {
        return matchTeamB;
    }

    public void setMatchTeamB(String matchTeamB) {
        this.matchTeamB = matchTeamB;
    }

    public String getMatchLink() {
        return matchLink;
    }

    public void setMatchLink(String matchLink) {
        this.matchLink = matchLink;
    }
}
