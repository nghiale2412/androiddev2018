package usth.edu.vn.ictflickr.UserProfile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import usth.edu.vn.ictflickr.Login.SplashActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.BottomNavigationViewHelper;
import usth.edu.vn.ictflickr.Utils.UniversalImageLoader;

import static android.R.attr.x;
import static usth.edu.vn.ictflickr.R.string.photos;

/**
 * Created by Nghia le on 11/12/2017.
 */

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private static final int ActivityNumber = 4;
    private ProgressBar mProgressBar;
    private ImageView profileImage;
    private ImageView profileCoverImage;
    private TextView userName;
    private String imageURL;
    private String realname;
    FlickrClient client;

    //http://farm{icon-farm}.staticflickr.com/{icon-server}/buddyicons/{nsid}.jpg

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: Started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client = FlickrClientApp.getRestClient();
        setupBottomNavigationView();
        setupActivityWidgets();
        loadUserProfileImage();
        UniversalImageLoader.setImage("http://www.dccomics.com/sites/default/files/GalleryChar_1920x1080_BM_Cv38_54b5d0d1ada864.04916624.jpg",profileCoverImage,mProgressBar);
        setupViewPager();
    }

    private void loadUserProfileImage() {
        Log.d(TAG, "gridSetup: starting");
        client.getUserProfileImage(SplashActivity.userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                // Add new photos to SQLite
                try {
                    JSONObject person = json.getJSONObject("person");
                    String nsid = person.getString("nsid");
                    String iconserver = person.getString("iconserver");
                    Integer iconfarm = person.getInt("iconfarm");
                    realname = person.getJSONObject("realname").getString("_content");
                    imageURL = "https://farm" + iconfarm + ".staticflickr.com/" + iconserver + "/buddyicons/"+nsid+".jpg";
                    UniversalImageLoader.setImage(imageURL,profileImage,mProgressBar);
                    userName.setText(realname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void setupActivityWidgets() {
        userName = (TextView) findViewById(R.id.userName);
        mProgressBar = (ProgressBar) findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileCoverImage = (ImageView) findViewById(R.id.profileCoverImage);
    }


    private void setupViewPager() {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());
        CameraRollFragment n0 = new CameraRollFragment();
        PublicFragment n1 = new PublicFragment();
        AlbumsFragment n2 = new AlbumsFragment();
        GroupsFragment n3 = new GroupsFragment();

        adapter.addFrag(n0, getString(R.string.camera_roll));
        adapter.addFrag(n1, getString(R.string.public1));
        adapter.addFrag(n2, getString(R.string.albums));
        adapter.addFrag(n3, getString(R.string.groups));

        ViewPager viewPager = (ViewPager) findViewById(R.id.profilePager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.profileTab);
        tabLayout.setupWithViewPager(viewPager);
        adapter.notifyDataSetChanged();

    }


    private class ProfilePagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        public ProfilePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFrag(Fragment f, String title) {
            fragList.add(f);
            titleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragList.get(position);
        }

        @Override
        public int getCount() {
            return fragList.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

    }


    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(UserProfileActivity.this, this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNumber);
        menuItem.setChecked(true);
    }

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
