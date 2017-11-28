package usth.edu.vn.ictflickr.Network;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

/**
 * Created by NghiaLe on 11/24/2017.
 */

public class FlickrClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = FlickrApi.class;

    public static final String REST_URL = "https://www.flickr.com/services";

    public static final String REST_CONSUMER_KEY = "42b2c5cba93ef18e966a2bb2e6e0ddce";

    public static final String REST_CONSUMER_SECRET = "f845dcd6db5ecbaf";

    public static final String REST_CALLBACK_URL = "flickr-android://cprest";

    public FlickrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET,
                REST_CALLBACK_URL);
        setBaseUrl("https://api.flickr.com/services/rest");
    }

    public void testLogin(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?method=flickr.test.login&format=json&nojsoncallback=1");
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getUserPublicPhotos(String userId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?method=flickr.people.getPublicPhotos" +
                "&format=json&nojsoncallback=1" +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getUserCameraRollPhotos(String userId,AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("?method=flickr.people.getPhotos" +
                "&format=json&nojsoncallback=1" +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getUserProfileImage(String userId,AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("?method=flickr.people.getInfo" +
                "&format=json&nojsoncallback=1" +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void searchPhotos(String text,AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("?method=flickr.photos.search" +
                "&format=json&nojsoncallback=1" +
                "&text=" + text);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getContactPhotos(String userId,AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("?method=flickr.photos.getContactsPublicPhotos" +
                "&format=json&nojsoncallback=1" +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getAlbumsList(String userId,AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("?method=flickr.photosets.getList" +
                "&format=json&nojsoncallback=1" +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }

    public void getAlbumDetailItems(String userId, String albumId, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("?method=flickr.photosets.getPhotos" +
                "&format=json&nojsoncallback=1" + "&photoset_id=" + albumId +
                "&user_id=" + userId);
        Log.d("DEBUG", "Sending API call to " + apiUrl);
        client.get(apiUrl, null, handler);
    }


}
