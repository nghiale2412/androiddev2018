package usth.edu.vn.ictflickr.UserProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.BottomNavigationViewHelper;

/**
 * Created by Nghia le on 11/12/2017.
 */

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private static final int ActivityNumber = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: Started");
        setupBottomNavigationView();
        setupViewPager();
    }

    private void setupViewPager() {
        ProfilePagerAdapter adapter = new ProfilePagerAdapter(getSupportFragmentManager());
        CameraRollFragment n0 = new CameraRollFragment();
        PublicFragment n1 = new PublicFragment();
        AlbumsFragment n2 = new AlbumsFragment();
        GroupsFragment n3 = new GroupsFragment();

        adapter.addFrag(n0, "Camera Roll");
        adapter.addFrag(n1, "Public");
        adapter.addFrag(n2, "Albums");
        adapter.addFrag(n3, "Groups");

        ViewPager viewPager = (ViewPager) findViewById(R.id.profilePager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.profileTab);
        tabLayout.setupWithViewPager(viewPager);

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
