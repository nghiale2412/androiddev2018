package usth.edu.vn.ictflickr.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

import cz.msebera.android.httpclient.Header;
import uk.co.senab.photoview.PhotoView;
import usth.edu.vn.ictflickr.Login.SplashActivity;
import usth.edu.vn.ictflickr.Network.FlickrClient;
import usth.edu.vn.ictflickr.Network.FlickrClientApp;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Utils.BottomNavigationViewHelper;
import usth.edu.vn.ictflickr.Utils.FullScreenImageViewActivity;
import usth.edu.vn.ictflickr.Utils.GridImageAdapter;
import usth.edu.vn.ictflickr.Utils.UniversalImageLoader;

/**
 * Created by NghiaLe on 11/28/2017.
 */

public class AlbumsDetailActivity extends AppCompatActivity {
    private String albumId;
    private String albumTitle;
    private String albumImageCount;
    private String albumPrimaryImageURL;
    private Integer position;
    private TextView title;
    private TextView imageCount;
    private ImageView primaryImage;
    private ProgressBar albumProgressBar;
    private String imageURL;
    private ImageView backButton;
    private ArrayList<String> imageURLs = new ArrayList<>();
    FlickrClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_detail_item);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        client = FlickrClientApp.getRestClient();
        setupActivityWidgets();
        setAlbumItems();
        loadAlbumDetailItems();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_to_right, R.anim.slide_right_to_left);
            }
        });
    }

    private void setupActivityWidgets() {
        title = (TextView) findViewById(R.id.albumDetailTitle);
        imageCount = (TextView) findViewById(R.id.albumDetailImageCount);
        primaryImage = (ImageView) findViewById(R.id.albumDetailPrimaryImage);
        backButton = (ImageView) findViewById(R.id.albumDetailBackButton);
        albumProgressBar = new ProgressBar(this);
        albumProgressBar.setVisibility(View.GONE);
    }

    private void setAlbumItems() {
        Bundle args1 = getIntent().getExtras();
        albumId = args1.getString("AlbumId");
        albumTitle = args1.getString("AlbumTitle");
        albumImageCount = args1.getString("AlbumImageCount");
        albumPrimaryImageURL = args1.getString("AlbumPrimaryImageURL");
        position = args1.getInt("position");
        UniversalImageLoader.setImage(albumPrimaryImageURL, primaryImage, albumProgressBar);
        title.setText(albumTitle);
        imageCount.setText(albumImageCount);
        Log.d("DEBUG", "setAlbumItems: " + albumId);
    }

    private void setImageGrid(final ArrayList<String> imageURLs){
        GridView gridView = (GridView) findViewById(R.id.gridViewAlbumDetail);
        GridImageAdapter gridAdapter = new GridImageAdapter(AlbumsDetailActivity.this,R.layout.layout_grid_image_view,imageURLs);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/3;
        gridView.setColumnWidth(imageWidth);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumsDetailActivity.this, FullScreenImageViewActivity.class);
                intent.putExtra("ImageURL", imageURLs.get(position));
                intent.putExtra("position", position);
                intent.putStringArrayListExtra("ImageList", imageURLs);
                startActivity(intent);
            }
        });
    }

    private void loadAlbumDetailItems() {
        client.getAlbumDetailItems(SplashActivity.userId, albumId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject json) {
                Log.d("DEBUG", "result: " + json.toString());
                Log.d("DEBUG", "onSuccess: " + albumId);
                imageURLs.clear();
                try {
                    JSONArray photos = json.getJSONObject("photoset").getJSONArray("photo");
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
