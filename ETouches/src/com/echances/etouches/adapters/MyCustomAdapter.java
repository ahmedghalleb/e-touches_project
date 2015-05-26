package com.echances.etouches.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.echances.etouches.R;
import com.echances.etouches.model.Service;

public class MyCustomAdapter extends BaseAdapter {

    private ArrayList<Service> mData;
    private ArrayList<Service> orig;
    
	private Context mContext;

    public MyCustomAdapter(Context context, ArrayList<Service> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Service getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, null);
            holder.textView = (TextView)convertView.findViewById(R.id.title_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(mData.get(position).getSEN());
        return convertView;
    }
    
    public ArrayList<Service> getData() {
		return mData;
	}

	public void setData(ArrayList<Service> Data) {
		this.mData = Data;
	}
    
    public static class ViewHolder {
        public TextView textView;
    }
    
    public Filter getFilter() {
	    return new Filter() {

	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            final FilterResults oReturn = new FilterResults();
	            final ArrayList<Service> results = new ArrayList<Service>();
	            if (orig == null)
	                orig = mData;
	            if (constraint != null) {
	                if (orig != null && orig.size() > 0) {
	                    for (final Service g : orig) {
	                        if (g.getSEN().toLowerCase().contains(constraint.toString()))
	                            results.add(g);
	                    }
	                }
	                oReturn.values = results;
	            }

	            return oReturn;
	        }

	        @SuppressWarnings("unchecked")
	        @Override
	        protected void publishResults(CharSequence constraint,
	                FilterResults results) {
	            mData = (ArrayList<Service>) results.values;
	            notifyDataSetChanged();
	        }
	    };
	}

}
