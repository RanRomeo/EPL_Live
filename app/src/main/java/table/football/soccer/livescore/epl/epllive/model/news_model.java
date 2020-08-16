package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 15-Aug-18.
 */

public class news_model {

    String thumburl;
    String headText;
    String newsUrl;


    public news_model() {
    }

    public news_model(String thumburl, String headText, String newsUrl) {
        this.thumburl = thumburl;
        this.headText = headText;
        this.newsUrl = newsUrl;
    }

    public String getThumburl() {
        return thumburl;
    }

    public void setThumburl(String thumburl) {
        this.thumburl = thumburl;
    }

    public String getHeadText() {
        return headText;
    }

    public void setHeadText(String headText) {
        this.headText = headText;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
