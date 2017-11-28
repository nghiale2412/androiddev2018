package usth.edu.vn.ictflickr.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.models.Photo;

/**
 * Created by NghiaLe on 11/26/2017.
 */

public class NewsFeedAdapter extends ArrayAdapter<Photo> {

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private ArrayList<String> imageURLs;

    public NewsFeedAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Photo> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
    }

    static class ViewHolder{
        CircleImageView mProfileImage;
        TextView user_real_name, title;
        ImageView imageView;
        ImageView favoriteWhite, favoriteBlue, comment, share;
        GestureDetector detector;
        Favorite favorite;
        ProgressBar mProgressBar;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            convertView = mInflater.inflate(mLayoutResource,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.feedProgressBar);
            viewHolder.title = (TextView) convertView.findViewById(R.id.imageTitle);
            viewHolder.user_real_name = (TextView) convertView.findViewById(R.id.contactName);
            viewHolder.mProfileImage = (CircleImageView) convertView.findViewById(R.id.contactProfileImage);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.contactImage);
            viewHolder.favoriteWhite = (ImageView) convertView.findViewById(R.id.favoriteIcon);
            viewHolder.favoriteBlue = (ImageView) convertView.findViewById(R.id.favoriteIconBlue);
            viewHolder.comment = (ImageView) convertView.findViewById(R.id.commentIcon);
            viewHolder.share = (ImageView) convertView.findViewById(R.id.shareIcon);
            viewHolder.favorite = new Favorite(viewHolder.favoriteWhite, viewHolder.favoriteBlue);
            viewHolder.detector = new GestureDetector(mContext, new GestureListener(viewHolder));

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.favoriteBlue.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewHolder.detector.onTouchEvent(event);
            }
        });
        viewHolder.favoriteWhite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewHolder.detector.onTouchEvent(event);
            }
        });

        viewHolder.favoriteWhite.setVisibility(View.VISIBLE);
        viewHolder.favoriteBlue.setVisibility(View.GONE);
        viewHolder.mProgressBar.setVisibility(View.GONE);
        viewHolder.user_real_name.setText(getItem(position).getUser_name());
        viewHolder.title.setText(getItem(position).getTitle());
        String imageURL = getItem(position).getImageURL();
        String profileImageURL = getItem(position).getUser_profile_image();
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(profileImageURL, viewHolder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        imageLoader.displayImage(imageURL, viewHolder.imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }
        });


        return convertView;
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        ViewHolder mHolder;
        public GestureListener(ViewHolder viewHolder){
            mHolder = viewHolder;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mHolder.favorite.toggleFavorite();
            return true;
        }
    }
}
