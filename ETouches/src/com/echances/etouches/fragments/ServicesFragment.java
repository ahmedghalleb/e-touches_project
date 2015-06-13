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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
public class ServicesFragment extends BaseFragment {
	
	MyCustomAdapter mAdapter;
	ListView mListView;
	ArrayList<Service> mDataArray;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ServicesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);
        
        mListView = (ListView) rootView.findViewById(R.id.services_listview);
       
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
    	mDataArray = new ArrayList<Service>();
    	
		mAdapter = new MyCustomAdapter(getActivity(), mDataArray);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(AddServiceFragment.newInstance(1));
			}
		});
		
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
		((MainActivity)getActivity()).mTitleTextView.setText("Services");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.GONE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_add);
    	((MainActivity)getActivity()).setRightViewListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(AddServiceFragment.newInstance(0));
			}
		});
	}
	
	/**
     * Method used to GetServices
     */
    public void GetServices(){

    	DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).GetServices(
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

					GetServicesResponse result = new GetServicesResponse();
					try {
						result = ((GetServicesResponse) data);
						Log.i(TAG, result.getServics().get(0).getSEN());
						mAdapter.setData(result.getServics());
						mAdapter.notifyDataSetChanged();
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