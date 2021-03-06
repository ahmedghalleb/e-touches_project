package com.echances.etouches.api;

import java.util.HashMap;
import java.util.Map.Entry;

import android.util.Log;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EchouchesApplication;
import com.echances.etouches.model.GetOneServiceResponse;
import com.echances.etouches.model.GetProfileResponse;
import com.echances.etouches.model.GetServicesResponse;
import com.echances.etouches.model.LoginResponse;
import com.echances.etouches.model.Response;
import com.echances.etouches.model.ScheduleResponse;
import com.echances.etouches.utilities.Constants.HTTPConstantParams;
import com.echances.etouches.utilities.Constants.ParamsWebService;
import com.echances.etouches.webservices.AddServiceRequest;
import com.echances.etouches.webservices.ForgetPasswordRequest;
import com.echances.etouches.webservices.GetOneServicesRequest;
import com.echances.etouches.webservices.GetProfileRequest;
import com.echances.etouches.webservices.GetProviderServicesRequest;
import com.echances.etouches.webservices.GetServicesRequest;
import com.echances.etouches.webservices.LoginRequest;
import com.echances.etouches.webservices.RemoveImageRequest;
import com.echances.etouches.webservices.ScheduleRequest;
import com.echances.etouches.webservices.SubscriptionRequest;
import com.echances.etouches.webservices.UpdateLocationRequest;
import com.echances.etouches.webservices.UpdateProfileRequest;
import com.echances.etouches.webservices.UpdateServiceRequest;
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
	private WebServiceWaitingListener	mWebServiceWaitingListener;

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
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put(HTTPConstantParams.MOBILE, login);
			params.put(HTTPConstantParams.PASSWORD, password);
			
			LoginRequest mLoginRequest = new LoginRequest(params);
			mActivity.getSpiceManager()
					.execute(
							mLoginRequest,
							generateCacheKey(LoginRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new LoginRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}

	@Override
	public void Subscription (String username, String password,
			String mobile, int type,
			WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
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
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}

	@Override
	public void GetServices (WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			GetServicesRequest mGetServicesRequest = new GetServicesRequest(
					params);
			mActivity.getSpiceManager()
					.execute(
							mGetServicesRequest,
							generateCacheKey(GetServicesRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new GetServicesRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void GetProviderServices (String userId, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			
			GetProviderServicesRequest mGetServicesRequest = new GetProviderServicesRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mGetServicesRequest,generateCacheKey(GetProviderServicesRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new GetOneServiceRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void GetOneServices (String userId, String serviceId, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			params.put("sid", serviceId);
			
			GetOneServicesRequest mGetServicesRequest = new GetOneServicesRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mGetServicesRequest,generateCacheKey(GetOneServicesRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new GetOneServiceRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void AddService (String userId, String serviceId, String p, String h, String aar, String aen, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			params.put("sid", serviceId);
			params.put("p", p);
			params.put("h", h);
			params.put("aar", aar);
			params.put("aen", aen);
			
			AddServiceRequest mGetServicesRequest = new AddServiceRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mGetServicesRequest,generateCacheKey(AddServiceRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void UpdateService (String userId, String serviceId, String p, String h, String aar, String aen, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			params.put("sid", serviceId);
			params.put("p", p);
			params.put("h", h);
			params.put("aar", aar);
			params.put("aen", aen);
			
			UpdateServiceRequest mGetServicesRequest = new UpdateServiceRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mGetServicesRequest,generateCacheKey(UpdateServiceRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void DeleteImage (String id, String name, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("id", id);
			params.put("img", name);			

			RemoveImageRequest mRemoveImageRequest = new RemoveImageRequest(
					params);
			mActivity.getSpiceManager()
					.execute(
							mRemoveImageRequest,
							generateCacheKey(RemoveImageRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void ForgetPassword (String mbNumber, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("mb", mbNumber);

			ForgetPasswordRequest mForgetPasswordRequest = new ForgetPasswordRequest(
					params);
			mActivity.getSpiceManager()
					.execute(
							mForgetPasswordRequest,
							generateCacheKey(ForgetPasswordRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void GetSchedule (String userId, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			
			ScheduleRequest mScheduleRequest = new ScheduleRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mScheduleRequest,generateCacheKey(ScheduleRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new GetScheduleRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void GetProfile (String userId, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			
			GetProfileRequest mGetProfileRequest = new GetProfileRequest(
					params);
			mActivity.getSpiceManager()
					.execute(mGetProfileRequest,generateCacheKey(GetProfileRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new GetProfileRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void EditProfile (String userId, String un, String mob, String in, String out, String male, String female, String cash, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", userId);
			params.put("un", un);
			params.put("mob", mob);
			params.put("in", in);
			params.put("out", out);
			params.put("male", male);
			params.put("female", female);
			params.put("cash", cash);

			UpdateProfileRequest mUpdateProfileRequest = new UpdateProfileRequest(
					params);
			mActivity.getSpiceManager()
					.execute(
							mUpdateProfileRequest,
							generateCacheKey(UpdateProfileRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	@Override
	public void UpdateLocation (String uid, String lng, String lat, WebServiceWaitingListener webServiceWaitingListener)
	{
		mWebServiceWaitingListener = webServiceWaitingListener;
		mWebServiceWaitingListener.OnWebServiceWait();
		if (EchouchesApplication.checkInternetConnection()) {
			HashMap<String, String> params = new HashMap<String, String>();

			params.put("uid", uid);
			params.put("lng", lng);	
			params.put("lat", lat);	

			UpdateLocationRequest mUpdateLocationRequest = new UpdateLocationRequest(params);
			mActivity.getSpiceManager()
					.execute(
							mUpdateLocationRequest,
							generateCacheKey(RemoveImageRequest.class.getName(),
									params), DurationInMillis.ALWAYS_EXPIRED,
							new WSRequestListener());
		}
		else {
			mWebServiceWaitingListener.OnWebServiceEnd(false,
					"Check your Internet Connection",
					null);
		}
	}
	
	
	// ######################### listeners ####################################
	
	
	private class WSRequestListener implements RequestListener<Response>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to server",
						null);
		}

		@Override
		public void onRequestSuccess (Response result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}

	private class LoginRequestListener implements RequestListener<LoginResponse>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to  server",
						null);
		}

		@Override
		public void onRequestSuccess (LoginResponse result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}

	private class GetServicesRequestListener implements RequestListener<GetServicesResponse>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to  server",
						null);
		}

		@Override
		public void onRequestSuccess (GetServicesResponse result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}
	
	private class GetOneServiceRequestListener implements RequestListener<GetOneServiceResponse>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to server",
						null);
		}

		@Override
		public void onRequestSuccess (GetOneServiceResponse result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}
	
	private class GetScheduleRequestListener implements RequestListener<ScheduleResponse>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to server",
						null);
		}

		@Override
		public void onRequestSuccess (ScheduleResponse result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							false, result.getMessage(),
							result);
			}
		}
	}
	
	private class GetProfileRequestListener implements RequestListener<GetProfileResponse>
	{

		@Override
		public void onRequestFailure (SpiceException arg0)
		{

			if (mWebServiceWaitingListener != null)
				mWebServiceWaitingListener.OnWebServiceEnd(false,
						"Problem to connect to server",
						null);
		}

		@Override
		public void onRequestSuccess (GetProfileResponse result)
		{

			Log.e("WebServiceRetour", "getStatus= " + result.getCode());

			if (result.isSucces()) {

				Log.e("WebServiceRetour", "response = " + result);
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
							true, "", result);
			}
			else {
				Log.e("WebServiceRetour", "getErrorCode= " + result.getCode());
				if (mWebServiceWaitingListener != null)
					mWebServiceWaitingListener.OnWebServiceEnd(
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
