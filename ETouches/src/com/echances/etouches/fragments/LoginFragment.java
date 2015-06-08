package com.echances.etouches.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.dina.oauth.instagram.InstagramApp.OAuthAuthenticationListener;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.activities.TwitterActivity;
import com.echances.etouches.activities.TwitterActivity.TwitterCallBack;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EchouchesApplication;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.model.LoginResponse;
import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.echances.etouches.utilities.Utils;

/**
 * 
 * @file LoginFragment.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class extended from Fragment.
 * @details *
 * 
 */
public class LoginFragment extends BaseFragment
{

	String TAG = "LoginFragment";
	
    Button mLoginButton, mTwitterButton, mInstagramButton;
    TextView forgetPasswordButton, mSignupButton;
    EditText mMailEditText, mPasswordEditText;

    @Override
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //		setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, null);
        mLoginButton = (Button)view.findViewById(R.id.connect_button);
        mMailEditText = (EditText)view.findViewById(R.id.mail_edit_text);
        mPasswordEditText = (EditText)view.findViewById(R.id.password_edit_text);
        forgetPasswordButton= (TextView)view.findViewById(R.id.forget_password_button);
        mSignupButton= (TextView)view.findViewById(R.id.signup_button);
        mTwitterButton= (Button)view.findViewById(R.id.twitter_button);
        mInstagramButton= (Button)view.findViewById(R.id.instagram_button);

        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


        mLoginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(isValideFields())
                    Login();
                else
                	DialogsModels.showErrorDialog(getActivity(),  "Please verify all fields");
            }
        });

        forgetPasswordButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	SendMailForget();
                
            }
        });
        
        mSignupButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	InscriptionFragment fragment = new InscriptionFragment("");
                ((ConnectionActivity)getActivity()).addFragmentWithHorizAnimation(fragment);
                
            }
        });
        
        mTwitterButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	((TwitterActivity)getActivity()).getUsername(new TwitterCallBack() {
					
					@Override
					public void GetUserNameSuccess(String username) {
						// TODO Auto-generated method stub
						InscriptionFragment fragment = new InscriptionFragment(username);
		                ((ConnectionActivity)getActivity()).addFragmentWithHorizAnimation(fragment);
					}
					
					@Override
					public void GetUserNameError() {
						// TODO Auto-generated method stub
						
					}
				});
            	                
            }
        });
        
        mInstagramButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	if (((BaseActivity)getActivity()).mApp.hasAccessToken()) {
            		
            		InscriptionFragment fragment = new InscriptionFragment(((BaseActivity)getActivity()).mApp.getName());
	                ((ConnectionActivity)getActivity()).addFragmentWithHorizAnimation(fragment);
	                
	                
            	}else{
            		((BaseActivity)getActivity()).mApp.setListener(listener);
            		((BaseActivity)getActivity()).mApp.authorize();
            	}
            	                
            }
        });

        //
    }
    
    protected void SendMailForget() {
		// TODO Auto-generated method stub
    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("Enter you Phone Number, to recover your password");
		// Set up the input
		final EditText input = new EditText(getActivity());
		// Specify the type of input expected;
		input.setInputType(InputType.TYPE_CLASS_PHONE);
		
		input.setHint("Enter you Phone Number");
		
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        String text = input.getText().toString();
		        Log.i(TAG, text);
		        dialog.cancel();
//		        getActivity().startActivity(new Intent(getActivity(),MainActivity.class));
//				getActivity().finish();
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		AlertDialog alert = builder.create();

		alert.show();
	}

	/**
     * Method used to Validate Fields
     * @return boolean
     */
    private boolean isValideFields(){

      
            if(!mMailEditText.getText().toString().equals("") && !mPasswordEditText.getText().toString().equals(""))
                return true;
            else
                return false;
           
    }

   
    @Override
    public void onResume ()
    {
        super.onResume();
        
        ((ConnectionActivity) getActivity()).showHideActionBar(false);

    }

    /**
     * Method used to Login
     */
    public void Login(){

    	DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity) getActivity()).Login(mMailEditText.getText().toString(),
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

	OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			InscriptionFragment fragment = new InscriptionFragment(((BaseActivity)getActivity()).mApp.getName());
            ((ConnectionActivity)getActivity()).addFragmentWithHorizAnimation(fragment);
        }

		@Override
		public void onFail(String error) {
			Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
		}
	};

}
