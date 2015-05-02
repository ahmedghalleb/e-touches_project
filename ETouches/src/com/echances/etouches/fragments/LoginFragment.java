package com.echances.etouches.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.echances.etouches.R;
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

    Button mLoginButton,forgetPasswordButton;
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
        forgetPasswordButton= (Button)view.findViewById(R.id.forget_password_button);

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
            }
        });

        forgetPasswordButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
                
            }
        });

    }
    /**
     * Method used to Validate Fields
     * @return boolean
     */
    private boolean isValideFields(){

      
            if(Utils.isEmailValid(mMailEditText.getText().toString()))
                return true;
            else
                return false;
           
    }

   
    @Override
    public void onResume ()
    {
        super.onResume();

    }

    /**
     * Method used to Login
     */
    public void Login(){


//        if(!SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").isShowing())
//            SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").show();
//        WebServiceApiImp.getInstance((BaseActivity)getActivity()).Login(mMailEditText.getText().toString(),  mPasswordEditText.getText().toString(), new WebServiceWaitingListener() {
//            @Override
//            public void OnWebServiceProgress(float value) {
//                // TODO Auto-generated method stub
//
//            }
//            @Override
//            public void OnWebServiceWait() {
//
//                //DigiSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment") = DialogsModels.ProgressingDialog(getActivity(), getString(R.string.DigiSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment")_loading));
//                if(!SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").isShowing())
//                    SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").show();
//            }
//
//            @Override
//            public void OnWebServiceEnd(boolean statut, String message, Object data) {
//                // TODO Auto-generated method stub
//                Logr.w("WS message="+message);
//                if(SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment") != null && SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").isShowing())
//                    SnapSchoolApplicationCache.getInstance().getmProgressDialog("LoginFragment").cancel();
//                if(statut){
//
//                    UserResponse result=new UserResponse();
//                    try {
//                        result= ((UserResponse)data);
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//
//                    Logr.w("WS getCodeRetour="+result.getCodeRetour());
//                    Logr.w("WS toString="+result.toString());
//                    Logr.w("WS getToken="+result.getToken());
//
//
//                    SnapSchoolApplicationCache.getInstance().setUser(result.getUser());
//                    SnapSchoolApplicationCache.getInstance().setToken(result.getToken());
//                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
//                    getActivity().finish();
//                }else{
//                    
//                    DialogsModels.WarningDialog(getActivity(), message);
//                }
//            }
//        });

    }


}
