package com.echances.etouches.utilities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.widget.TextView;



/**
 * 
 * @file DialogsModels.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class used for dialog alerts.
 * @details *
 * 
 */
@SuppressLint ("InlinedApi")
public class DialogsModels
{
	
	static ProgressDialog mLoadingDialog;
	/**
	 * Method used to show Confirmation Dialog
	 * 
	 * @param context
	 * @param title
	 * @param message
	 * @param labelBtnPositiv
	 * @param labelBtnNegativ
	 * @param mClickListener
	 */
	public static void ConfirmationDialog (Context context, String title,
			String message, String labelBtnPositiv, String labelBtnNegativ,
			final OnConfirmationDialogListeners mClickListener)
	{

		TextView titleTextView = new TextView(context);
		titleTextView.setText(title);
		
		titleTextView.setGravity(Gravity.CENTER);

		// titleTextView.setTextSize(context.getResources().getDimension(R.dimen.alert_text_size));

		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage(message);
		builder.setCustomTitle(titleTextView);

		builder.setCancelable(false);
		builder.setNegativeButton(labelBtnNegativ, new OnClickListener()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
				if (mClickListener != null)
					mClickListener.onClickNegativeButton(dialog);
			}
		});

		builder.setPositiveButton(labelBtnPositiv, new OnClickListener()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
				if (mClickListener != null)
					mClickListener.onClickPositiveButton(dialog);
			}
		});

		AlertDialog alert = builder.create();

		alert.show();

		
	}



	public interface OnConfirmationDialogListeners
	{
		public abstract void onClickPositiveButton (DialogInterface dialog);

		public abstract void onClickNegativeButton (DialogInterface dialog);
	}

	
	public static ProgressDialog getHorisontalProgressingDialog (Context activity)
	{
		ProgressDialog barProgressDialog = new ProgressDialog(activity,
				ProgressDialog.THEME_HOLO_LIGHT);

		// barProgressDialog.setTitle("Downloading Image ...");

		barProgressDialog.setMessage("progress ...");

		barProgressDialog.setProgressStyle(barProgressDialog.STYLE_HORIZONTAL);
		// barProgressDialog.setIndeterminate(true);
		barProgressDialog.setCancelable(false);
		barProgressDialog.setCanceledOnTouchOutside(false);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(100);

		return barProgressDialog;

	}
	
	public static void showLoadingDialog(Context activity){
		
		mLoadingDialog = new ProgressDialog(activity,
				ProgressDialog.THEME_HOLO_LIGHT);

		mLoadingDialog.setMessage("Loading ...");

		mLoadingDialog.setIndeterminate(true);
		
		mLoadingDialog.setCancelable(false);
		
		mLoadingDialog.show();
		
	}
	
	public static void showErrorDialog(Context activity, String message){
		
//		TextView titleTextView = new TextView(activity);
//		titleTextView.setText("Error");
//		titleTextView.setGravity(Gravity.CENTER);

		AlertDialog.Builder builder = new AlertDialog.Builder(activity,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage(message);
		builder.setTitle("Error");

		builder.setNegativeButton("OK", new OnClickListener()
		{
			@Override
			public void onClick (DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});

		AlertDialog alert = builder.create();

		alert.show();
	}
	
	public static void hideLoadingDialog(){
		if(mLoadingDialog != null && mLoadingDialog.isShowing())
			mLoadingDialog.cancel();
	}
	
}