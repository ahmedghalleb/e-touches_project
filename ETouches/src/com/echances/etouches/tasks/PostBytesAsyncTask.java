package com.echances.etouches.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class PostBytesAsyncTask extends AsyncTask<byte[], String, String>{

	String mUrl;

	public PostBytesAsyncTask(String url){
		mUrl = url;
	}

	@Override
	protected String doInBackground(byte[]... params) {

		String retour;

		try{
			
			System.setProperty("http.keepAlive", "false");

			URL u = new URL(mUrl);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("connection", "close");
			conn.setRequestMethod("POST");
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			conn.setRequestProperty( "Content-Length", String.valueOf(params[0].length));
			OutputStream os = conn.getOutputStream();
			os.write(params[0]);

			String respString = conn.getResponseMessage();

			Log.i("tag", respString);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line+"\n");
			}
			br.close();
			Log.i("tag", sb.toString());

			retour = sb.toString();

		}
		catch (IOException e){
			e.printStackTrace();
			retour = null;
		}
		return retour;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}
}
