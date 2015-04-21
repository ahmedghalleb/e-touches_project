package com.echances.etouches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.echances.etouches.fragments.PlaceholderFragment;

import com.echances.etouches.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    TextView sectionTextView;
    
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        sectionTextView = (TextView) rootView.findViewById(R.id.section_label);
        int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (sectionNumber) {
		case 1:
			sectionTextView.setText(getString(R.string.title_section1).toUpperCase());
			break;
		case 2:
			sectionTextView.setText(getString(R.string.title_section2).toUpperCase());
			break;
		case 3:
			sectionTextView.setText(getString(R.string.title_section3).toUpperCase());
			break;
		case 4:
			sectionTextView.setText(getString(R.string.title_section4).toUpperCase());
			break;
		case -1:
			sectionTextView.setText("Details Fragment");
			break;
		default:
			sectionTextView.setText(getString(R.string.title_section1).toUpperCase());
			break;
		}
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	sectionTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).addFragment(MainFragment.newInstance(-1));
			}
		});
    }
    
}