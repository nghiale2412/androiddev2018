package usth.edu.vn.ictflickr.UserProfile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.FullScreenImageViewActivity;
import usth.edu.vn.ictflickr.Utils.GridImageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {
    private static final String TAG = "GroupsFragment";
    View view;

    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_groups, container, false);
        gridSetup();
        return view;
    }

    private void setImageGrid(final ArrayList<String> imageURLs){
        Log.d(TAG, "setImageGrid: starting");
        GridView gridView = (GridView) view.findViewById(R.id.gridViewGroups);
        GridImageAdapter gridAdapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_image_view, imageURLs);
        gridView.setAdapter(gridAdapter);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/3;
        gridView.setColumnWidth(imageWidth);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FullScreenImageViewActivity.class);
                intent.putExtra("ImageURL", imageURLs.get(position));
                intent.putExtra("position",position);
                intent.putStringArrayListExtra("ImageList", imageURLs);
                startActivity(intent);
            }
        });
    }

    private void gridSetup(){
        Log.d(TAG, "gridSetup: starting");
        ArrayList<String> imageURLs = new ArrayList<>();
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        imageURLs.add("https://farm5.staticflickr.com/4546/37668973585_c988a17d6d.jpg");
        setImageGrid(imageURLs);
    }

}
