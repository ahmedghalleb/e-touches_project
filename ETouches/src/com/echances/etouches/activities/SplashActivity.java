package com.echances.etouches.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.echances.etouches.R;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.application.EtouchesApplicationCache;

/**
 * 
 * @file SplashActivity.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from BaseActivity.
 * @details *
 * 
 */
public class SplashActivity extends BaseActivity{

    /** Splash time  */
    private int	_splashTime	= 2000;


    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (Exception e) {
            // TODO: handle exception
        	e.printStackTrace();
        }


        setContentView(R.layout.activity_splash);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run ()
            {
            	if(EtouchesApplicationCache.getInstance().isConnected())
            		startActivity(new Intent(SplashActivity.this, MainActivity.class));
            	else
            		startActivity(new Intent(SplashActivity.this, ConnectionActivity.class));
            	
                finish();

            }
        }, _splashTime);
        
       
    }

}
