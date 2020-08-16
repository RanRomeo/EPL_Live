package table.football.soccer.livescore.epl.epllive.model;

/**
 * Created by Ran on 21-Aug-18.
 */

public class photo_model {


    String photoLink;
    String photoDesc;
    String photoThumb;

    public photo_model() {
    }

    public photo_model(String photoLink, String photoDesc, String photoThumb) {
        this.photoLink = photoLink;
        this.photoDesc = photoDesc;
        this.photoThumb = photoThumb;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getPhotoDesc() {
        return photoDesc;
    }

    public void setPhotoDesc(String photoDesc) {
        this.photoDesc = photoDesc;
    }

    public String getPhotoThumb() {
        return photoThumb;
    }

    public void setPhotoThumb(String photoThumb) {
        this.photoThumb = photoThumb;
    }
}
