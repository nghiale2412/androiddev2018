package usth.edu.vn.ictflickr.UserProfile;


import android.content.Intent;
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
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import usth.edu.vn.ictflickr.Login.SplashActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.AlbumsListAdapter;
import usth.edu.vn.ictflickr.models.Album;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsFragment extends Fragment {
    private static final String TAG = "AlbumsFragment";
    private ArrayList<Album> albumList = new ArrayList<>();
    View view;
    FlickrClient client;
    private String primaryImageURL;
    Album album;

    public AlbumsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_profile_albums, container, false);
        client = FlickrClientApp.getRestClient();
        loadAlbumItems();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setAlbumItems(final ArrayList<Album> albumList) {
        ListView listView = (ListView) view.findViewById(R.id.albumsList);
        AlbumsListAdapter albumAdapter = new AlbumsListAdapter(getActivity(), R.layout.layout_albums_list_item, albumList);
        listView.setAdapter(albumAdapter);
        albumAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AlbumsDetailActivity.class);
                intent.putExtra("AlbumTitle", albumList.get(position).getAlbumTitle());
                intent.putExtra("AlbumPrimaryImageURL", albumList.get(position).getAlbumPrimaryImageURL());
                intent.putExtra("AlbumImageCount", albumList.get(position).getImageCount());
                intent.putExtra("AlbumId", albumList.get(position).getAlbumId());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void loadAlbumItems() {
        Log.d(TAG, "listSetup: starting");
        client.getAlbumsList(SplashActivity.userId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                albumList.clear();
                try {
                    JSONArray photosets = json.getJSONObject("photosets").getJSONArray("photoset");
                    for (int x = 0; x < photosets.length(); x++) {
                        album = new Album();
                        String id = photosets.getJSONObject(x).getString("id");
                        String primary = photosets.getJSONObject(x).getString("primary");
                        String secret = photosets.getJSONObject(x).getString("secret");
                        String server = photosets.getJSONObject(x).getString("server");
                        Integer farm = photosets.getJSONObject(x).getInt("farm");
                        Integer photos = photosets.getJSONObject(x).getInt("photos");
                        String imageCount = photos.toString();
                        String title = photosets.getJSONObject(x).getJSONObject("title").getString("_content");
                        String description = photosets.getJSONObject(x).getJSONObject("description").getString("_content");
                        primaryImageURL = "https://farm" + farm + ".staticflickr.com/" + server + "/" + primary + "_" + secret + ".jpg";
                        album.setAlbumId(id);
                        album.setAlbumTitle(title);
                        if (description != null){
                        album.setAlbumDescription(description);}
                        album.setAlbumPrimaryImageURL(primaryImageURL);
                        album.setImageCount(imageCount);
                        albumList.add(album);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAlbumItems(albumList);
            }
        });
    }

}
