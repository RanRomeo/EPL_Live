package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 24-Oct-18.
 */

public class Top_videos {



  private   int T_NO;
  private   String ImageLink;
  private   String Yt_id;
  private  String Video_Title;
  private String ChannelId;

    public Top_videos() {
    }

    public Top_videos(int t_NO, String imageLink, String yt_id, String video_Title, String channelId) {
        T_NO = t_NO;
        ImageLink = imageLink;
        Yt_id = yt_id;
        Video_Title = video_Title;
        ChannelId = channelId;
    }

    public int getT_NO() {
        return T_NO;
    }

    public void setT_NO(int t_NO) {
        T_NO = t_NO;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getYt_id() {
        return Yt_id;
    }

    public void setYt_id(String yt_id) {
        Yt_id = yt_id;
    }

    public String getVideo_Title() {
        return Video_Title;
    }

    public void setVideo_Title(String video_Title) {
        Video_Title = video_Title;
    }

    public String getChannelId() {
        return ChannelId;
    }

    public void setChannelId(String channelId) {
        ChannelId = channelId;
    }
}
