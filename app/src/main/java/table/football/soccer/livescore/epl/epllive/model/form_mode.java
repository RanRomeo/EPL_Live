package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 23-Aug-18.
 */

public class form_mode {


    String result;
    String image;
    String team_and_score;
    String place;
    String match_link;


    public form_mode() {
    }

    public form_mode(String result, String image, String team_and_score, String place, String match_link) {
        this.result = result;
        this.image = image;
        this.team_and_score = team_and_score;
        this.place = place;
        this.match_link = match_link;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTeam_and_score() {
        return team_and_score;
    }

    public void setTeam_and_score(String team_and_score) {
        this.team_and_score = team_and_score;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMatch_link() {
        return match_link;
    }

    public void setMatch_link(String match_link) {
        this.match_link = match_link;
    }
}
