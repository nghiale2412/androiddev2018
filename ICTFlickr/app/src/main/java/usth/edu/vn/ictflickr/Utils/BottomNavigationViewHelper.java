package usth.edu.vn.ictflickr.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import usth.edu.vn.ictflickr.Camera.CameraActivity;
import usth.edu.vn.ictflickr.Home.HomeActivity;
import usth.edu.vn.ictflickr.Notifications.NotificationsActivity;
import usth.edu.vn.ictflickr.R;
import usth.edu.vn.ictflickr.Search.SearchActivity;
import usth.edu.vn.ictflickr.UserProfile.UserProfileActivity;

/**
 * Created by Nghia le on 11/12/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationView){
        Log.d(TAG, "setupBottomNavigationView: ");
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.news: /* Activity Number 0 */
                        Intent intent0 = new Intent(context, HomeActivity.class);
                        context.startActivity(intent0);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.search: /* Activity Number 1 */
                        Intent intent1 = new Intent(context, SearchActivity.class);
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.camera: /* Activity Number 2 */
                        Intent intent2 = new Intent(context, CameraActivity.class);
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.notifications: /* Activity Number 3 */
                        Intent intent3 = new Intent(context, NotificationsActivity.class);
                        context.startActivity(intent3);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.profile: /* Activity Number 4 */
                        Intent intent4 = new Intent(context, UserProfileActivity.class);
                        context.startActivity(intent4);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }

                return false;
            }
        });
    }
}
