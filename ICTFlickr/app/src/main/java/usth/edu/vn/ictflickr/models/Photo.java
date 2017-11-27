package usth.edu.vn.ictflickr.models;

/**
 * Created by NghiaLe on 11/27/2017.
 */

public class Photo {
    private String photo_id;
    private String title;
    private String user_id;
    private String user_name;
    private String imageURL;
    private String user_profile_imageURL;
    private String user_real_name;

    public Photo(){

    }

    public Photo(String photo_id, String title, String user_id, String user_name,
                 String user_profile_imageURL, String user_real_name, String imageURL) {
        this.photo_id = photo_id;
        this.title = title;
        this.user_id = user_id;
        this.user_name = user_name;
        this.imageURL = imageURL;
        this.user_profile_imageURL = user_profile_imageURL;
        this.user_real_name = user_real_name;
    }

    public String getUser_profile_image() {
        return user_profile_imageURL;
    }

    public void setUser_profile_image(String user_profile_imageURL) {
        this.user_profile_imageURL = user_profile_imageURL;
    }

    public String getUser_real_name() {
        return user_real_name;
    }

    public void setUser_real_name(String user_real_name) {
        this.user_real_name = user_real_name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photo_id='" + photo_id + '\'' +
                ", title='" + title + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", user_profile_image='" + user_profile_imageURL + '\'' +
                ", user_real_name='" + user_real_name + '\'' +
                '}';
    }
}
