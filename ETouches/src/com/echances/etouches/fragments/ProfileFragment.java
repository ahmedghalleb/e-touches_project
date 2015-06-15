package com.echances.etouches.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EchouchesApplication;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.model.GetProfileResponse;
import com.echances.etouches.model.Profile;
import com.echances.etouches.model.Response;
import com.echances.etouches.model.ScheduleResponse;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.echances.etouches.utilities.Utils;
import com.echances.etouches.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends BaseFragment {
	
	private Button locationButton, mCancel, mAccept;
	private EditText mNameEditText, mMobileEditText;
	private Switch ServiceFromWorkingPlace, ServiceOutsideWorkingPlace, ServiceForMale, ServiceFromWomen, CashonDelivery;
	
	GetProfileResponse mGetProfileResponse;
	
	boolean isModifyMode;
	
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        
        mCancel = (Button) rootView.findViewById(R.id.cancel_button);
        mAccept = (Button) rootView.findViewById(R.id.validate_button);
        mNameEditText = (EditText) rootView.findViewById(R.id.pseudo_edit_text);
        mMobileEditText = (EditText) rootView.findViewById(R.id.phone_edit_text);
        ServiceFromWorkingPlace = (Switch) rootView.findViewById(R.id.ServiceFromWorkingPlace);
        ServiceOutsideWorkingPlace = (Switch) rootView.findViewById(R.id.ServiceOutsideWorkingPlace);
        ServiceForMale = (Switch) rootView.findViewById(R.id.ServiceForMale);
        ServiceFromWomen = (Switch) rootView.findViewById(R.id.ServiceFromWomen);
        CashonDelivery = (Switch) rootView.findViewById(R.id.CashonDelivery);    	
        locationButton = (Button) rootView.findViewById(R.id.location_button);
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	getProfile();
    	
    	mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeMode();
				//fillProfile();
			}
		});
    	
    	mAccept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isModifyMode)
					editProfile();
				else
					changeMode();
			}
		});
    	
    	locationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(MapFragment.newInstance(mGetProfileResponse.getResult().getLng(), mGetProfileResponse.getResult().getLat()));
			}
    	});
    	
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
		getProfile();
		super.onResumeFragment();
	}
	
	private void refreshHeader(){
		((MainActivity)getActivity()).mTitleTextView.setText("Profile");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.GONE);
		((MainActivity)getActivity()).mRightImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_logout);
    	((MainActivity)getActivity()).setRightViewListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				DialogsModels.showCustomDialog(getActivity(), "Confirm", "Do you realy want to logout", new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						EtouchesApplicationCache.getInstance().saveUserId(0);
						getActivity().startActivity(new Intent(getActivity(), ConnectionActivity.class));
						getActivity().finish();
					}
				});
				
				
			}
		});
	}
	
	private void fillProfile() {
		// TODO Auto-generated method stub
		Profile profile = mGetProfileResponse.getResult();
		
		mNameEditText.setText(profile.getUn());
		mMobileEditText.setText(profile.getMb());
		
		ServiceFromWorkingPlace.setChecked(profile.getServiceFromWorkingPlace()==0);
		ServiceOutsideWorkingPlace.setChecked(profile.getServiceOutsideWorkingPlace()==0);
		ServiceForMale.setChecked(profile.getServiceForMale()==0);
		ServiceFromWomen.setChecked(profile.getServiceFromWomen()==0);
		CashonDelivery.setChecked(profile.getCashonDelivery()==0);
		
	}
	
	private void changeMode(){
    	isModifyMode = !isModifyMode;
    	if(isModifyMode){
    		mCancel.setVisibility(View.VISIBLE);
    		mAccept.setText("Validate");
    	}else{
    		mCancel.setVisibility(View.GONE);
    		mAccept.setText("Modify Your Profile");
    	}
	}
	
	 private void getProfile(){
	    	DialogsModels.showLoadingDialog(getActivity());
			WebServiceApiImp.getInstance((BaseActivity)getActivity()).GetProfile(EtouchesApplicationCache.getInstance().getUserId()+"",
					new WebServiceWaitingListener() {

				@Override
				public void OnWebServiceWait() {
				}

				@Override
				public void OnWebServiceProgress(
						float value) {
					
				}

				@Override
				public void OnWebServiceEnd(boolean statut, String message, Object data) {

					DialogsModels.hideLoadingDialog();

					Logr.w("WS message=" + message);

					if (statut) {

						mGetProfileResponse = new GetProfileResponse();
						try {
							mGetProfileResponse = ((GetProfileResponse) data);						
							fillProfile();
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					} else {
						DialogsModels.showErrorDialog(getActivity(), message);
					}

				}
			});
	    }

	 private void editProfile(){
	    	DialogsModels.showLoadingDialog(getActivity());
			WebServiceApiImp.getInstance((BaseActivity)getActivity()).EditProfile(EtouchesApplicationCache.getInstance().getUserId()+"", mNameEditText.getText().toString(), mMobileEditText.getText().toString(),
					ServiceFromWorkingPlace.isChecked()?"1":"0",
							ServiceOutsideWorkingPlace.isChecked()?"1":"0",
									ServiceForMale.isChecked()?"1":"0",
											ServiceFromWomen.isChecked()?"1":"0",
													CashonDelivery.isChecked()?"1":"0",
															new WebServiceWaitingListener() {

				@Override
				public void OnWebServiceWait() {
				}

				@Override
				public void OnWebServiceProgress(
						float value) {
					
				}

				@Override
				public void OnWebServiceEnd(boolean statut, String message, Object data) {

					DialogsModels.hideLoadingDialog();

					Logr.w("WS message=" + message);

					if (statut) {

						Response mResponse = new Response();
						try {
							mResponse = ((Response) data);						
							//fillProfile();
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}

					} else {
						DialogsModels.showErrorDialog(getActivity(), message);
					}

				}
			});
	    }

	
	
}