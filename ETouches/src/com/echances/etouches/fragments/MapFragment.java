package com.echances.etouches.fragments;

import android.graphics.drawable.BitmapDrawable;
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

import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.model.Response;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.echances.etouches.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapFragment extends BaseFragment {
	
	private static String PARAM_1 = "lng";
	private static String PARAM_2 = "lat";

	private GoogleMap mMap;
	private Button mCancel, mAccept;
	
	String mLng, mLat;
	
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance(String lng, String lat) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_1, lng);
        args.putString(PARAM_2, lat);
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
    	
    	mLng = getArguments().getString(PARAM_1);
    	
    	mLat = getArguments().getString(PARAM_2);
    	
    	setUpMapIfNeeded();
    	
    	if(!mLat.equals(""))
    		mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng))).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
    	
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
				updateLocation();
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
				mLng = latLng.longitude+"";
				mLat = latLng.latitude+"";
				mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
			}
			
		});
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
		((MainActivity)getActivity()).mTitleTextView.setText("Map");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}
	
	private void updateLocation(){
    	DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).UpdateLocation(EtouchesApplicationCache.getInstance().getUserId()+"", mLng, mLat, new WebServiceWaitingListener() {

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

					Response mResponse = new Response();
					try {
						mResponse = ((Response) data);						
						((PlaceholderFragment)getParentFragment()).popBackStack();
						
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