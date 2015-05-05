package com.echances.etouches.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
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
public class InscriptionFragment extends BaseFragment {

	Button mSubscribeButton;
	EditText mPseudoEditText, mPasswordEditText,
			mConfirmPasswordEditText, mPhoneEditText;

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

				DialogsModels.hideLoadingDialog();
				
				Logr.w("WS message=" + message);

				if (statut) {

					Response result = new Response();
					try {
						result = ((Response) data);
					} catch (Exception e) {
						// TODO: handle exception
					}
					getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
					getActivity().finish();
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
	}

}
