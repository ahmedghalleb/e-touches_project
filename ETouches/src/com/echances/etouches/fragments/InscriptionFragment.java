package com.echances.etouches.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.model.LoginResponse;
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
public class InscriptionFragment extends BaseFragment {

	Button mSubscribeButton;
	EditText mPseudoEditText, mPasswordEditText,
			mConfirmPasswordEditText, mPhoneEditText;
	
	private int mMode;
	private String mUsername;
	
	public InscriptionFragment(String username){
		mUsername = username;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inscription, null);
		mSubscribeButton = (Button) view.findViewById(R.id.inscrire_button);

		mPseudoEditText = (EditText) view.findViewById(R.id.pseudo_edit_text);
		mPhoneEditText = (EditText) view.findViewById(R.id.phone_edit_text);
		mPasswordEditText = (EditText) view
				.findViewById(R.id.password_edit_text);
		mConfirmPasswordEditText = (EditText) view
				.findViewById(R.id.confirm_password_edit_text);		

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mSubscribeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

				if (checkPWD()) {
					subscribe();
				}else{
					DialogsModels.showErrorDialog(getActivity(), "Please verify all fields");
				}

			}
		});
		
		mPseudoEditText.setText(mUsername);

	}

	private void subscribe(){
		DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity) getActivity()).Subscription(mPseudoEditText.getText().toString(),
				mPasswordEditText.getText().toString(),
				mPhoneEditText.getText().toString(),
				2,
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

				
				
				Logr.w("WS message=" + message);

				if (statut) {

					Response result = new Response();
					try {
						result = ((Response) data);
					} catch (Exception e) {
						// TODO: handle exception
					}
					//((ConnectionActivity) getActivity()).popBackStack();
					Login();
				} else {
					DialogsModels.hideLoadingDialog();
					DialogsModels.showErrorDialog(getActivity(), message);
				}

			}
		});
	}
	
	/**
     * Method used to Login
     */
    public void Login(){

    	//DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity) getActivity()).Login(mPhoneEditText.getText().toString(),
				mPasswordEditText.getText().toString(),
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

					LoginResponse result = new LoginResponse();
					try {
						result = ((LoginResponse) data);
						Log.i(TAG, result.getResult().getMb());
						
						EtouchesApplicationCache.getInstance().saveUserId(result.getResult().getId());
						getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
						getActivity().finish();
						
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
    
	private boolean checkPWD() {
		if (!mPseudoEditText.getText().toString().equals("") && !mPhoneEditText.getText().toString().equals("") &&
				!mPasswordEditText.getText().toString().equals("") && !mConfirmPasswordEditText.getText().toString().equals("") &&
				mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString())) {
			return true;
		}

		else {
			return false;
		}

	}
	
	@Override
	public void onResume() {
		super.onResume();
		((ConnectionActivity) getActivity()).showHideActionBar(true);
		((ConnectionActivity) getActivity()).setTitleActionBar("Registration");
	}

}
