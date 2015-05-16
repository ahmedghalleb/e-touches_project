package com.echances.etouches.fragments;

import com.echances.etouches.utilities.Logr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @file BaseFragment.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from Fragment.
 * @details *
 * 
 */
public class BaseFragment extends Fragment
{

	/* Check if fragment is the current fragment running in the screen */
	private boolean			isInFront;

	private Activity	mParentActivity;

	public boolean isActivityRunning ()
	{
		if (isInFront && !getActivity().isFinishing())
			return true;
		else
			return false;
	}

	/**
	 * Method Constructor
	 */
	public BaseFragment()
	{
		super();
	}

	public static final String	TAG	= BaseFragment.class.getName();

	@Override
	public void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Logr.v("onCreate--" + getClass().getName());
		if (savedInstanceState != null) {
			Logr.v("onRestoreInstanceState");
			Logr.v("*****");
		}
		if (getArguments() != null) {
			Logr.v("GetArguement");
			Logr.v("*****");
		}

	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Logr.v("onCreateView--" + getClass().getName());
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onActivityCreated (Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Logr.v("onActivityCreated--" + getClass().getName());
	}

	@Override
	public void onStart ()
	{

		Logr.v("onStart--" + getClass().getName());
		super.onStart();
	}

	@Override
	public void onResume ()
	{
		super.onResume();
		isInFront = true;
		Logr.v("onResume--" + getClass().getName());
	}

	@Override
	public void onPause ()
	{
		super.onPause();
		isInFront = false;
	}

	@Override
	public void onStop ()
	{
		super.onStop();
		Logr.v("onStop--" + getClass().getName());

	}

	@Override
	public void onDestroy ()
	{
		super.onDestroy();
		Logr.v("onDestroy--" + getClass().getName());
		Logr.v("----------------------------");
	}

	@Override
	public void onSaveInstanceState (Bundle outState)
	{
		Logr.v("*****");
		Logr.v("onSaveInstanceState--" + getClass().getName());
		Logr.v("*****");
		super.onSaveInstanceState(outState);
	}

	/**
	 * Check is fragment run on foreground
	 * 
	 * @return fragment status
	 */
	public boolean isFragmentRunning ()
	{
		if (isInFront && !getActivity().isFinishing())
			return true;
		else
			return false;
	}

	/**
	 * Check is fragment run on foreground this method use isResumed and
	 * isFragmentRunning methods
	 * 
	 * @return fragment status
	 */
	public boolean isMyFragResumed ()
	{
		// only for HONEYCOMB and newer versions
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			return isResumed();
		}
		else {
			return isFragmentRunning();
		}
	}

	public boolean onKeyDown ()
	{
		return true;
	}

	// abstract methods
	public void onResumeFragment ()
	{

		Logr.i("BaseFragment : onResumeFragment");
	}

}
