package com.echances.etouches.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;

/**
 * 
 * @file WebServiceApiImp.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @date 16/04/2015
 * @brief * interface that contains all web services methods.
 * @details *
 * 
 */

public interface WebServiceApi {

	
	/**
	 * call login web service
	 * 
	 * @param login
	 *            user login
	 * @param password
	 *            user password
	 * @param webServiceWaitingListener
	 *            the call back of web service request
	 */
	public void Login(String login, String password,
			WebServiceWaitingListener webServiceWaitingListener);

	/**
	 * call Subscription web service
	 * 
	 * @param login
	 *            user login
	 * @param password
	 *            user password
	 * @param webServiceWaitingListener
	 *            the call back of web service request
	 */
	public void Subscription (String username, String password,
			String mobile, int type,
			WebServiceWaitingListener webServiceWaitingListener);

	
	public void GetServices (WebServiceWaitingListener webServiceWaitingListener);
	
	public void GetProviderServices(String userId, WebServiceWaitingListener webServiceWaitingListener);
	
	public void GetOneServices(String userId, String serviceId, WebServiceWaitingListener webServiceWaitingListener);
	
	public void AddService(String userId, String serviceId, String p, String h, String aar, String aen,
			WebServiceWaitingListener webServiceWaitingListener);
	
	public void UpdateService(String userId, String serviceId, String p, String h, String aar, String aen,
			WebServiceWaitingListener webServiceWaitingListener);
	
	public void DeleteImage(String id, String name,
			WebServiceWaitingListener webServiceWaitingListener);
	
	public void ForgetPassword(String mbNumber,
			WebServiceWaitingListener webServiceWaitingListener);
	
	public void GetSchedule(String userId,
			WebServiceWaitingListener webServiceWaitingListener);
	
	public void GetProfile (String userId, WebServiceWaitingListener webServiceWaitingListener);
	
	public void EditProfile (String userId, String un, String mob, String in, String out, String male, String female, String cash, WebServiceWaitingListener webServiceWaitingListener);
	
	public void UpdateLocation (String uid, String lng, String lat, WebServiceWaitingListener webServiceWaitingListener);
	
	/**
	 * the call back of web service request interface
	 * 
	 * @author ahmed.ghalleb
	 * 
	 */
	public interface WebServiceWaitingListener {
		/**
		 * method called on start web service request
		 */
		public void OnWebServiceWait();

		/**
		 * 
		 * @param value
		 */
		public void OnWebServiceProgress(float value);

		/**
		 * method called on end web service request
		 * 
		 * @param statut
		 *            state of request
		 * @param message
		 *            error message if exist
		 * @param data
		 *            object that contain the result of request
		 */
		public void OnWebServiceEnd(boolean statut, String message, Object data);

	}

	
}
