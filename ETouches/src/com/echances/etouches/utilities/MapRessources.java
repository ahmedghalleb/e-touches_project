package com.echances.etouches.utilities;

import java.util.ArrayList;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.LatLng;

public class MapRessources {

	public static boolean GPS_STATUS = false;
	public static String GET_ZIP = "zip";
	public static String GET_NEAR = "near";
	public static int OFFLINE_ZOOM = 5;
	public static int DEFAULT_ZOOM = 13;
	private static boolean locationPermission;
	public static OnMyLocationChangeListener locationListner = new OnMyLocationChangeListener() {
		@Override
		public void onMyLocationChange(Location location) {

		}
	};
	public static Listener gpsListner = new Listener() {
		@Override
		public void onGpsStatusChanged(int event) {
			switch (event) {
			case GpsStatus.GPS_EVENT_STARTED:
				//StihlManager.getInstance().getGpsHelper().onGPSActivated();
				Log.i("MapRessources", "GPS_EVENT_STARTED");
				break;

			case GpsStatus.GPS_EVENT_STOPPED:
				Log.i("MapRessources", "GPS_EVENT_STOPPED");
				break;

			case GpsStatus.GPS_EVENT_FIRST_FIX:
				Log.i("MapRessources", "GPS_EVENT_FIRST_FIX");
				break;

			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				Log.i("MapRessources", "GPS_EVENT_SATELLITE_STATUS");
				break;

			}
		}
	};

	
	public static float distance2(LatLng StartP, LatLng EndP) {
		Location locationA = new Location("point A");

		locationA.setLatitude(StartP.latitude);
		locationA.setLongitude(StartP.longitude);

		Location locationB = new Location("point B");

		locationB.setLatitude(EndP.latitude);
		locationB.setLongitude(EndP.longitude);

		float distance = locationA.distanceTo(locationB);
		return distance / 1000;
	}

	public static double distance(LatLng StartP, LatLng EndP) {
		double lat1 = StartP.latitude;
		double lat2 = EndP.latitude;
		double lon1 = StartP.longitude;
		double lon2 = EndP.longitude;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));

		c = 6366000 * c;
		String rst = "" + c;
		rst = "" + rst.subSequence(0, rst.indexOf("."));
		double finalrst = Double.valueOf(rst);
		return finalrst / 1000;
	}


}
