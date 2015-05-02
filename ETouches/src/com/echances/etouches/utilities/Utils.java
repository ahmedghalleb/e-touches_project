package com.echances.etouches.utilities;

import java.util.regex.Pattern;

import android.util.Log;

/**
 * 
 * @file Utils.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class that contain the method to draw number of request
 * @details *
 */
public class Utils
{

	static String					reg						= "^[a-zA-Z0-9_-]+([.][a-zA-Z0-9_-]+)*@([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,63}$";

	private static final Pattern	EMAIL_ADDRESS_PATTERN	= Pattern.compile(reg);

	public static boolean isEmailValid (String email)
	{
		Log.e("email", EMAIL_ADDRESS_PATTERN.toString());
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
}