package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 16-Nov-18.
 */

public class season_model {

    int id;
    String date;

    public season_model() {
    }

    public season_model(int id, String date) {
        this.id = id;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
