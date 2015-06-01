package com.echances.etouches.application;


import com.echances.etouches.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
/**
 * 
 * @file EchouchesApplication.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * Echouches Application Class.
 * @details *
 */
public class EchouchesApplication extends Application {
    /**
     * the application context
     */
    public static Context context;

    @Override
    public void onCreate() {
        //CalligraphyConfig.initDefault("Fonts/Lato-Regular.ttf", R.attr.fontPath);
        super.onCreate();

        context = getApplicationContext();

        initImageLoader(getApplicationContext());
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
    /**
     * Check the Internet connection
     * @return Internet connection status
     */
    public static boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v("checkInternetConnection", "Internet Connection Not Present");
            return false;
        }
    }

}
