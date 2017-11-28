package usth.edu.vn.ictflickr.UserProfile;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import usth.edu.vn.ictflickr.Login.SplashActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.FullScreenImageViewActivity;
import usth.edu.vn.ictflickr.Utils.GridImageAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicFragment extends Fragment {
    private static final String TAG = "PublicFragment";
    View view;
    FlickrClient client;
    private String imageURL;
    private ArrayList<String> imageURLs = new ArrayList<>();

    //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

    public PublicFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starting");
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_public, container, false);
        client = FlickrClientApp.getRestClient();
        loadPublicPhotos();
        return view;

    }


    private void setImageGrid(final ArrayList<String> imageURLs){
        Log.d(TAG, "setImageGrid: starting");
        GridView gridView = (GridView) view.findViewById(R.id.gridViewPublic);
        GridImageAdapter gridAdapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_image_view,imageURLs);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/3;
        gridView.setColumnWidth(imageWidth);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FullScreenImageViewActivity.class);
                intent.putExtra("ImageURL", imageURLs.get(position));
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("ImageList", imageURLs);
                startActivity(intent);
            }
        });
    }

    private void loadPublicPhotos() {
        Log.d(TAG, "gridSetup: starting");
        client.getUserPublicPhotos(SplashActivity.userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                imageURLs.clear();
                try {
                    JSONArray photos = json.getJSONObject("photos").getJSONArray("photo");
                    for (int x = 0; x < photos.length(); x++) {
                        String id = photos.getJSONObject(x).getString("id");
                        String secret = photos.getJSONObject(x).getString("secret");
                        String server = photos.getJSONObject(x).getString("server");
                        Integer farm = photos.getJSONObject(x).getInt("farm");
                        imageURL = "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
                        imageURLs.add(imageURL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setImageGrid(imageURLs);
            }
        });
    }
}
