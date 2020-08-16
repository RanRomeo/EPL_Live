package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 17-Aug-18.
 */

public class match_model {

    private String HomeTeam;
    private String HomeTeamID;
    private String HomeTeamScore;
    private String AwayTeam;
    private String AwayTeamID;
    private String AwayTeamScore;

    private String Date;
    private String Time;
    private String MathcId;
    private String MatchStatus;
    private String gameTimePast;
    private String cityName;
    private String StadiumName;


    private Long Millies;

    public match_model() {
    }

    public match_model(String homeTeam, String homeTeamID, String homeTeamScore, String awayTeam, String awayTeamID, String awayTeamScore, String date, String time, String mathcId, String matchStatus, String gameTimePast, String cityName, String stadiumName, Long millies) {
        HomeTeam = homeTeam;
        HomeTeamID = homeTeamID;
        HomeTeamScore = homeTeamScore;
        AwayTeam = awayTeam;
        AwayTeamID = awayTeamID;
        AwayTeamScore = awayTeamScore;
        Date = date;
        Time = time;
        MathcId = mathcId;
        MatchStatus = matchStatus;
        this.gameTimePast = gameTimePast;
        this.cityName = cityName;
        StadiumName = stadiumName;
        Millies = millies;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public String getHomeTeamID() {
        return HomeTeamID;
    }

    public void setHomeTeamID(String homeTeamID) {
        HomeTeamID = homeTeamID;
    }

    public String getHomeTeamScore() {
        return HomeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        HomeTeamScore = homeTeamScore;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

    public String getAwayTeamID() {
        return AwayTeamID;
    }

    public void setAwayTeamID(String awayTeamID) {
        AwayTeamID = awayTeamID;
    }

    public String getAwayTeamScore() {
        return AwayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        AwayTeamScore = awayTeamScore;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getMathcId() {
        return MathcId;
    }

    public void setMathcId(String mathcId) {
        MathcId = mathcId;
    }

    public String getMatchStatus() {
        return MatchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        MatchStatus = matchStatus;
    }

    public String getGameTimePast() {
        return gameTimePast;
    }

    public void setGameTimePast(String gameTimePast) {
        this.gameTimePast = gameTimePast;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStadiumName() {
        return StadiumName;
    }

    public void setStadiumName(String stadiumName) {
        StadiumName = stadiumName;
    }

    public Long getMillies() {
        return Millies;
    }

    public void setMillies(Long millies) {
        Millies = millies;
    }
}
