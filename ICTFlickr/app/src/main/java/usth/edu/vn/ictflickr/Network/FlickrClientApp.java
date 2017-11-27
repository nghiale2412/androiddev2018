package usth.edu.vn.ictflickr.Network;

import android.app.Application;
import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by NghiaLe on 11/24/2017.
 */

public class FlickrClientApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        FlickrClientApp.context = this;

/*
        FlowManager.init(new FlowConfig.Builder(this).build());
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V); */
    }

    public static FlickrClient getRestClient() {
        return (FlickrClient) FlickrClient.getInstance(FlickrClient.class, FlickrClientApp.context);
    }
}