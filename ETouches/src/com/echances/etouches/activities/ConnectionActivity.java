package com.echances.etouches.activities;

import java.lang.reflect.Field;

import com.echances.etouches.R;
import com.echances.etouches.fragments.BaseFragment;
import com.echances.etouches.fragments.LoginFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * 
 * @file ConnectionActivity.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from BaseActivity.
 * @details *
 * 
 */
public class ConnectionActivity extends TwitterActivity
{

	ImageView mLeftImageView;
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		
		LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
		
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		
		mLeftImageView = (ImageView) mCustomView.findViewById(R.id.leftImageView);
		
		mLeftImageView.setVisibility(View.VISIBLE);
		
		mLeftImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popBackStack();
			}
		});
		
		showHideActionBar(false);
		
		// Add LoginFragment
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		LoginFragment fragment = new LoginFragment();
		t.replace(R.id.content_frame, fragment, fragment.getClass().getName());
		t.addToBackStack(fragment.getClass().getName());
		t.commit();		

	}

	/**
	 * Add new fragment we must call addToBackStak
	 * 
	 * @param fragment
	 */
	public void addContent (android.support.v4.app.Fragment fragment, String fragmentTag)
	{

		final String className = fragment.getClass().getName();

		// if (classNameTmp != null && className.equals(classNameTmp))
		// return;

		FragmentManager fragmentManager = getSupportFragmentManager();

		// ft.executePendingTransactions();

		if (fragmentManager.findFragmentByTag(className + fragmentTag) != null) {
			// duplicate fragment exit
			// nothing to do
			Log.i("ConnectionActivity", "fragmentTag already exist use an other tag");
		}
		else {
			// add new Fragment
			FragmentTransaction ft = fragmentManager.beginTransaction();
//			ft.setCustomAnimations(R.anim.in_from_right_animation, 0, 0,
//					R.anim.out_from_left_animation);

			ft.add(R.id.content_frame, fragment, className + fragmentTag);
			ft.addToBackStack(className + fragmentTag);
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}

	}
	
	public void addFragmentWithHorizAnimation(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();        
        //fragmentTransaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.content_frame, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName()); 
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }
	
	public void popBackStack(){
		Log.i("ConnectionActivity", "popBackStack");
		
		int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
		
		if(backStackEntryCount < 2){
			finish();
			return;
		}
			
		getSupportFragmentManager().popBackStack();
		Fragment baseFragment = getSupportFragmentManager().findFragmentByTag(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-2).getName());
		if (baseFragment == null) {
			Log.i("ConnectionActivity", "baseFragment == null");
			return;
		}
		if (baseFragment instanceof BaseFragment) {
			((BaseFragment) baseFragment).onResume();
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		popBackStack();
	}
	
	public void showHideActionBar(boolean isShow){
		if(isShow)
			getSupportActionBar().show();
		else
			getSupportActionBar().hide();
		
		disableShowHideAnimation(getSupportActionBar());
	}
	
	public static void disableShowHideAnimation(ActionBar actionBar) {
	    try
	    {
	        actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
	    }
	    catch (Exception exception)
	    {
	        try {
	            Field mActionBarField = actionBar.getClass().getSuperclass().getDeclaredField("mActionBar");
	            mActionBarField.setAccessible(true);
	            Object icsActionBar = mActionBarField.get(actionBar);
	            Field mShowHideAnimationEnabledField = icsActionBar.getClass().getDeclaredField("mShowHideAnimationEnabled");
	            mShowHideAnimationEnabledField.setAccessible(true);
	            mShowHideAnimationEnabledField.set(icsActionBar,false);
	            Field mCurrentShowAnimField = icsActionBar.getClass().getDeclaredField("mCurrentShowAnim");
	            mCurrentShowAnimField.setAccessible(true);
	            mCurrentShowAnimField.set(icsActionBar,null);
	        }catch (Exception e){
	            //....
	        	e.printStackTrace();
	        }
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
