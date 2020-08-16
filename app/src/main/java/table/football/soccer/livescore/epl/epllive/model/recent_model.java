package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 22-Aug-18.
 */

public class recent_model  {


    String matchDate;
    String matchStatdium;
    String matchscore;
    String matchTeamA;
    String matchTeamB;
    String matchLink;


    public recent_model() {
    }

    public recent_model(String matchDate, String matchStatdium, String matchscore, String matchTeamA, String matchTeamB, String matchLink) {
        this.matchDate = matchDate;
        this.matchStatdium = matchStatdium;
        this.matchscore = matchscore;
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

    public String getMatchscore() {
        return matchscore;
    }

    public void setMatchscore(String matchscore) {
        this.matchscore = matchscore;
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
