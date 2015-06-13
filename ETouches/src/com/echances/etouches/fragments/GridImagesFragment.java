package com.echances.etouches.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.echances.etouches.R;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class GridImagesFragment extends BaseFragment {
	
	private static String ARG_1 = "images";

	private View mRootView;
	private RecyclerView mListView;
	private Button mCancel, mAccept;
	
	ImagesCollectionAdapter mAdapter;
	ArrayList<ImageItem> mDataArray;
	
	boolean isSelected;
	
	static AddServiceFragment mAddServiceFragment;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     * @param addServiceFragment 
     */
    public static GridImagesFragment newInstance(AddServiceFragment addServiceFragment, ArrayList<String> images) {
        GridImagesFragment fragment = new GridImagesFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_1, images);
        fragment.setArguments(args);
        mAddServiceFragment = addServiceFragment;
        return fragment;
    }

    public GridImagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_grid_images, container, false);
        
        mListView = (RecyclerView) mRootView.findViewById(R.id.images_listview);
        
        mCancel = (Button) mRootView.findViewById(R.id.cancel_button);
        mAccept = (Button) mRootView.findViewById(R.id.validate_button);
       
        return mRootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	initView();
    	
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
				ArrayList<String> urls = new ArrayList<String>();
				for (ImageItem item : mAdapter.getItems()) {
					if(item.isChecked())
						urls.add(item.getUrl());
				}
				if(urls.size()>0)
					mAddServiceFragment.setSelectedImagesFromInstagram(urls);
				((PlaceholderFragment)getParentFragment()).popBackStack();
			}
		});
    	
    }
    
    private void initView() {
		// TODO Auto-generated method stub
    	initCollectionView();
				
	}
    
    private void initCollectionView() {
    	
    	ArrayList<String> images = new ArrayList<String>();
    	
    	images = getArguments().getStringArrayList(ARG_1);
    	
    	mDataArray = new ArrayList<GridImagesFragment.ImageItem>();
    	
    	for (int i = 0; i < images.size(); i++) {
    		Log.i(TAG, images.get(i));
    		GridImagesFragment.ImageItem item;
    		item = new ImageItem(images.get(i), false);
    		mDataArray.add(item);
		}
    	
        //mListView.addItemDecoration(new MarginDecoration(this));
    	mListView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        
        mListView.setLayoutManager(mLayoutManager);
        mAdapter = new ImagesCollectionAdapter(getActivity(), mDataArray);
        mListView.setAdapter(mAdapter);
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
		((MainActivity)getActivity()).mTitleTextView.setText("My Instagram Images");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_check_off);
    	((MainActivity)getActivity()).setRightViewListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isSelected = !isSelected;
				mAdapter.selectAll(isSelected);
				mAdapter.notifyDataSetChanged();
				if(isSelected){
					((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_check_all);
				}else{
					((MainActivity)getActivity()).mRightImageView.setImageResource(R.drawable.ic_check_off);
				}
			}
		});
    	
	}
    
    public class ImagesCollectionAdapter extends RecyclerView.Adapter<ImagesCollectionAdapter.CustomHolder> {

        Context context;
        private ArrayList<ImageItem> items;

        public ImagesCollectionAdapter(Context context, ArrayList<ImageItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image_item, parent, false);
            return new CustomHolder(view);
        }

        public void setItems(ArrayList<ImageItem> items) {
            this.items = items;
        }

        @Override
        public void onBindViewHolder(final CustomHolder holder, final int position) {

            final ImageItem item = items.get(position);

            holder.checkBox.setChecked(item.isChecked());

            ImageLoader.getInstance().displayImage(item.getUrl(), holder.image);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	item.setChecked(!item.isChecked());
                }
            });
            
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	
                	((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(ImageFullFragment.newInstance(item.getUrl(), null));
                	
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
        
        public ArrayList<ImageItem> getItems() {
            return items;
        }
        
        public void selectAll(boolean select){
        	for (ImageItem imageItem : items) {
				imageItem.setChecked(select);
			}
        }

        public class CustomHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public CheckBox checkBox;

            public CustomHolder(View view) {
                super(view);
                image = (ImageView) itemView.findViewById(R.id.image);
                checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            }
        }
    }

    public class ImageItem {

        private String url;
        
        private boolean isChecked;
        
		public ImageItem(String url, boolean isChecked) {
			super();
			this.url = url;
			this.isChecked = isChecked;
		}
		
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

        

    }
}