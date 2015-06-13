package com.echances.etouches.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.fragments.PlaceholderFragment;
import com.echances.etouches.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageFullFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_1 = "imageUrl";
    private static final String ARG_2 = "imageBitmap";

    private ImageView mImageView;
    
    private Bitmap mBitmapImage;
    private String mUrlImage;
    
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ImageFullFragment newInstance(String imageUrl, Bitmap imageBitmap) {
        ImageFullFragment fragment = new ImageFullFragment();
        Bundle args = new Bundle();
        args.putString(ARG_1, imageUrl);
        args.putParcelable(ARG_2, imageBitmap);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageFullFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_full, container, false);
        mImageView = (ImageView) rootView.findViewById(R.id.image_view);
        
        mUrlImage = getArguments().getString(ARG_1);
        mBitmapImage = getArguments().getParcelable(ARG_2);
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	if(mBitmapImage != null){
        	mImageView.setImageBitmap(mBitmapImage);
        }else{
        	// load url
        	ImageLoader.getInstance().displayImage(mUrlImage, mImageView);
        }
    	
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
		((MainActivity)getActivity()).mTitleTextView.setText("Full Screen Image");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}
	
}