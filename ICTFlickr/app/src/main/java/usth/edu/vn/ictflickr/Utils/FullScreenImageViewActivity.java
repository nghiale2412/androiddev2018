package usth.edu.vn.ictflickr.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import usth.edu.vn.ictflickr.R;

/**
 * Created by Nghia le on 11/17/2017.
 */

public class FullScreenImageViewActivity extends AppCompatActivity {
    private static final String TAG = "FullScreenImageViewActi";
    private PhotoView fullScreenImage;
    private ProgressBar fullScreenProgressBar;
    private ViewPager viewPager;
    private String imageURL;
    private int position;
    private ArrayList<String> imageList = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_view);
        setupActivityWidgets();
        setFullScreenImage();
        List<PhotoView> images = new ArrayList<PhotoView>();
        for (int i = 0; i < imageList.size(); i++) {
            fullScreenProgressBar = new ProgressBar(this);
            fullScreenProgressBar.setVisibility(View.GONE);
            fullScreenImage = new PhotoView(this);
            imageURL = imageList.get(i);
            UniversalImageLoader.setImage(imageURL, fullScreenImage, fullScreenProgressBar);
            images.add(fullScreenImage);
        }
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(images);
        ViewPager viewPager = (ViewPager) findViewById(R.id.fullScreenPager);
        viewPager.setAdapter(imagePagerAdapter);
        viewPager.setCurrentItem(position);
        ImageView closeButton = (ImageView) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Closing Full Screen View");
                finish();
            }
        });
    }

    private void setFullScreenImage() {
        Log.d(TAG, "setProfileImage: set profile image");
        Bundle args = getIntent().getExtras();
        imageURL = args.getString("ImageURL");
        position = args.getInt("position");
        imageList = args.getStringArrayList("ImageList");
        UniversalImageLoader.setImage(imageURL, fullScreenImage, fullScreenProgressBar);
    }

    private void setupActivityWidgets() {
        fullScreenProgressBar = new ProgressBar(this);
        fullScreenProgressBar.setVisibility(View.GONE);
        fullScreenImage = new PhotoView(this);
    }

    public class ImagePagerAdapter extends PagerAdapter {

        private List<PhotoView> images;

        public ImagePagerAdapter(List<PhotoView> images) {
            this.images = images;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = images.get(position);
            container.addView(photoView);
            PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);
            attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    Log.d(TAG, "onViewTap: touching screen");
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.relLayout1Full);
                    if (layout.getVisibility() == view.VISIBLE) {
                        layout.setVisibility(view.GONE);
                    } else layout.setVisibility(view.VISIBLE);
                }
            });
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}

