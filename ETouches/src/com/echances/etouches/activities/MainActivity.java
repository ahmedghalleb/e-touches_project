package com.echances.etouches.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.echances.etouches.R;
import com.echances.etouches.fragments.PlaceholderFragment;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

	public static final int TAB_SERVICES_INDEX = 1;
	public static final int TAB_SETTING_INDEX = 2;
	public static final int TAB_SCHEDULE_INDEX = 3;
	public static final int TAB_RESERVATION_INDEX = 4;
	
	public PlaceholderFragment mFragmentServices;
	public PlaceholderFragment mFragmentSetting;
	public PlaceholderFragment mFragmentSchedule;
	public PlaceholderFragment mFragmentReservation;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        //  actionBar.setSelectedNavigationItem(position);

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < 4; i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            /*.setText(mSectionsPagerAdapter.getPageTitle(i))*/
                    		.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
                            .setTabListener(this));
        }
        
        
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
        if (isShow)
            fragmentTransaction.show(fragment);
        else
            fragmentTransaction.hide(fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void clearStack() {
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return false;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        //mViewPager.setCurrentItem(tab.getPosition());
    	selectTab(tab.getPosition() + 1);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
