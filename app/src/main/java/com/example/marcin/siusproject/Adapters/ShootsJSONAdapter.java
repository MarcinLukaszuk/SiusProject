package com.example.marcin.siusproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcin.siusproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ShootsJSONAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;
    Activity currentActivity;

    public ShootsJSONAdapter(Context context, LayoutInflater inflater, Activity _currentActivity) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
        currentActivity = _currentActivity;
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
        ShootsJSONAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shooter_info_row, null);
            holder = new ShootsJSONAdapter.ViewHolder();
            holder.ShootsSeries = (TextView) convertView.findViewById(R.id.shoots_series);
            holder.FullResult = (TextView) convertView.findViewById(R.id.shoots_full_result);
            holder.ShootsRecycler = (RecyclerView) convertView.findViewById(R.id.shoots_recycler);

            convertView.setTag(holder);
        } else {
            holder = (ShootsJSONAdapter.ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);

        String shootsString = "";
        String result = "";
        JSONArray shoots;
        if (jsonObject.has("shootsString")) {
            shootsString = jsonObject.optString("shootsString");
        }

        if (jsonObject.has("result")) {
            result = jsonObject.optString("result");
        }
        if (jsonObject.has("shoots")) {
            shoots = jsonObject.optJSONArray("shoots");

            holder.ShootsRecycler.setHasFixedSize(true);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(currentActivity);
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.ShootsRecycler.setAdapter(new ShooterShootJSONAdapter(shoots));
            holder.ShootsRecycler.setLayoutManager(MyLayoutManager);
        }


        //holder.ShootsSeries.setText(shootsString);
        //holder.FullResult.setText(result);
        return convertView;
    }

    private String parseShootsToSeries(JSONArray jArray) {
        Double tenShootsValue = 0.0;
        String resultString = "";
        DecimalFormat df = new DecimalFormat("#.0");
        for (int i = 0; i < jArray.length(); i++) {
            try {
                String stringValue = "";
                JSONObject object = jArray.getJSONObject(i);
                if (object.has("Value")) {
                    stringValue = object.optString("Value");
                }
                if (stringValue != "") {
                    tenShootsValue += Double.parseDouble(stringValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if ((i + 1) % 10 == 0) {
                resultString += df.format(tenShootsValue);
                resultString += " | ";
                tenShootsValue = 0.0;
            }
        }
        if (jArray.length() % 10 != 0) {
            resultString += tenShootsValue;
        }
        return resultString;
    }

    private static class ViewHolder {
        public TextView ShootsSeries;
        public TextView FullResult;
        public RecyclerView ShootsRecycler;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
