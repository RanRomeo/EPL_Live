package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 21-Oct-18.
 */

public class video_model {


    String imageLink;
    String VideoTitle;
    String videID;
    String channelId;

    public video_model() {
    }

    public video_model(String imageLink, String videoTitle, String videID, String channelId) {
        this.imageLink = imageLink;
        VideoTitle = videoTitle;
        this.videID = videID;
        this.channelId = channelId;
    }


    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }

    public String getVideID() {
        return videID;
    }

    public void setVideID(String videID) {
        this.videID = videID;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
