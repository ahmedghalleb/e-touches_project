package com.echances.etouches.activities;

import java.io.InputStream;

import com.echances.etouches.R;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterActivity extends BaseActivity{
	
	/* Shared preference keys */
	private static final String PREF_NAME = "sample_twitter_pref";
	private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
	private static final String PREF_USER_NAME = "twitter_user_name";

	/* Any number for uniquely distinguish your request */
	public static final int WEBVIEW_REQUEST_CODE = 100;

	private ProgressDialog pDialog;

	private static Twitter twitter;
	private static RequestToken requestToken;
	
	private static SharedPreferences mSharedPreferences;

	private String consumerKey = null;
	private String consumerSecret = null;
	private String callbackUrl = null;
	private String oAuthVerifier = null;
	
	TwitterCallBack mTwitterCallBack;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* initializing twitter parameters from string.xml */
		initTwitterConfigs();

		/* Enabling strict mode */
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		/* Check if required twitter keys are set */
		if (TextUtils.isEmpty(consumerKey) || TextUtils.isEmpty(consumerSecret)) {
			Toast.makeText(this, "Twitter key and secret not configured",
					Toast.LENGTH_SHORT).show();
			return;
		}

		/* Initialize application preferences */
		mSharedPreferences = getSharedPreferences(PREF_NAME, 0);

		boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
		
		/*  if already logged in, then hide login layout and show share layout */
		if (isLoggedIn) {
			
			String username = mSharedPreferences.getString(PREF_USER_NAME, "");

		} else {

			Uri uri = getIntent().getData();
			
			if (uri != null && uri.toString().startsWith(callbackUrl)) {
			
				String verifier = uri.getQueryParameter(oAuthVerifier);

				try {
					
					/* Getting oAuth authentication token */
					AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

					/* Getting user id form access token */
					long userID = accessToken.getUserId();
					final User user = twitter.showUser(userID);
					final String username = user.getName();

					/* save updated token */
					saveTwitterInfo(accessToken);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	
	/**
	 * Saving user information, after user is authenticated for the first time.
	 * You don't need to show user to login, until user has a valid access toen
	 */
	private void saveTwitterInfo(AccessToken accessToken) {
		
		long userID = accessToken.getUserId();
		
		User user;
		try {
			user = twitter.showUser(userID);
		
			String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
			Editor e = mSharedPreferences.edit();
			e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
			e.putString(PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
			e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
			e.putString(PREF_USER_NAME, username);
			e.commit();

		} catch (TwitterException e1) {
			e1.printStackTrace();
		}
	}

	/* Reading twitter essential configuration parameters from strings.xml */
	private void initTwitterConfigs() {
		consumerKey = getString(R.string.twitter_consumer_key);
		consumerSecret = getString(R.string.twitter_consumer_secret);
		callbackUrl = getString(R.string.twitter_callback);
		oAuthVerifier = getString(R.string.twitter_oauth_verifier);
	}

	
	private void loginToTwitter() {
		boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
		
		if (!isLoggedIn) {
			final ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);

			final Configuration configuration = builder.build();
			final TwitterFactory factory = new TwitterFactory(configuration);
			twitter = factory.getInstance();

			try {
				requestToken = twitter.getOAuthRequestToken(callbackUrl);

				/**
				 *  Loading twitter login page on webview for authorization 
				 *  Once authorized, results are received at onActivityResult
				 *  */
				final Intent intent = new Intent(this, WebViewActivity.class);
				intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
				startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
				
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {


		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			String verifier = data.getExtras().getString(oAuthVerifier);
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);

				long userID = accessToken.getUserId();
				final User user = twitter.showUser(userID);
				String username = user.getName();
				
				saveTwitterInfo(accessToken);
				
				if(mTwitterCallBack != null)
					mTwitterCallBack.GetUserNameSuccess(username);

				//TwitterAuthClient

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	class updateTwitterStatus extends AsyncTask<String, String, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			pDialog = new ProgressDialog(TwitterActivity.this);
			pDialog.setMessage("Posting to twitter...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(String... args) {

			String status = args[0];
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(consumerKey);
				builder.setOAuthConsumerSecret(consumerSecret);
				
				// Access Token
				String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
				// Access Token Secret
				String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");

				AccessToken accessToken = new AccessToken(access_token, access_token_secret);
				Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

				// Update status
				StatusUpdate statusUpdate = new StatusUpdate(status);
//				InputStream is = getResources().openRawResource(R.drawable.lakeside_view);
//				statusUpdate.setMedia("test.jpg", is);
				
				twitter4j.Status response = twitter.updateStatus(statusUpdate);

				Log.d("Status", response.getText());
				
			} catch (TwitterException e) {
				Log.d("Failed to post!", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			/* Dismiss the progress dialog after sharing */
			pDialog.dismiss();
			
			Toast.makeText(TwitterActivity.this, "Posted to Twitter!", Toast.LENGTH_SHORT).show();

		}

	}
	
	public void getUsername(TwitterCallBack twitterCallBack){
		
		mTwitterCallBack = twitterCallBack;
		
		boolean isLoggedIn = mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
		
		if(isLoggedIn){
			mTwitterCallBack.GetUserNameSuccess(mSharedPreferences.getString(PREF_USER_NAME, ""));
		}else{
			loginToTwitter();
		}
		
	}
	
	public interface TwitterCallBack{
		void GetUserNameSuccess(String username);
		void GetUserNameError();
	}
}