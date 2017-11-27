package usth.edu.vn.ictflickr.Search;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.UserProfile.*;
import usth.edu.vn.ictflickr.Utils.BottomNavigationViewHelper;
/**
 * Created by Nghia le on 11/12/2017.
 */

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private static final int ActivityNumber = 1;
    private EditText mSearchParams;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imageView;
    public static String searchText;
    private SearchActivity.SearchPagerAdapter adapter = new SearchActivity.SearchPagerAdapter(getSupportFragmentManager());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d(TAG, "onCreate: Started");
        Toolbar toolbar = (Toolbar) findViewById(R.id.searchToolBar);
        setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.searchClear);
        mSearchParams = (EditText) findViewById(R.id.searchText);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchParams.setText("");
            }
        });
        hideKeyboard();
        setupViewPager();
        initTextListener();
        setupBottomNavigationView();
        Log.d("DEBUG", "result: " + searchText);
    }

    private void hideKeyboard(){
        if(getCurrentFocus() != null){
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    private void initTextListener(){
        mSearchParams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tabLayout.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText = mSearchParams.getText().toString().toLowerCase(Locale.getDefault());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupViewPager() {
        PhotosFragment n0 = new PhotosFragment();
        PeopleFragment n1 = new PeopleFragment();
        GroupsFragment n2 = new GroupsFragment();

        adapter.addFrag(n0, getString(R.string.photos));
        adapter.addFrag(n1, getString(R.string.people));
        adapter.addFrag(n2, getString(R.string.groups));

        viewPager = (ViewPager) findViewById(R.id.searchPager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout = (TabLayout) findViewById(R.id.searchTab);
        tabLayout.setupWithViewPager(viewPager);

    }

    private class SearchPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> fragList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();

        public SearchPagerAdapter(FragmentManager fm) {
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


    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: Setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this, this, bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNumber);
        menuItem.setChecked(true);
    }

}
