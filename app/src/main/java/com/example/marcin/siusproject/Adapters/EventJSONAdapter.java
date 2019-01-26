package com.example.marcin.siusproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcin.siusproject.Helper;
import com.example.marcin.siusproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventJSONAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public EventJSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }

    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.event_row, null);
            holder = new ViewHolder();
            holder.eventName = (TextView) convertView.findViewById(R.id.event_name);
            holder.cityName = (TextView) convertView.findViewById(R.id.city_name);

            holder.startDate = (TextView) convertView.findViewById(R.id.start_date);
            holder.endDate = (TextView) convertView.findViewById(R.id.end_date);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);
        String eventName = "";
        String  cityName = "";
        String  startDate = "";
        String  endDate = "";

        if (jsonObject.has("Name"))
            eventName = jsonObject.optString("Name");
        if (jsonObject.has("StartDate"))
            startDate = jsonObject.optString("StartDate");
        if (jsonObject.has("CityName"))
            cityName = jsonObject.optString("CityName");
        if (jsonObject.has("EndDate"))
            endDate = jsonObject.optString("EndDate");


        holder.eventName.setText(eventName);
        holder.cityName.setText(cityName);

       holder.startDate.setText(Helper.GetDateStringFromDateString(startDate)  )   ;
        holder.endDate.setText(Helper.GetDateStringFromDateString( endDate));
        return convertView;
    }

    private static class ViewHolder {
        public TextView eventName;
        public TextView startDate;
        public TextView endDate;
        public TextView cityName;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
