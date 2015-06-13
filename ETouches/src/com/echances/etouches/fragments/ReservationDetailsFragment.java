package com.echances.etouches.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;

/**
 * 
 * @file InscriptionFragment.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from Fragment.
 * @details *
 * 
 */
public class ReservationDetailsFragment extends BaseFragment {

	Button mModifyButton, mCancelButton, mAcceptButton;
	EditText mServieNameEditText, mServiceEditText,
			mClientEditText, mReservationDateEditText;
	
	
	public ReservationDetailsFragment(){
	}
	
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReservationDetailsFragment newInstance() {
    	ReservationDetailsFragment fragment = new ReservationDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_reservation_details, null);

		mServieNameEditText = (EditText) view.findViewById(R.id.service_name_edit_text);
		mServiceEditText = (EditText) view.findViewById(R.id.service_edit_text);
		mClientEditText = (EditText) view
				.findViewById(R.id.client_edit_text);
		mReservationDateEditText = (EditText) view
				.findViewById(R.id.reservation_date_edit_text);
		
		mModifyButton = (Button) view.findViewById(R.id.modify_button);
		mCancelButton = (Button) view.findViewById(R.id.cancel_button);
		mAcceptButton = (Button) view.findViewById(R.id.validate_button);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
    	
    	mAcceptButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
    	
    	mModifyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
		
    	mServiceEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectService();
			}
		});

	}

    public void selectService(){
    	
		final String[] items = {"Service 1","Service 2","Service 3","Service 4"};

		new AlertDialog.Builder(getActivity()).setTitle(null)
		.setItems(items,  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mServiceEditText.setText(items[which].toString());

				dialog.dismiss();
			}
		}).create().show();
	}
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	refreshHeader();
    	super.onResume();
    }
    
	@Override
	public void onResumeFragment() {
		// TODO Auto-generated method stub
		refreshHeader();		
		
		super.onResumeFragment();
	}
	
	private void refreshHeader(){
		((MainActivity)getActivity()).mTitleTextView.setText("Reservation details");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}

}
