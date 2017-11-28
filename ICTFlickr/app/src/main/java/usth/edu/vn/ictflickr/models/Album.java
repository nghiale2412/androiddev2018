package usth.edu.vn.ictflickr.models;

/**
 * Created by NghiaLe on 11/28/2017.
 */

public class Album {
    private String albumId;
    private String albumPrimaryImageURL;
    private String albumTitle;
    private String albumDescription;
    private String albumDate;
    private String imageCount;

    public Album(){

    }

    public Album(String albumId, String imageURL, String imageTitle, String albumDescription, String albumDate, String imageCount) {
        this.albumId = albumId;
        this.albumPrimaryImageURL = imageURL;
        this.albumTitle = imageTitle;
        this.albumDescription = albumDescription;
        this.albumDate = albumDate;
        this.imageCount = imageCount + " photos";
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumPrimaryImageURL() {
        return albumPrimaryImageURL;
    }

    public void setAlbumPrimaryImageURL(String albumPrimaryImageURL) {
        this.albumPrimaryImageURL = albumPrimaryImageURL;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public String getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }

    public String getImageCount() {
        return imageCount;
    }

    public void setImageCount(String imageCount) {
        this.imageCount = imageCount + " photos";
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumPrimaryImageURL='" + albumPrimaryImageURL + '\'' +
                ", albumTitle='" + albumTitle + '\'' +
                ", albumDescription='" + albumDescription + '\'' +
                ", albumDate='" + albumDate + '\'' +
                ", imageCount='" + imageCount + '\'' +
                '}';
    }
}


