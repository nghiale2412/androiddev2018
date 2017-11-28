package usth.edu.vn.ictflickr.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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
import usth.edu.vn.ictflickr.models.Album;

/**
 * Created by NghiaLe on 11/28/2017.
 */

public class AlbumsListAdapter extends ArrayAdapter<Album> {
    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;

    public AlbumsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Album> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mLayoutResource = resource;
        this.mContext = context;
    }

    static class ViewHolder{
        ImageView primaryImage;
        TextView albumTitle, albumDescription, albumDate, albumCount;
        ProgressBar mProgressBar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.albumProgressBar);
            viewHolder.primaryImage = (ImageView) convertView.findViewById(R.id.albumPrimaryImage);
            viewHolder.albumTitle = (TextView) convertView.findViewById(R.id.albumTitle);
            viewHolder.albumDescription = (TextView) convertView.findViewById(R.id.albumDescription);
            viewHolder.albumDate = (TextView) convertView.findViewById(R.id.albumDateCrated);
            viewHolder.albumCount = (TextView) convertView.findViewById(R.id.imageCount);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.albumTitle.setText(getItem(position).getAlbumTitle());
        viewHolder.albumDescription.setText(getItem(position).getAlbumDescription());
        viewHolder.albumDate.setText(getItem(position).getAlbumDate());
        viewHolder.albumCount.setText(getItem(position).getImageCount());
        String primaryImageURL = getItem(position).getAlbumPrimaryImageURL();
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(primaryImageURL, viewHolder.primaryImage, new ImageLoadingListener() {
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
}
