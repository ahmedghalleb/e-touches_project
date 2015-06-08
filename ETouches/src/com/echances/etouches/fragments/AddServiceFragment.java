package com.echances.etouches.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import br.com.dina.oauth.instagram.InstagramApp.OAuthAuthenticationListener;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.ConnectionActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.activities.MainActivity.OnGetImageListener;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddServiceFragment extends BaseFragment {
	
	private RecyclerView mGaleryView;
	private EditText mServiceEditText, mPriceEditText, mAverageEditText;
	private Button addImageInstagramButton, uploadImageButton, mCancel, mAccept;
	
	AddServiceAdapter mAdapter;
	ArrayList<GaleryItem> mDataArray;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AddServiceFragment newInstance() {
        AddServiceFragment fragment = new AddServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddServiceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_service, container, false);
               
        mGaleryView = (RecyclerView) rootView.findViewById(R.id.gallery_view);
        addImageInstagramButton = (Button) rootView.findViewById(R.id.instagram_button);
        uploadImageButton = (Button) rootView.findViewById(R.id.upload_button);
        mCancel = (Button) rootView.findViewById(R.id.cancel_button);
        mAccept = (Button) rootView.findViewById(R.id.validate_button);
        mServiceEditText = (EditText) rootView.findViewById(R.id.service_edit_text);
        mPriceEditText = (EditText) rootView.findViewById(R.id.price_edit_text);
        mAverageEditText = (EditText) rootView.findViewById(R.id.average_edit_text);
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	
    	initView();
    	
    	mServiceEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectService();
			}
		});
    	
    	mAverageEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTime();
			}
		});
    	
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
    	
    	
    	addImageInstagramButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	if (((BaseActivity)getActivity()).mApp.hasAccessToken()) {
            		
            		GetInstagramImages();
	                
	                
            	}else{
            		((BaseActivity)getActivity()).mApp.setListener(listener);
            		((BaseActivity)getActivity()).mApp.authorize();
            	}
            	                
            }
        });
    	
    	uploadImageButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	Intent pickPicture = new Intent(
        				Intent.ACTION_PICK,
        				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        		startActivityForResult(pickPicture, MainActivity.GALERY_REQUEST_CODE);
        		
        		((MainActivity)getActivity()).setOnGetImageListener(new OnGetImageListener() {
					
					@Override
					public void OnGetImage(Bitmap pickedPhoto) {
						// TODO Auto-generated method stub
						mDataArray.add(new GaleryItem(mDataArray.size()+1, "", pickedPhoto));
						mAdapter.setItems(mDataArray);
						mAdapter.notifyDataSetChanged();
						
					}
				});
            	                
            }
        });
    	
    	
    }
    
    private void initView() {
		// TODO Auto-generated method stub
		
    	initGaleryView();
    	
		GetServices();
						
	}
	    
	    private void initGaleryView() {
	    	
	    	mDataArray = new ArrayList<GaleryItem>();	    	
	    	
//	    	for (int i = 0; i < 10 ; i++) {
//	    		GaleryItem item; //https://simplycharlottemason.com/wp-content/uploads/2011/03/butterfly-homeschool-nature-study.jpg
//	    		item = new GaleryItem(i+1, "http://173.254.109.165/wp-content/uploads/2014/04/4112014_ArborDayRareBooks.jpg", null);
//	    		mDataArray.add(item);
//			}
	    	
	        //recyclerView.addItemDecoration(new MarginDecoration(this));
	        mGaleryView.setHasFixedSize(true);
	        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
	        
	        mGaleryView.setLayoutManager(mLayoutManager);
	        mAdapter = new AddServiceAdapter(getActivity(), mDataArray);
	        mGaleryView.setAdapter(mAdapter);
	        
	        
	    }
	    
	    public void selectService(){
	    	
			final String[] items = {"Service 1","Service 2","Service 3","Service 4"};

			new AlertDialog.Builder(getActivity()).setTitle(null)
			.setItems(items,  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mServiceEditText.setText(items[which].toString());

					dialog.dismiss();
				}
			}).create().show();
		}
	    
	    public void selectTime(){
	    	
			final String[] items = {"Time 1","Time 2","Time 3"};

			new AlertDialog.Builder(getActivity()).setTitle(null)
			.setItems(items,  new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mAverageEditText.setText(items[which].toString());

					dialog.dismiss();
				}
			}).create().show();
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
    	
		mAdapter.notifyDataSetChanged();
		
		GetServices();
		
		super.onResumeFragment();
	}
	
	private void refreshHeader(){
		((MainActivity)getActivity()).mTitleTextView.setText("Add New Service");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}
	
	public void setSelectedImagesFromInstagram(ArrayList<String> urls){
		for (String string : urls) {
			mDataArray.add(new GaleryItem(mDataArray.size()+1, string, null));
			mAdapter.setItems(mDataArray);
		}
	}
	
	/**
     * Method used to GetServices
     */
    public void GetServices(){

    }
    
    protected void GetInstagramImages() {
		try{
			
		URL example = new URL("https://api.instagram.com/v1/users/self/media/recent?access_token="
				+ ((BaseActivity)getActivity()).mApp.getAccessToken());
		
			URLConnection tc = example.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					tc.getInputStream()));

			ArrayList<String> imagesArrays = new ArrayList<String>();
			
			String line;
			while ((line = in.readLine()) != null) {
				JSONObject ob = new JSONObject(line);

				JSONArray object = ob.getJSONArray("data");

				for (int i = 0; i < object.length(); i++) {


					JSONObject jo = (JSONObject) object.get(i);
					JSONObject nja = (JSONObject) jo.getJSONObject("images");

					JSONObject purl3 = (JSONObject) nja
							.getJSONObject("thumbnail");
					Log.i("TAG", "" + purl3.getString("url"));
					imagesArrays.add(purl3.getString("url"));
				}

			}
			
			((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(GridImagesFragment.newInstance(this, imagesArrays));
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}
    
    OAuthAuthenticationListener listener = new OAuthAuthenticationListener() {

		@Override
		public void onSuccess() {
			GetInstagramImages();
        }

		@Override
		public void onFail(String error) {
			Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
		}
	};

    
    public class AddServiceAdapter extends RecyclerView.Adapter<AddServiceAdapter.CustomHolder> {

        Context context;
        private ArrayList<GaleryItem> items;

        public AddServiceAdapter(Context context, ArrayList<GaleryItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.galery_item, parent, false);
            return new CustomHolder(view);
        }

        public void setItems(ArrayList<GaleryItem> items) {
            this.items = items;
        }

        @Override
        public void onBindViewHolder(final CustomHolder holder, final int position) {

            final GaleryItem item = items.get(position);
            
            if(item.getBitmap() != null){
            	holder.image.setImageBitmap(item.getBitmap());
            }else{
            	// load url
            	ImageLoader.getInstance().displayImage(item.getUrl(), holder.image);
            	
            }

            holder.zoomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	AddServiceFragment.this.mGaleryView.smoothScrollToPosition(position);
                	
                	((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(ImageFullFragment.newInstance(item.getUrl(), item.getBitmap()));
                	
                }
            });
            
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	items.remove(position);
                	notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class CustomHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public ImageButton zoomBtn;
            public ImageButton deleteBtn;

            public CustomHolder(View view) {
                super(view);
                image = (ImageView) itemView.findViewById(R.id.image_view);
                zoomBtn = (ImageButton) itemView.findViewById(R.id.image_btn_zoom);
                deleteBtn = (ImageButton) itemView.findViewById(R.id.image_btn_delete);
            }
        }
    }

    public class GaleryItem {

        private int id;

        private String url;
        
        private Bitmap bitmap;

		public GaleryItem(int id, String url, Bitmap bitmap) {
			super();
			this.id = id;
			this.url = url;
			this.bitmap = bitmap;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}      

    }
}