package com.echances.etouches.application;

import com.echances.etouches.R;
import com.echances.etouches.utilities.Constants;
import com.echances.etouches.utilities.Constants.Params;
import com.echances.etouches.utilities.Constants.PreferencesCache;
import com.echances.etouches.utilities.DialogsModels;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @file EtouchesApplicationCache.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * Etouches Application cache class.
 * @details *
 */

public class EtouchesApplicationCache
{
	/**
	 * instance of EtouchesApplicationCache class
	 */
	private static EtouchesApplicationCache	instance	= new EtouchesApplicationCache();

	/**
	 * token from web service
	 */
	private String								token;
	/**
	 * current context cached
	 */
	private Context								currentContext;
	/**
	 * is first run cached variable
	 */
	private boolean								isFirstRun	= true;
	/**
	 * is refresh response variable cached
	 */
	private boolean								isRefreshResponse;
	/**
	 * progress dialog object for all the application
	 */
	ProgressDialog								mProgressDialog;
	
	
	public DisplayImageOptions options = new DisplayImageOptions.Builder()
    .showStubImage(R.drawable.ic_launcher)
    .cacheInMemory()
    .cacheOnDisc()
    .build();

	private EtouchesApplicationCache()
	{

	}

	/**
	 * static method to get the instance of DigiSchoolApplicationCache
	 * 
	 * @return the curent instance object
	 */
	public static EtouchesApplicationCache getInstance ()
	{
		if (instance == null)
			instance = new EtouchesApplicationCache();
		return instance;
	}

	/**
	 * get the progress dialog of the application
	 * 
	 * @return progress dialog object
	 */
	public ProgressDialog getmProgressDialog (String fragmentname)
	{

//		if (mProgressDialog == null)
//			mProgressDialog = DialogsModels.ProgressingDialog(currentContext, "Loading");
//		else {
//
//			if (!mProgressDialog.getContext().equals(currentContext)) {
//				if (mProgressDialog.isShowing())
//					mProgressDialog.dismiss();
//				mProgressDialog = DialogsModels.ProgressingDialog(currentContext, currentContext.getString(R.string.dialog_loading));
//			}
//
//		}
		return mProgressDialog;
	}

	/**
	 * get current context
	 * 
	 * @return the current context object
	 */
	public Context getCurrentContext ()
	{
		return currentContext;
	}

	/**
	 * set current context
	 * 
	 * @param currentContext
	 */
	public void setCurrentContext (Context currentContext)
	{
		this.currentContext = currentContext;
	}

	/**
	 * get the user token
	 * 
	 * @return
	 */
	public String getToken ()
	{
		if (token == null || token.equals("")) {
			// get data from pref
			//token = getClientDataFromPreferences(PreferencesCache.USER_TOKEN);
		}
		return token;
	}

	/**
	 * set the user token
	 * 
	 * @param token
	 */
	public void setToken (String token)
	{
		this.token = token;
		// save data in pref
		putDataInPreferences(PreferencesCache.USER_TOKEN, token);
	}

	/**
	 * get is first run variable
	 * 
	 * @return is first run boolean
	 */
	public Boolean getIsFirstRun ()
	{
		if (isFirstRun) {
			// get data from pref
			//String first = getClientDataFromPreferences(PreferencesCache.IS_FIRST_RUN);
//			if (first == null || first.equals("")) {
//				isFirstRun = true;
//			}
//			else {
//				isFirstRun = false;
//			}
		}
		return isFirstRun;
	}

	/**
	 * set is first run variable to false
	 */
	public void setIsFirstRun ()
	{
		isFirstRun = false;
		putDataInPreferences(PreferencesCache.IS_FIRST_RUN, "OK");
	}

	/**
	 * get is refresh responses or not
	 * 
	 * @return refresh responses state
	 */
	public boolean isRefreshResponse ()
	{
		return isRefreshResponse;
	}

	/**
	 * set is refresh responses
	 * 
	 * @param isRefreshResponse
	 */
	public void setRefreshResponse (boolean isRefreshResponse)
	{
		this.isRefreshResponse = isRefreshResponse;
	}


	/**
	 * check if the user is connected or not
	 * 
	 * @return user is connected or not
	 */
	public boolean isConnected ()
	{
		return (getUserId() != 0);
	}

	/**
	 * put data in the preferences
	 * 
	 * @param key
	 *            key of data
	 * @param value
	 *            value of data
	 * @returns
	 */
	public static void putDataInPreferences (String key, String value)
	{
		initiateSharedPreferences().edit().putString(key, value).commit();
	}

	public void saveUserId (int id)
	{
		initiateSharedPreferences().edit().putInt(Constants.PreferencesCache.USER_ID, id).commit();
	}
	
	public int getUserId(){
		return initiateSharedPreferences().getInt(Constants.PreferencesCache.USER_ID, 0);
	}
	
	/**
	 * init the preferences
	 * 
	 * @param
	 * @returns the SharedPreferences.
	 */
	private static SharedPreferences initiateSharedPreferences ()
	{
		return EchouchesApplication.context.getSharedPreferences(
				Params.PREFS_NAME, 0);
	}
}
