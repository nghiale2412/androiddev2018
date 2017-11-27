package usth.edu.vn.ictflickr.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import usth.edu.vn.ictflickr.Home.HomeActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;

import cz.msebera.android.httpclient.Header;

/**
 * Created by NghiaLe on 11/24/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    FlickrClient client;
    public static String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        client = FlickrClientApp.getRestClient();
        getUserId();
        ImageView textView = (ImageView) findViewById(R.id.splashImage);
        Animation splash = AnimationUtils.loadAnimation(this, R.anim.splash_transaction);
        textView.startAnimation(splash);
        final Intent i = new Intent(this, HomeActivity.class);
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }

    public void getUserId(){
        client.testLogin(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
                    JSONObject user = json.getJSONObject("user");
                    userId = user.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
