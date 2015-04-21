package com.echances.etouches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echances.etouches.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    int mSectionNumber;
    
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
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		case 2:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		case 3:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		case 4:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		default:
			addFragment(MainFragment.newInstance(mSectionNumber));
			break;
		}
        return rootView;
    }

    
    public void addFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        // clear all fragment and init loading fragment
        fragmentTransaction.add(R.id.content_child_frame, fragment, fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }
}