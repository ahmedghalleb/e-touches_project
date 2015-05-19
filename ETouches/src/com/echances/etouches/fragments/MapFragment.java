package com.echances.etouches.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapFragment extends BaseFragment {

	private GoogleMap mMap;
	private Button mCancel, mAccept;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        
        mCancel = (Button) rootView.findViewById(R.id.cancel_button);
        mAccept = (Button) rootView.findViewById(R.id.validate_button);

        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	setUpMapIfNeeded();
    	
    	mCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
    	
    	mAccept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
    	
    }
    
    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        if (mMap == null) {
            return;
        }
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng latLng) {
				// TODO Auto-generated method stub
				Log.i("MapFragment", "LatLng : "+latLng.latitude+" "+latLng.longitude);
				mMap.clear();// if (marker != null) {marker.remove();}
				mMap.addMarker(new MarkerOptions().position(latLng));
			}
			
		});
    }
    
    @Override
    public void onResume() {
    	// TODO Auto-generated method stub
    	((PlaceholderFragment)getParentFragment()).setTitle("Map");
    	((PlaceholderFragment)getParentFragment()).setVisibility(View.VISIBLE, View.VISIBLE);
    	
    	super.onResume();
    }
}