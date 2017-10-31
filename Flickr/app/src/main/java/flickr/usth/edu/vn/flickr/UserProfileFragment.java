package flickr.usth.edu.vn.flickr;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {

    View view;
    TabLayout tabs;
    ViewPager vp;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        tabs = (TabLayout) view.findViewById(R.id.profiletab);
        vp = (ViewPager) view.findViewById(R.id.profilepager);
        setUpPager();
        return view;
    }
    private void setUpPager() {
        ProfilePagerAdapter adp = new ProfilePagerAdapter(getChildFragmentManager());
        CameraRollsFragment n0 = new CameraRollsFragment();
        CameraRollsFragment n1 = new CameraRollsFragment();
        CameraRollsFragment n2 = new CameraRollsFragment();
        CameraRollsFragment n3 = new CameraRollsFragment();

        adp.addFrag(n0, "Camera Rolls");
        adp.addFrag(n1, "Public");
        adp.addFrag(n2, "Albums");
        adp.addFrag(n3, "Groups");
        vp.setAdapter(adp);
        vp.setOffscreenPageLimit(1);
        tabs.setupWithViewPager(vp);
    }

    private class ProfilePagerAdapter extends FragmentPagerAdapter{

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

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("ChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
