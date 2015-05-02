package com.echances.etouches.api;

import java.util.HashMap;
import java.util.Map.Entry;

import android.util.Log;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.application.EchouchesApplication;
import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.Constants.HTTPConstantParams;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.LoginRequest;
import com.echances.etouches.webservices.SubscriptionRequest;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * 
 * @file WebServiceApiImp.java
 * @author Bilel Gaabel
 * @version 1.0
 * @date 11/08/2014
 * @brief * class that contains the implementation of all web services call
 *        methods .
 * @details *
 * 
 */

public class WebServiceApiImp implements WebServiceApi
{

	/**
	 * the base activity from it the request is called
	 */
	private BaseActivity				mActivity;
	/**
	 * the listener of web services requests
	 */
	private WebServiceWaitingListener	mValidateUserWebServiceWaitingListener;

	/**
	 * the instance of WebServiceApiImp class
	 */
	private static WebServiceApiImp		instance;

	public static WebServiceApiImp getInstance (BaseActivity activity)
	{
		if (instance == null) {
			instance = new WebServiceApiImp();
		}
		instance.mActivity = activity;
		return instance;
	}

	@Override
	public void Login (String login, String password,
			WebServiceWaitingListener webServiceWaitingListener)
	{
		mValidateUserWebServiceWaitingListener = webServiceWaitingListener;
		mValidateUserWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put(HTTPConstantParams.LOGIN, login);
			params.put(HTTPConstantParams.PASSWORD, password);
			
			params.put(HTTPConstantParams.LANG,
					ParamsWebService.LANG);
			LoginRequest mLoginRequest = new LoginRequest(params);
			mActivity.getSpiceManager()
					.execute(
							mLoginRequest,
							generateCacheKey(LoginRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new LoginRequestListener());
		}
		else {
			mValidateUserWebServiceWaitingListener.OnWebServiceEnd(false,
					"WEB_SERVICE_NO_INTERNET",
					null);
		}
	}

	@Override
	public void Subscription (String username, String password,
			String mobile, int type,
			WebServiceWaitingListener webServiceWaitingListener)
	{
		mValidateUserWebServiceWaitingListener = webServiceWaitingListener;
		mValidateUserWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put(HTTPConstantParams.USERNAME, username);
			params.put(HTTPConstantParams.PASSWORD, password);
			params.put(HTTPConstantParams.MOBILE, mobile);
			params.put(HTTPConstantParams.TYPE, "" + type);
			

			SubscriptionRequest mSubscriptionRequest = new SubscriptionRequest(
					params);
			mActivity.getSpiceManager()
					.execute(
							mSubscriptionRequest,
							generateCacheKey(SubscriptionRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new LoginRequestListener());
		}
		else {
			mValidateUserWebServiceWaitingListener.OnWebServiceEnd(false,
					"WEB_SERVICE_NO_INTERNET",
					null);
		}
	}

	// listener
	private class LoginRequestListener implements RequestListener<Response>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mValidateUserWebServiceWaitingListener != null)
				mValidateUserWebServiceWaitingListener.OnWebServiceEnd(false,
						"WEB_SERVICE_ON_FAIL",
						null);
		}

		@Override
		public void onRequestSuccess (Response result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mValidateUserWebServiceWaitingListener != null)
					mValidateUserWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mValidateUserWebServiceWaitingListener != null)
					mValidateUserWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}

	
	/**
	 * 	 * Generate a key that will be used in caching Web service response,The key
	 * must be one for each request
	 */
	public String generateCacheKey (String name, HashMap<String, String> params)
	{
		String key = name + ":";
		if (params != null)
			for (Entry<String, String> entry : params.entrySet()) {
				key += "(" + entry.getKey() + "=" + entry.getValue() + ")";

			}

		return key;
	}


}
