package com.echances.etouches.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
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
public class ReservationsFragment extends BaseFragment {
	
	
	RadioGroup mRadioGroup;
	ListView mListView;
	EditText mSearchEditText;
	
	MyCustomAdapter mAdapter;
	ArrayList<Service> mDataArray;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReservationsFragment newInstance() {
        ReservationsFragment fragment = new ReservationsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ReservationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reservations, container, false);
        
        mRadioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
        
        mListView = (ListView) rootView.findViewById(R.id.reservations_listview);
        
        mSearchEditText = (EditText) rootView.findViewById(R.id.search_edit_text);
       
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	initView();
    	
    	mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    	
    	mSearchEditText.addTextChangedListener(searchTextWatcher);
    	
    }
    
    private void initView() {
		// TODO Auto-generated method stub
    	mDataArray = new ArrayList<Service>();
    	
		mAdapter = new MyCustomAdapter(getActivity(), mDataArray);
		mListView.setAdapter(mAdapter);
		
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
		((MainActivity)getActivity()).mTitleTextView.setText("Reservations");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.GONE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}
	
	
	RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.new_button) {
                GetServices();
            } else {
            	GetServices();
            }
            
            mSearchEditText.setText("");
        }
    };
    
    private TextWatcher searchTextWatcher = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// ignore
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// ignore
		}

		@Override
		public void afterTextChanged(Editable s) {
			Log.d("after changed", "*** Search value changed: " + s.toString());
			mAdapter.getFilter().filter(s.toString());			
		}
	};
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