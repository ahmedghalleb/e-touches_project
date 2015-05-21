package com.echances.etouches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echances.etouches.R;
import com.echances.etouches.activities.MainActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    int mSectionNumber;
    
    public String actionBarTitle;
    public int leftVisibility, rightVisibility;
    
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_holder, container, false);
        mSectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (mSectionNumber) {
		case 1:
			addFragment(ServicesFragment.newInstance());
			break;
		case 2:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		case 3:
			addFragment(ProfileFragment.newInstance(mSectionNumber));
			break;
		case 4:
			addFragment(ReservationsFragment.newInstance());
			break;
		default:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		}
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    }
    
    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        // clear all fragment and init loading fragment
        fragmentTransaction.add(R.id.content_child_frame, fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(fragment.getClass().getName()); 
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }
    

    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	resumeLastFragment();
    	
    	((MainActivity)getActivity()).setLeftViewListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popBackStack();
			}
		});
    	super.onResume();
    }
    
	public void popBackStack(){
		Log.i("PlaceholderFragment", "popBackStack");
		
		int backStackEntryCount = getChildFragmentManager().getBackStackEntryCount();
		
		if(backStackEntryCount < 2){
			return;
		}
			
		getChildFragmentManager().popBackStack();
		Fragment baseFragment = getChildFragmentManager().findFragmentByTag(getChildFragmentManager().getBackStackEntryAt(backStackEntryCount-2).getName());
		if (baseFragment == null) {
			Log.i("PlaceholderFragment", "baseFragment == null");
			return;
		}
		if (baseFragment instanceof BaseFragment) {
			((BaseFragment) baseFragment).onResumeFragment();;
		}
	}
	
	private void resumeLastFragment(){
		
		int backStackEntryCount = getChildFragmentManager().getBackStackEntryCount();
		
		if(backStackEntryCount < 1){
			return;
		}
			
		Fragment baseFragment = getChildFragmentManager().findFragmentByTag(getChildFragmentManager().getBackStackEntryAt(backStackEntryCount-1).getName());
		if (baseFragment == null) {
			Log.i("PlaceholderFragment", "baseFragment == null");
			return;
		}
		if (baseFragment instanceof BaseFragment) {
			((BaseFragment) baseFragment).onResume();
		}
	}
}