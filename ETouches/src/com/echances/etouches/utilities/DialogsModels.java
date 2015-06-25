package com.echances.etouches.utilities;

import com.echances.etouches.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

		showLoadingDialog(activity, "Loading ...");

	}
	
	public static void showLoadingDialog(Context activity, String message){

		mLoadingDialog = new ProgressDialog(activity,
				ProgressDialog.THEME_HOLO_LIGHT);

		mLoadingDialog.setMessage(message);

		mLoadingDialog.setIndeterminate(true);

		mLoadingDialog.setCancelable(false);

		mLoadingDialog.show();

	}

	public static void showErrorDialog(Context context, String message){
		showCustomDialog(context, "Error", message, null);
	}
	
	public static void showCustomDialog(Context context, String title, String message, android.view.View.OnClickListener listener){

		// init dialog
		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

		// init dialog content view
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_ok, null, false);
		TextView titleTextView = (TextView) v.findViewById(R.id.titleTextViewDialogOk);
		Button okButton = (Button) v.findViewById(R.id.buttonDialogOk);
		Button cancelButton = (Button) v.findViewById(R.id.cancel_button);
		TextView messageTextView = (TextView) v.findViewById(R.id.messageTextViewDialogOk);

		// set alert title
		titleTextView.setText(title);
		messageTextView.setText(message);
		
		if(listener != null){
			okButton.setOnClickListener(listener);
			okButton.setText("Accept");
			cancelButton.setVisibility(View.VISIBLE);
		}
		else
		{
			okButton.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}
		
		cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// set dialog content view
		dialog.setContentView(v);

		// Show the dialog
		dialog.show();
	}
	
	public static void showCustomEditDialog(Context context, String title, final OnClickEditDialog listener){

		// init dialog
		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);

		// init dialog content view
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.dialog_edit_text, null, false);
		TextView titleTextView = (TextView) v.findViewById(R.id.titleTextViewDialogOk);
		Button okButton = (Button) v.findViewById(R.id.buttonDialogOk);
		Button cancelButton = (Button) v.findViewById(R.id.cancel_button);
		final EditText editText = (EditText) v.findViewById(R.id.edit_text);

		// set alert title
		titleTextView.setText(title);
		
		editText.setInputType(InputType.TYPE_CLASS_PHONE);
		
		editText.setHint("Enter you Phone Number");
		
		if(listener != null){
			okButton.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
					listener.OnClickOk(editText.getText().toString());
				}
			});
		}
		else
		{
			okButton.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}
		
		cancelButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// set dialog content view
		dialog.setContentView(v);

		// Show the dialog
		dialog.show();
	}

	public static void hideLoadingDialog(){
		if(mLoadingDialog != null && mLoadingDialog.isShowing())
			mLoadingDialog.cancel();
	}
	
	public interface OnClickEditDialog{
		abstract void OnClickOk(String text);
	}

}