package com.echances.etouches.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.echances.etouches.R;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.model.GetServicesResponse;
import com.echances.etouches.model.LoginResponse;
import com.echances.etouches.model.Service;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;


public class MainActivity extends BaseActivity{

	String TAG = "MainActivity";
	
	public static int GALERY_REQUEST_CODE = 33;
	
	public static final int TAB_SERVICES_INDEX = 1;
	public static final int TAB_SETTING_INDEX = 2;
	public static final int TAB_SCHEDULE_INDEX = 3;
	public static final int TAB_RESERVATION_INDEX = 4;
	
	public PlaceholderFragment mFragmentServices;
	public PlaceholderFragment mFragmentSetting;
	public PlaceholderFragment mFragmentSchedule;
	public PlaceholderFragment mFragmentReservation;
	
	public ImageView mLeftImageView, mRightImageView;
	public TextView mTitleTextView;
	RadioGroup mTabBar;
	
	OnGetImageListener onGetImageListener;
	
	public ArrayList<Service> Services;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabBar = (RadioGroup) findViewById(R.id.tab_bar);
        
        // Set up the action bar.
        initActionBar();
                        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        
        GetServices();
        
    }
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
    
    private void initActionBar(){
    	LayoutInflater mInflater = LayoutInflater.from(this);
		View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
		
		ActionBar mActionBar = getSupportActionBar();
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		
		mLeftImageView = (ImageView) mCustomView.findViewById(R.id.leftImageView);
		mRightImageView = (ImageView) mCustomView.findViewById(R.id.rightImageView);
		mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
  
    }
    
    public void selectTab(int index){
    	switch (index) {
		case TAB_SERVICES_INDEX:
		{
			if(mFragmentServices == null){
				mFragmentServices = PlaceholderFragment.newInstance(index);
				addFragment(R.id.content_frame, mFragmentServices);
			}else{
				showFragment(mFragmentServices);
			}
		}
			break;
		case TAB_SETTING_INDEX:
		{
			if(mFragmentSetting == null){
				mFragmentSetting = PlaceholderFragment.newInstance(index);
				addFragment(R.id.content_frame, mFragmentSetting);
			}else{
				showFragment(mFragmentSetting);
			}
		}
			break;
		case TAB_SCHEDULE_INDEX:
		{
			if(mFragmentSchedule == null){
				mFragmentSchedule = PlaceholderFragment.newInstance(index);
				addFragment(R.id.content_frame, mFragmentSchedule);
			}else{
				showFragment(mFragmentSchedule);
			}
		}
			break;
		case TAB_RESERVATION_INDEX:
		{
			if(mFragmentReservation == null){
				mFragmentReservation = PlaceholderFragment.newInstance(index);
				addFragment(R.id.content_frame, mFragmentReservation);
			}else{
				showFragment(mFragmentReservation);
			}
		}
			break;

		default:
			break;
		}
    }

    public void showFragment(Fragment fragment){
    	
    	attachFragment(false, mFragmentServices);
    	attachFragment(false, mFragmentSetting);
    	attachFragment(false, mFragmentSchedule);
    	attachFragment(false, mFragmentReservation);
    	
    	attachFragment(true, fragment);
    }

    public void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // clear all fragment and init loading fragment
        fragmentTransaction.add(containerViewId, fragment, fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // clear all fragment and init loading fragment
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getName());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void attachFragment(boolean isShow, Fragment fragment) {
    	if(fragment == null)
    		return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isShow){
            fragmentTransaction.show(fragment);
            fragment.onResume();
        }
        else{
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void clearStack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    
    
    public void showHideActionBar(boolean isShow){
		if(isShow)
			getSupportActionBar().show();
		else
			getSupportActionBar().hide();
	}
    
    public void setLeftViewListener(View.OnClickListener listener){
    	mLeftImageView.setOnClickListener(listener);
    }
    
    public void setRightViewListener(View.OnClickListener listener){
    	mRightImageView.setOnClickListener(listener);
    }
        
    public void TabSelected(View v) {
    	Log.i(TAG,"onTabSelected : "+v.getTag());
    	selectTab(Integer.parseInt(v.getTag().toString()));
    }
    
    /**
     * Method used to GetServices
     */
    public void GetServices(){

    	DialogsModels.showLoadingDialog(this);
		WebServiceApiImp.getInstance(this).GetServices(
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
						Services = result.getServics();
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					// select first tab
			        mTabBar.check(R.id.tab_1);
					selectTab(TAB_SERVICES_INDEX);
					
				} else {
					DialogsModels.showErrorDialog(MainActivity.this, message);
				}

			}
		});

    }

    
  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			
			if(true){//requestCode == GALERY_REQUEST_CODE
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				Bitmap pickedPhoto = BitmapFactory.decodeFile(picturePath);
				
				if(onGetImageListener != null)
					onGetImageListener.OnGetImage(pickedPhoto);

			}
		}
	}
  

  public void setOnGetImageListener(OnGetImageListener onGetImageListener) {
		this.onGetImageListener = onGetImageListener;
  }

  public interface OnGetImageListener {
	  void OnGetImage(Bitmap pickedPhoto);
  }

    
}
