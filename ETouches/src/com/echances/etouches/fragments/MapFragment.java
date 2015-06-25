package com.echances.etouches.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.echances.etouches.utilities.MapRessources;
import com.echances.etouches.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A placeholder fragment containing a simple view.
 */
public class MapFragment extends BaseFragment {

    private static String PARAM_1 = "lng";
    private static String PARAM_2 = "lat";

    public static final int					MILLISECONDS_PER_SECOND					= 1000;
    // The update interval
    public static final int					UPDATE_INTERVAL_IN_SECONDS				= 300;													// 5 * 60 = 5 minutes
    // A fast interval ceiling
    public static final int					FAST_CEILING_IN_SECONDS					= 120;													// 2 * 60 = 2 minutes
    // The waiting interval
    public static final int					WAITING_TIMER_TASK_IN_SECONDS			= 30;														// 1/2 minutes
    // Update interval in milliseconds
    public static final long				UPDATE_INTERVAL_IN_MILLISECONDS			= MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    public static final long				FAST_INTERVAL_CEILING_IN_MILLISECONDS	= MILLISECONDS_PER_SECOND * FAST_CEILING_IN_SECONDS;
    public static final long				WAITING_TIMER_TASK_IN_MILLISECONDS		= MILLISECONDS_PER_SECOND * WAITING_TIMER_TASK_IN_SECONDS;

    private Marker myPosition;
    private LocationManager locationManager;
    private GoogleMap mMap;
    private Button mCancel, mAccept;
    private ImageView mLocationButton;

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
        mLocationButton = (ImageButton) rootView.findViewById(R.id.location);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        mLng = getArguments().getString(PARAM_1);

        mLat = getArguments().getString(PARAM_2);

        setUpMapIfNeeded();

        if(!mLat.equals("")) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng))).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
            moveCam(new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLng)));
        }

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
        
        mLocationButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	if (myPosition != null)
                    moveCam(myPosition.getPosition());
            }
        });
        
        checkGPS();
        
        requestCurrentLocation();

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
                if (myPosition != null)
                	drawMarker(myPosition.getPosition());
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

    private void checkGPS ()
    {
    	locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
    	MapRessources.GPS_STATUS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	if (!MapRessources.GPS_STATUS) {
    		// MapRessources.showGpsDialog(getSherlockActivity());
    	}
    }

    private void createLocationUpdateRequest ()
    {
        DialogsModels.showLoadingDialog(getActivity(), "Waiting for Localisation...");
        locationManager.addGpsStatusListener(MapRessources.gpsListner);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_INTERVAL_IN_MILLISECONDS, (float)FAST_CEILING_IN_SECONDS, locationListener);
    }
    private void createNetworkLocationUpdateRequest ()
    {
        if (locationManager != null) {
            locationManager.removeGpsStatusListener(MapRessources.gpsListner);
            locationManager.removeUpdates(locationListener);
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_INTERVAL_IN_MILLISECONDS, FAST_CEILING_IN_SECONDS, locationListener);
    }

    private void removeLocationUpdateRequest ()
    {
    	DialogsModels.hideLoadingDialog();
        if (locationManager != null) {
            locationManager.removeGpsStatusListener(MapRessources.gpsListner);
            locationManager.removeUpdates(locationListener);
        }
    }

    LocationListener	locationListener	= new LocationListener()
    {
        @Override
        public void onStatusChanged (String provider, int status, Bundle extras)
        {
            DialogsModels.hideLoadingDialog();
            Log.i("MapViewFrag", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled (String provider)
        {
            DialogsModels.hideLoadingDialog();
            Log.i("MapViewFrag", "onProviderEnabled");
        }

        @Override
        public void onProviderDisabled (String provider)
        {
            DialogsModels.hideLoadingDialog();
            Log.i("MapViewFrag", "onProviderEnabled");
        }

        @Override
        public void onLocationChanged (Location location)
        {
            Log.i("MapViewFrag", "onLocationChanged");
            handlerNetworkLocationTask.removeMessages(0);
            handlerLocationTask.removeMessages(0);
            DialogsModels.hideLoadingDialog();
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            drawMarker(myLocation);
            // moveCam(myLocation);
        }

    };

    private void requestCurrentLocation ()
    {
        Log.i("MapViewFrag", "requestCurrentLocation");
        createLocationUpdateRequest();
        handlerNetworkLocationTask.sendEmptyMessageDelayed(0, 15000);
        handlerLocationTask.sendEmptyMessageDelayed(0, WAITING_TIMER_TASK_IN_MILLISECONDS);
    }

    Handler	handlerLocationTask	= new Handler()
    {
        @Override
        public void handleMessage (Message msg)
        {
            Log.i("MapViewFrag", "handlerLocationTask handleMessage");
            removeLocationUpdateRequest();
            DialogsModels.showErrorDialog(getActivity(), "Fail to get location");
        }
    };

    Handler	handlerNetworkLocationTask	= new Handler()
    {
        @Override
        public void handleMessage (Message msg)
        {
            Log.i("MapViewFrag", "handlerNetworkLocationTask handleMessage");
            createNetworkLocationUpdateRequest();
        }
    };

    private void drawMarker(LatLng location) {
        //googleMap.clear();

        if (myPosition != null)
            myPosition.remove();

        myPosition = mMap
                .addMarker(new MarkerOptions().position(location).icon(
                        BitmapDescriptorFactory
                                .fromResource(R.drawable.loc_mark)));

        if(mLat.equals(""))
            moveCam(location);

            //StihlManager.getInstance().setCurrentPosition(myPosition);
    }

    private void moveCam (LatLng location)
    {
        CameraPosition cameraPosition;
            if (location == null)
                cameraPosition = new CameraPosition.Builder().target(new LatLng(47, 2.10)).zoom(MapRessources.OFFLINE_ZOOM).build();
            else {
                cameraPosition = new CameraPosition.Builder().target(location).zoom(MapRessources.DEFAULT_ZOOM).build();
            }
            try {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        
    }

}