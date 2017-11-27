package usth.edu.vn.ictflickr.Utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by NghiaLe on 11/26/2017.
 */

public class Favorite {

    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    public ImageView favoriteWhite,favoriteBlue;

    public Favorite(ImageView favoriteWhite, ImageView favoriteBlue){
        this.favoriteWhite = favoriteWhite;
        this.favoriteBlue = favoriteBlue;
    }

    public void toggleFavorite(){
        AnimatorSet animationSet = new AnimatorSet();

        if(favoriteBlue.getVisibility() == View.VISIBLE){
            favoriteBlue.setScaleX(0.1f);
            favoriteBlue.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(favoriteBlue, "scaleY", 1f, 0f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(ACCELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(favoriteBlue, "scaleX", 1f, 0f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(ACCELERATE_INTERPOLATOR);

            favoriteBlue.setVisibility(View.GONE);
            favoriteWhite.setVisibility(View.VISIBLE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        }
        else if(favoriteBlue.getVisibility() == View.GONE){
            favoriteBlue.setScaleX(0.1f);
            favoriteBlue.setScaleY(0.1f);

            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(favoriteBlue, "scaleY", 0.1f, 1f);
            scaleDownY.setDuration(300);
            scaleDownY.setInterpolator(DECELERATE_INTERPOLATOR);

            ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(favoriteBlue, "scaleX", 0.1f, 1f);
            scaleDownX.setDuration(300);
            scaleDownX.setInterpolator(DECELERATE_INTERPOLATOR);

            favoriteBlue.setVisibility(View.VISIBLE);
            favoriteWhite.setVisibility(View.GONE);

            animationSet.playTogether(scaleDownY, scaleDownX);
        }

        animationSet.start();

    }

}
