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
import android.widget.TextView;

import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.application.EchouchesApplication;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends BaseFragment {
	
	private Button locationButton;
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
        
        locationButton = (Button) rootView.findViewById(R.id.location_button);
        
        locationButton.setOnClickListener(new OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				// TODO Auto-generated method stub
        				((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(MapFragment.newInstance());
        			}
        });
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
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
		((MainActivity)getActivity()).mTitleTextView.setText("Profile");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.GONE);
		((MainActivity)getActivity()).mRightImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_logout);
    	((MainActivity)getActivity()).setRightViewListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EtouchesApplicationCache.getInstance().saveUserId(0);
				getActivity().startActivity(new Intent(getActivity(), ConnectionActivity.class));
				getActivity().finish();
			}
		});
	}
	
	
}