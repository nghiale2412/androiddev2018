package usth.edu.vn.ictflickr.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleFragment extends Fragment {


    private static final String TAG = "PublicFragment";
    View view;
    FlickrClient client;
    String imageURL;
    private ArrayList<String> imageURLs = new ArrayList<>();

    public PeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_public, container, false);
        return view;
    }

}
