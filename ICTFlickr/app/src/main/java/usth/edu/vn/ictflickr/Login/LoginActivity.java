package usth.edu.vn.ictflickr.Login;

import android.content.Intent;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.codepath.oauth.OAuthLoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import usth.edu.vn.ictflickr.Home.HomeActivity;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.UserProfile.UserProfileActivity;
import usth.edu.vn.ictflickr.Utils.UniversalImageLoader;

import static android.view.View.GONE;

/**
 * Created by NghiaLe on 11/23/2017.
 */

public class LoginActivity extends OAuthLoginActivity<FlickrClient> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initImgLoader();
    }

    private void initImgLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(LoginActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());

    }

    @Override
    public void onLoginSuccess() {
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    public void loginToRest(View view) {
        getClient().connect();
    }

}
