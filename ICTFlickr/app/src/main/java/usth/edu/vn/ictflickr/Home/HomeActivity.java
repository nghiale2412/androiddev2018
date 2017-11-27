package usth.edu.vn.ictflickr.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import usth.edu.vn.ictflickr.Login.SplashActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.BottomNavigationViewHelper;
import usth.edu.vn.ictflickr.Utils.Favorite;
import usth.edu.vn.ictflickr.Utils.NewsFeedAdapter;
import usth.edu.vn.ictflickr.Utils.UniversalImageLoader;
import usth.edu.vn.ictflickr.models.Photo;

import static android.view.View.GONE;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ActivityNumber = 0;
    private ArrayList<Photo> photoList = new ArrayList<>();
    private ArrayList<String> newsFeedUserId = new ArrayList<>();
    private Photo photo;
    private String realname;
    private String profileImageURL;
    private String id, secret, server, username, title, owner, imageURL;
    private Integer farm, x, i;

    FlickrClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: Starting..");
        client = FlickrClientApp.getRestClient();
        setupBottomNavigationView();
        loadNewsFeed();
    }

    private void setNewsFeedItem(final ArrayList<Photo> photoList) {
        ListView listView = (ListView) findViewById(R.id.feedsList);
        NewsFeedAdapter feedAdapter = new NewsFeedAdapter(this, R.layout.layout_news_feed_item, photoList);
        listView.setAdapter(feedAdapter);
        feedAdapter.notifyDataSetChanged();
    }

    private void loadNewsFeed() {
        client.getContactPhotos(SplashActivity.userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                try {
                    JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
                    for (x = 0; x < photos.length(); x++) {
                        photo = new Photo();
                        id = photos.getJSONObject(x).getString("id");
                        secret = photos.getJSONObject(x).getString("secret");
                        server = photos.getJSONObject(x).getString("server");
                        farm = photos.getJSONObject(x).getInt("farm");
                        username = photos.getJSONObject(x).getString("username");
                        title = photos.getJSONObject(x).getString("title");
                        owner = photos.getJSONObject(x).getString("owner");
                        newsFeedUserId.add(owner);
                        imageURL = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
                        photo.setPhoto_id(id);
                        photo.setUser_name(username);
                        photo.setTitle(title);
                        photo.setUser_id(owner);
                        photo.setImageURL(imageURL);
                        photoList.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (i = 0; i < (newsFeedUserId.size()-1); i++) {
                    client.getUserProfileImage(newsFeedUserId.get(i), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers,
                                              JSONObject json) {

                            Photo photo1 = photoList.get(i);
                            Log.d("DEBUG", "result: " + json.toString());
                            try {
                                JSONObject person = json.getJSONObject("person");
                                String nsid = person.getString("nsid");
                                String iconserver = person.getString("iconserver");
                                Integer iconfarm = person.getInt("iconfarm");
                                realname = person.getJSONObject("realname").getString("_content");
                                profileImageURL = "https://farm" + iconfarm + ".staticflickr.com/" + iconserver + "/buddyicons/" + nsid + ".jpg";
                                photo1.setUser_profile_image(profileImageURL);
                                photo1.setUser_real_name(realname);
                                Log.d("DEBUG", "onSuccess: " + photo1.getUser_real_name());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (i == (newsFeedUserId.size()-1)) {
                                setNewsFeedItem(photoList);
                                Log.d("DEBUG", "LIST: setting list");
                            }
                            //((Photo) photoList.get(x).setUser_profile_image(profileImageURL);
                        }
                    });
                }

            }
        });
    }

    /*client.getUserProfileImage(owner, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers,
                JSONObject json) {

            Log.d("DEBUG", "result: " + json.toString());
            try {
                JSONObject person = json.getJSONObject("person");
                String nsid = person.getString("nsid");
                String iconserver = person.getString("iconserver");
                Integer iconfarm = person.getInt("iconfarm");
                String realname1 = person.getJSONObject("realname").getString("_content");
                profileImageURL = "https://farm" + iconfarm + ".staticflickr.com/" + iconserver + "/buddyicons/" + nsid + ".jpg";
                setRealname(realname1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            photo.setPhoto_id(id);
            photo.setUser_name(username);
            photo.setTitle(title);
            photo.setUser_id(owner);
            photo.setImageURL(imageURL);
            setPhoto(photo);
            Log.d("DEBUG", "HAHA: " + getRealname());
            //((Photo) photoList.get(x).setUser_profile_image(profileImageURL);
        }
    }); */


    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this, this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNumber);
        menuItem.setChecked(true);
    }

   /* private void favoriteToggle(){
        favoriteIconBlue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: touching 1");
                return mGestureDetector.onTouchEvent(event);
            }
        });
        favoriteIconWhite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: touching 2");
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: double taping");
            favorite.toggleFavorite();
            return true;
        }
    } */


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
