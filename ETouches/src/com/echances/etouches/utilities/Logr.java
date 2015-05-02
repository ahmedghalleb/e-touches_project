package com.echances.etouches.utilities;

import android.util.Log;

/** Log utility class to handle the log tag and DEBUG-only logging. */
public final class Logr {
	private final static boolean debug = true;
	private static final String TAG = "digiSchool";
	

	public static void i(String message) {
		if (debug) {
			Log.i(TAG, message);
		}
	}

	public static void d(String message) {
		if (debug) {
			Log.d(TAG, message);
		}
	}

	public static void v(String message) {
		if (debug) {
			Log.v(TAG, message);
		}
	}

	public static void e(String message) {
		if (debug) {
			Log.e(TAG, message);
		}
	}
	public static void w(String message) {
		if (debug) {
			Log.w(TAG, message);
		}
	}

	public static void d(String message, Object... args) {
		if (debug) {
			d(String.format(message, args));
		}
	}
}
