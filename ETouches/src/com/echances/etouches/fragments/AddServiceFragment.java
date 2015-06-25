package com.echances.etouches.fragments;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
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
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.model.GalleryImage;
import com.echances.etouches.model.GetOneServiceResponse;
import com.echances.etouches.model.GetServicesResponse;
import com.echances.etouches.model.Hour;
import com.echances.etouches.model.InstagramUrlsResponse;
import com.echances.etouches.model.OneService;
import com.echances.etouches.model.Response;
import com.echances.etouches.model.Service;
import com.echances.etouches.tasks.PostBytesAsyncTask;
import com.echances.etouches.utilities.Constants;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddServiceFragment extends BaseFragment {

	private static String ARG_1 = "serviceId";

	private RecyclerView mGaleryView;
	private EditText mServiceEditText, mPriceEditText, mAverageEditText;
	private Button addImageInstagramButton, uploadImageButton, mCancel, mAccept;

	AddServiceAdapter mAdapter;
	ArrayList<GalleryImage> mDataArray;

	private int mServiceId;
	
	private int selectedServiceIndex = -1;
	private int selectedAverageHourIndex = -1;

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static AddServiceFragment newInstance(int serviceId) {
		AddServiceFragment fragment = new AddServiceFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_1, serviceId);
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

		mServiceId = getArguments().getInt(ARG_1);

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
				if(mServiceId!=0)
					UpdateService();
				else
					AddService();
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
						//						mDataArray.add(new GaleryItem(mDataArray.size()+1, "", pickedPhoto));
						//						mAdapter.setItems(mDataArray);
						//						mAdapter.notifyDataSetChanged();

						DialogsModels.showLoadingDialog(getActivity());

						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						// Must compress the Image to reduce image size to make upload easy
						pickedPhoto.compress(Bitmap.CompressFormat.PNG, 50, stream); 
						byte[] bytes = stream.toByteArray();

						new PostBytesAsyncTask(Constants.ParamsWebService.SERVER_NAME + Constants.ParamsWebService.UPLOAD_IMAGE+"?uid="+EtouchesApplicationCache.getInstance().getUserId()+"&sid="+mServiceId){
							@Override
							protected void onPostExecute(String result) {
								// TODO Auto-generated method stub
								super.onPostExecute(result);

								if(result == null){
									DialogsModels.showErrorDialog(getActivity(), "Problem to connect to server");
								}else{

									GalleryImage galleryImage = new Gson().fromJson(result, GalleryImage.class);
									if(galleryImage.isSucces()){
										mAdapter.getItems().add(galleryImage);
										mAdapter.notifyDataSetChanged();
									}else {
										DialogsModels.showErrorDialog(getActivity(), galleryImage.getMessage());
									}
								}

								DialogsModels.hideLoadingDialog();

							}
						}.execute(bytes);

					}
				});

			}
		});


	}

	private void initView() {
		// TODO Auto-generated method stub

		if(mServiceId!=0){
			addImageInstagramButton.setVisibility(View.VISIBLE);
			uploadImageButton.setVisibility(View.VISIBLE);
			mGaleryView.setVisibility(View.VISIBLE);
			mServiceEditText.setEnabled(false);
			GetOneService();
		}

		initGaleryView();

	}

	private void initGaleryView() {

		mDataArray = new ArrayList<GalleryImage>();	    	

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

	private void fillFields(OneService service){
		mServiceEditText.setText(service.getSEN());
		mPriceEditText.setText(service.getP()+"");
		mAverageEditText.setText(getHourStringForId(service.getD()));
		mAdapter.setItems(service.getGallery());
		mAdapter.notifyDataSetChanged();
	}
	
	private String getHourStringForId(int hourId){
		for (Hour hour : ((MainActivity)getActivity()).hours) {
			if(hour.getValue() == hourId)
				return hour.getText();
		}
		return "0:30";
	}

	public void selectService(){

		final String[] items = new String[((MainActivity)getActivity()).Services.size()];
		
		for (int i=0; i<((MainActivity)getActivity()).Services.size(); i++) {
			items[i] = ((MainActivity)getActivity()).Services.get(i).getSEN();
		}

		new AlertDialog.Builder(getActivity()).setTitle(null)
		.setItems(items,  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mServiceEditText.setText(items[which].toString());
				selectedServiceIndex = which;
				dialog.dismiss();
			}
		}).create().show();
	}

	public void selectTime(){

		final String[] items = new String[((MainActivity)getActivity()).hours.size()];
		
		for (int i=0; i<((MainActivity)getActivity()).hours.size(); i++) {
			items[i] = ((MainActivity)getActivity()).hours.get(i).getText();
		}
		
		new AlertDialog.Builder(getActivity()).setTitle(null)
		.setItems(items,  new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mAverageEditText.setText(items[which].toString());
				selectedAverageHourIndex = which;
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

		super.onResumeFragment();
	}

	private void refreshHeader(){
		((MainActivity)getActivity()).mTitleTextView.setText("Add New Service");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.VISIBLE);
		((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
	}

	public void setSelectedImagesFromInstagram(ArrayList<String> urls){
		
		DialogsModels.showLoadingDialog(getActivity());
		
		InstagramJson instagramJson = new InstagramJson();
		instagramJson.url = urls;
		
		String urlsString = new Gson().toJson(instagramJson);
		Log.i("urlsString", urlsString);
		
		byte[] bytes = urlsString.getBytes(Charset.forName("UTF-8"));
		
		new PostBytesAsyncTask(Constants.ParamsWebService.SERVER_NAME + Constants.ParamsWebService.UPLOAD_IMAGE_URL+"?uid="+EtouchesApplicationCache.getInstance().getUserId()+"&sid="+mServiceId){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				if(result == null){
					DialogsModels.showErrorDialog(getActivity(), "Problem to connect to server");
				}else{

					InstagramUrlsResponse instagramUrlsResponse = new Gson().fromJson(result, InstagramUrlsResponse.class);
					
					if(instagramUrlsResponse.isSucces()){
						
						for (GalleryImage galleryImage : instagramUrlsResponse.getUrls()) {
							mAdapter.getItems().add(galleryImage);
						}
						
						mAdapter.notifyDataSetChanged();
					}else {
						DialogsModels.showErrorDialog(getActivity(), instagramUrlsResponse.getMessage());
					}
				}

				DialogsModels.hideLoadingDialog();

			}
		}.execute(bytes);
		
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

	/**
	 * Method used to GetServices
	 */
	public void GetOneService(){

		DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).GetOneServices(EtouchesApplicationCache.getInstance().getUserId()+"", mServiceId+"",
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

					//DialogsModels.showErrorDialog(getActivity(), "jsdh jdhf lhd lknbnb");

					GetOneServiceResponse result = new GetOneServiceResponse();
					try {
						result = ((GetOneServiceResponse) data);
						Log.i(TAG, result.getServics().get(0).getSEN());
						fillFields(result.getServics().get(0));

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

	
	/**
	 * Method used to GetServices
	 */
	public void UpdateService(){

		DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).UpdateService(EtouchesApplicationCache.getInstance().getUserId()+"", mServiceId+"", mPriceEditText.getText().toString(), ((MainActivity)getActivity()).hours.get(selectedAverageHourIndex).getValue()+"", "desc arab", "desc english",
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

					//DialogsModels.showErrorDialog(getActivity(), "jsdh jdhf lhd lknbnb");

					Response result = new Response();
					try {
						result = ((Response) data);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					((PlaceholderFragment)getParentFragment()).popBackStack();

				} else {
					DialogsModels.showErrorDialog(getActivity(), message);
				}

			}
		});

	}
	
	/**
	 * Method used to GetServices
	 */
	public void AddService(){

		DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).AddService(EtouchesApplicationCache.getInstance().getUserId()+"", ((MainActivity)getActivity()).Services.get(selectedServiceIndex).getID()+"", mPriceEditText.getText().toString(), ((MainActivity)getActivity()).hours.get(selectedAverageHourIndex).getValue()+"", "desc arab", "desc english",
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

					//DialogsModels.showErrorDialog(getActivity(), "jsdh jdhf lhd lknbnb");

					Response result = new Response();
					try {
						result = ((Response) data);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					((PlaceholderFragment)getParentFragment()).popBackStack();

				} else {
					DialogsModels.showErrorDialog(getActivity(), message);
				}

			}
		});

	}
	
	private void deleteImage(final int position){
		
		GalleryImage image = mAdapter.getItems().get(position);
		
		DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity) getActivity()).DeleteImage(image.getID()+"", image.getImg(), new WebServiceWaitingListener() {

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

					Response result = new Response();
					try {
						result = ((Response) data);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					mAdapter.getItems().remove(position);
					mAdapter.notifyDataSetChanged();
					
				} else {
					DialogsModels.showErrorDialog(getActivity(), message);
				}

			}
		});
	}

	public class AddServiceAdapter extends RecyclerView.Adapter<AddServiceAdapter.CustomHolder> {

		Context context;
		private ArrayList<GalleryImage> items;

		public AddServiceAdapter(Context context, ArrayList<GalleryImage> items) {
			this.context = context;
			this.items = items;
		}

		@Override
		public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.galery_item, parent, false);
			return new CustomHolder(view);
		}

		public void setItems(ArrayList<GalleryImage> items) {
			this.items = items;
		}

		public ArrayList<GalleryImage> getItems(){
			return items;
		}

		@Override
		public void onBindViewHolder(final CustomHolder holder, final int position) {

			final GalleryImage item = items.get(position);

			ImageLoader.getInstance().displayImage(item.getSurl(), holder.image);

			holder.zoomBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AddServiceFragment.this.mGaleryView.smoothScrollToPosition(position);

					((PlaceholderFragment)getParentFragment()).addFragmentWithHorizAnimation(ImageFullFragment.newInstance(item.getUrl(), null));

				}
			});

			holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AddServiceFragment.this.deleteImage(position);
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

	public class InstagramJson {
		public ArrayList<String> url;		
	}
	
}