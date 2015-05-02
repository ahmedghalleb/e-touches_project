package com.echances.etouches.application;


import com.echances.etouches.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
/**
 * 
 * @file DigiSchoolApplication.java
 * @author Bilel Gaabel
 * @version 1.0
 * @brief * DigiSchool Application Class.
 * @details *
 */
public class EchouchesApplication extends Application {
    /**
     * the application context
     */
    public static Context context;

    @Override
    public void onCreate() {
        CalligraphyConfig.initDefault("Fonts/Lato-Regular.ttf", R.attr.fontPath);
        super.onCreate();

        context = getApplicationContext();

    }

    /**
     * Check the Internet connection
     * @return Internet connection status
     */
    public static boolean checkInternetConnection() {
    	return true;
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        // test for connection
//        if (cm.getActiveNetworkInfo() != null
//                && cm.getActiveNetworkInfo().isAvailable()
//                && cm.getActiveNetworkInfo().isConnected()) {
//            return true;
//        } else {
//            Log.v("checkInternetConnection", "Internet Connection Not Present");
//            return false;
//        }
    }

}
