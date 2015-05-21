package com.echances.etouches.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.adapters.MyCustomAdapter;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.model.GetServicesResponse;
import com.echances.etouches.model.Service;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.echances.etouches.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddServiceFragment extends BaseFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AddServiceFragment newInstance() {
        AddServiceFragment fragment = new AddServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddServiceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_service, container, false);
               
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	initView();
    	
    }
    
    private void initView() {
		// TODO Auto-generated method stub
		
		GetServices();
		
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
    	
		GetServices();
		
		super.onResumeFragment();
	}
	
	private void refreshHeader(){
		((MainActivity)getActivity()).mTitleTextView.setText("Add New Service");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}
	
	/**
     * Method used to GetServices
     */
    public void GetServices(){

    }

    
}