package com.echances.etouches.fragments;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.echances.etouches.R;
import com.echances.etouches.activities.BaseActivity;
import com.echances.etouches.activities.MainActivity;
import com.echances.etouches.api.WebServiceApiImp;
import com.echances.etouches.api.WebServiceApi.WebServiceWaitingListener;
import com.echances.etouches.application.EtouchesApplicationCache;
import com.echances.etouches.model.GalleryImage;
import com.echances.etouches.model.GetOneServiceResponse;
import com.echances.etouches.model.Response;
import com.echances.etouches.model.ScheduleResponse;
import com.echances.etouches.tasks.PostBytesAsyncTask;
import com.echances.etouches.utilities.Constants;
import com.echances.etouches.utilities.DialogsModels;
import com.echances.etouches.utilities.Logr;
import com.google.gson.Gson;

/**
 * A placeholder fragment containing a simple view.
 */
public class ScheduleFragment extends BaseFragment {

	private View mRootView;
	private RecyclerView mListView;
	private Button mCancel, mAccept;
	
	ScheduleCollectionAdapter mAdapter;
	ArrayList<ScheduleItem> mDataArray;
	
	boolean isModifyMode;
	
	ScheduleResponse mScheduleResponse;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ScheduleFragment newInstance() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ScheduleFragment() {
    	
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        
        mListView = (RecyclerView) mRootView.findViewById(R.id.schedule_listview);
        
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
				changeMode();
				fillSchedule();
			}
		});
    	
    	mAccept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isModifyMode)
					sendSchedule();
				else
					changeMode();
			}
		});
    	
    }

	private void initView() {
		// TODO Auto-generated method stub
    	initCollectionView();
    	
    	mAdapter.setEnable(false);
    	
    	getSchedule();
				
	}
    
    private void initCollectionView() {
    	
    	mDataArray = new ArrayList<ScheduleFragment.ScheduleItem>();
    	
    	String [] days = {"","Mon.","Tue.","Wed.","Thu.","Fri.","Sat.","Sun."};
    	
    	String [] hours = {"","08:00","10:00","12:00","14:00","16:00","18:00","20:00","22:00","00:00","02:00","04:00","06:00"};
    	
    	for (int i = 0; i < days.length * hours.length; i++) {
    		ScheduleFragment.ScheduleItem item;
    		if(i<8)
    			item = new ScheduleItem(false, days[i], false);
    		else if(i%8==0)
    			item = new ScheduleItem(false, hours[(i/8)], false);
    		else
    			item = new ScheduleItem(true, "", false);
    		mDataArray.add(item);
		}
    	
        //mListView.addItemDecoration(new MarginDecoration(this));
    	mListView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 8);
        
        mListView.setLayoutManager(mLayoutManager);
        mAdapter = new ScheduleCollectionAdapter(getActivity(), mDataArray);
        mListView.setAdapter(mAdapter);
    }
    
    private void changeMode(){
    	isModifyMode = !isModifyMode;
    	if(isModifyMode){
    		mCancel.setVisibility(View.VISIBLE);
    		mAccept.setText("Validate");
    	}else{
    		mCancel.setVisibility(View.GONE);
    		mAccept.setText("Modify Your Schedule");
    	}
    	
    	mAdapter.setEnable(isModifyMode);
    	mAdapter.notifyDataSetChanged();
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
		((MainActivity)getActivity()).mTitleTextView.setText("Schedule");
		((MainActivity)getActivity()).mLeftImageView.setVisibility(View.GONE);
    	((MainActivity)getActivity()).mRightImageView.setVisibility(View.GONE);
    	
	}
    
    private void fillSchedule() {
		// TODO Auto-generated method stub
    	for (int i=0; i<mAdapter.getItems().size(); i++) {
			ScheduleItem item = mAdapter.getItems().get(i);
			if(item.isIsData()){
				item.setChecked(false);
			}
    	}
    	
    	for (int hour : mScheduleResponse.getMON()) {
			mAdapter.getItems().get(getIndex(hour)+1).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getTUE()) {
			mAdapter.getItems().get(getIndex(hour)+2).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getWED()) {
			mAdapter.getItems().get(getIndex(hour)+3).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getTHU()) {
			mAdapter.getItems().get(getIndex(hour)+4).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getFRI()) {
			mAdapter.getItems().get(getIndex(hour)+5).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getSAT()) {
			mAdapter.getItems().get(getIndex(hour)+6).setChecked(true);
		}
    	
    	for (int hour : mScheduleResponse.getSUN()) {
			mAdapter.getItems().get(getIndex(hour)+7).setChecked(true);
		}
    	
    	mAdapter.notifyDataSetChanged();

	}
    
    private int getIndex(int hour){
    	if(hour < 8)
    		hour = hour + 24;
    	
    	int index = ((hour - 6)/2)*8;

    	return index;
    }

    private void setNewSchedule(ScheduleJson newSchedule) {
		// TODO Auto-generated method stub
		mScheduleResponse.setMON(newSchedule.getMON());
		mScheduleResponse.setTUE(newSchedule.getTUE());
		mScheduleResponse.setWED(newSchedule.getWED());
		mScheduleResponse.setTHU(newSchedule.getTHU());
		mScheduleResponse.setFRI(newSchedule.getFRI());
		mScheduleResponse.setSAT(newSchedule.getSAT());
		mScheduleResponse.setSUN(newSchedule.getSUN());
	}
    
    private void sendSchedule() {
		// TODO Auto-generated method stub
    	
    	DialogsModels.showLoadingDialog(getActivity());
    	
    	final ScheduleJson scheduleJson = new ScheduleJson();
    	
    	scheduleJson.initArrays();
    	
		for (int i=0; i<mAdapter.getItems().size(); i++) {
			ScheduleItem item = mAdapter.getItems().get(i);
			if(item.isIsData()){
				if(item.isChecked){
					int hour = getHour(i);
					if(i%8==1)
						scheduleJson.getMON().add(hour);
					if(i%8==2)
						scheduleJson.getTUE().add(hour);
					if(i%8==3)
						scheduleJson.getWED().add(hour);
					if(i%8==4)
						scheduleJson.getTHU().add(hour);
					if(i%8==5)
						scheduleJson.getFRI().add(hour);
					if(i%8==6)
						scheduleJson.getSAT().add(hour);
					if(i%8==7)
						scheduleJson.getSUN().add(hour);
				}
			}
		}
		
		String scheduleString = new Gson().toJson(scheduleJson);
		Log.i("schedule", scheduleString);
		
		byte[] bytes = scheduleString.getBytes(Charset.forName("UTF-8"));

		new PostBytesAsyncTask(Constants.ParamsWebService.SERVER_NAME + Constants.ParamsWebService.SET_SCHEDULE+"?uid="+EtouchesApplicationCache.getInstance().getUserId()){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);

				if(result == null){
					DialogsModels.showErrorDialog(getActivity(), "Problem to connect to server");
				}else{

					Response response = new Gson().fromJson(result, Response.class);
					if(response.isSucces()){
						changeMode();
						setNewSchedule(scheduleJson);
					}else {
						DialogsModels.showErrorDialog(getActivity(), response.getMessage());
					}
				}

				DialogsModels.hideLoadingDialog();

			}
		}.execute(bytes);

		
	}

	private int getHour(int index){
    	int hour = (index/8)*2 + 6;
    	if(hour > 22)
    		hour = hour - 24;
    	return hour;
    }
    
    private void getSchedule(){
    	DialogsModels.showLoadingDialog(getActivity());
		WebServiceApiImp.getInstance((BaseActivity)getActivity()).GetSchedule(EtouchesApplicationCache.getInstance().getUserId()+"",
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

					mScheduleResponse = new ScheduleResponse();
					try {
						mScheduleResponse = ((ScheduleResponse) data);						

						if(mScheduleResponse.getMON() == null)
							mScheduleResponse.initArrays();
						
						fillSchedule();
						
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

	public class ScheduleCollectionAdapter extends RecyclerView.Adapter<ScheduleCollectionAdapter.CustomHolder> {

        Context context;
        private ArrayList<ScheduleItem> items;
        private boolean isEnable;        

        public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}

		public ScheduleCollectionAdapter(Context context, ArrayList<ScheduleItem> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_item, parent, false);
            return new CustomHolder(view);
        }

        public void setItems(ArrayList<ScheduleItem> items) {
            this.items = items;
        }
        
        public ArrayList<ScheduleItem> getItems() {
            return items;
        }

        @Override
        public void onBindViewHolder(final CustomHolder holder, final int position) {

            final ScheduleItem item = items.get(position);
            
            if(item.isIsData()){
            	holder.checkBox.setVisibility(View.VISIBLE);
            }else{
            	holder.checkBox.setVisibility(View.INVISIBLE);
            }

            holder.checkBox.setChecked(item.isChecked());
            
            holder.checkBox.setEnabled(isEnable);

            holder.titleTextView.setText(item.getTitle());

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                	item.setChecked(!item.isChecked());
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class CustomHolder extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public CheckBox checkBox;

            public CustomHolder(View view) {
                super(view);
                titleTextView = (TextView) itemView.findViewById(R.id.title_text);
                checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
            }
        }
    }

    public class ScheduleItem {

        private boolean isData;

        private String title;
        
        private boolean isChecked;

		public ScheduleItem(boolean isData, String title, boolean isChecked) {
			super();
			this.isData = isData;
			this.title = title;
			this.isChecked = isChecked;
		}

		public boolean isIsData() {
			return isData;
		}

		public void setIsData(boolean isData) {
			this.isData = isData;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}   

    }

    public class ScheduleJson {
    	
    	ArrayList<Integer> SAT;
    	ArrayList<Integer> SUN;
    	ArrayList<Integer> MON;
    	ArrayList<Integer> TUE;
    	ArrayList<Integer> WED;
    	ArrayList<Integer> THU;
    	ArrayList<Integer> FRI;
    	
		public ArrayList<Integer> getSAT() {
			return SAT;
		}
		public void setSAT(ArrayList<Integer> sAT) {
			SAT = sAT;
		}
		public ArrayList<Integer> getSUN() {
			return SUN;
		}
		public void setSUN(ArrayList<Integer> sUN) {
			SUN = sUN;
		}
		public ArrayList<Integer> getMON() {
			return MON;
		}
		public void setMON(ArrayList<Integer> mON) {
			MON = mON;
		}
		public ArrayList<Integer> getTUE() {
			return TUE;
		}
		public void setTUE(ArrayList<Integer> tUE) {
			TUE = tUE;
		}
		public ArrayList<Integer> getWED() {
			return WED;
		}
		public void setWED(ArrayList<Integer> wED) {
			WED = wED;
		}
		public ArrayList<Integer> getTHU() {
			return THU;
		}
		public void setTHU(ArrayList<Integer> tHU) {
			THU = tHU;
		}
		public ArrayList<Integer> getFRI() {
			return FRI;
		}
		public void setFRI(ArrayList<Integer> fRI) {
			FRI = fRI;
		}
    	
    	public void initArrays(){
    		SAT = new ArrayList<Integer>();
    		SUN = new ArrayList<Integer>();
    		MON = new ArrayList<Integer>();
    		TUE = new ArrayList<Integer>();
    		WED = new ArrayList<Integer>();
    		THU = new ArrayList<Integer>();
    		FRI = new ArrayList<Integer>();
    	}
    	/*
    	 * {
    "SAT": [],
    "SUN": [],
    "MON": [],
    "TUE": [
        14,
        16,
        18,
        20,
        22
    ],
    "WED": [
        14,
        16,
        18,
        20,
        22
    ],
    "THU": [
        14,
        16,
        18,
        20,
        22
    ],
    "FRI": []
}*
    	 */
    }

}