package com.echances.etouches.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import br.com.dina.oauth.instagram.InstagramApp;

import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.utilities.Constants;
import com.echances.etouches.webservices.WebServiceEchancesService;
import com.octo.android.robospice.SpiceManager;

public class BaseActivity extends ActionBarActivity{
    // Declarer le service du RoboSpice (avec comme extension retrofit + okHttp)
    private SpiceManager spiceManager = new SpiceManager(WebServiceEchancesService.class);
    /**
     * @param isInFront
     *Check if Activity is the current activity running on the screen.
     */
    private boolean isInFront;
    /**
     * A method called to know if the current activity is running on the screen.
     * 
     * @returns the state of the current activity.
     */
    
    public InstagramApp mApp;
    
    public boolean isActivityRunning() {
        if (isInFront && !isFinishing())
            return true;
        else
            return false;
    }
    @Override
    protected void onStart ()
    {
        super.onStart();
        // Declencher le service du RoboSpice
        if (!spiceManager.isStarted()){
            spiceManager.start(getApplicationContext());
            Log.e("StartedSpice","StartedSpice");
        }
        
        mApp = new InstagramApp(this, Constants.Instagram.CLIENT_ID,
        		Constants.Instagram.CLIENT_SECRET, Constants.Instagram.CALLBACK_URL);
    }
    @Override
    protected void onStop() {
        if(EtouchesApplicationCache.getInstance().getmProgressDialog("BaseActivity") != null && EtouchesApplicationCache.getInstance().getmProgressDialog("BaseActivity").isShowing())
            EtouchesApplicationCache.getInstance().getmProgressDialog("BaseActivity").cancel();
        super.onStop();
    }
    @Override
    protected void onDestroy ()
    {
        // arreter le service du RoboSpice
        spiceManager.shouldStop();
        Log.e("onDestroySpice","onDestroySpice");
        super.onDestroy();
    }
    /**
     * A method called to get SpiceManager object for calling web services.
     * 
     * @returns the SpaceManager object.
     */
    public SpiceManager getSpiceManager() {
        if (!spiceManager.isStarted()){
            spiceManager.start(getApplicationContext());
            Log.e("onDestroySpice","onDestroySpice");
        }
        return spiceManager;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        isInFront = true;
        super.onResume();
        EtouchesApplicationCache.getInstance().setCurrentContext(this);

    }


    @Override
    protected void onPause() {
        isInFront = false;
        Log.e("BaseActivity", "onPause = "+this.toString());
        super.onPause();

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        EtouchesApplicationCache.getInstance().setCurrentContext(this);
    }

}
