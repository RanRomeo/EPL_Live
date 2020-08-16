package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 15-Aug-18.
 */

public class gif_model {

    int T_NO;
    String GifTumb;
    String GifLink;
    String GifText;


    public gif_model() {
    }

    public gif_model(int t_NO, String gifTumb, String gifLink, String gifText) {
        T_NO = t_NO;
        GifTumb = gifTumb;
        GifLink = gifLink;
        GifText = gifText;
    }


    public int getT_NO() {
        return T_NO;
    }

    public void setT_NO(int t_NO) {
        T_NO = t_NO;
    }

    public String getGifTumb() {
        return GifTumb;
    }

    public void setGifTumb(String gifTumb) {
        GifTumb = gifTumb;
    }

    public String getGifLink() {
        return GifLink;
    }

    public void setGifLink(String gifLink) {
        GifLink = gifLink;
    }

    public String getGifText() {
        return GifText;
    }

    public void setGifText(String gifText) {
        GifText = gifText;
    }
}


