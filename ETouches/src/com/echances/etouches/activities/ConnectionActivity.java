package com.echances.etouches.activities;

import com.echances.etouches.R;
import com.echances.etouches.fragments.LoginFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * 
 * @file ConnectionActivity.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from BaseActivity.
 * @details *
 * 
 */
public class ConnectionActivity extends BaseActivity
{

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		getSupportActionBar().hide();
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		LoginFragment fragment = new LoginFragment();
		t.replace(R.id.content_frame, fragment);
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
			ft.addToBackStack(className);
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();
		}

	}

	@Override
	protected void attachBaseContext (Context newBase)
	{
		super.attachBaseContext(new CalligraphyContextWrapper(newBase));
	}

}
