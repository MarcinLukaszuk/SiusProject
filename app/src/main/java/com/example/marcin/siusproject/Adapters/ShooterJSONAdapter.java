package com.example.marcin.siusproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcin.siusproject.Fragments.ShooterFragment;
import com.example.marcin.siusproject.Fragments.ShootsFragment;
import com.example.marcin.siusproject.MainActivity;
import com.example.marcin.siusproject.MyAdapter;
import com.example.marcin.siusproject.R;
import com.example.marcin.siusproject.ShooterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class ShooterJSONAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;
    Activity currentActivity;

    public ShooterJSONAdapter(Context context, LayoutInflater inflater, Activity _currentActivity) {
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
        ShooterJSONAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shooter_row, null);
            holder = new ShooterJSONAdapter.ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.shooter_name);
            holder.FullResult = (TextView) convertView.findViewById(R.id.shooter_full_result);
            holder.Recycler = (RecyclerView) convertView.findViewById(R.id.cardView);
            convertView.setTag(holder);
        } else {
            holder = (ShooterJSONAdapter.ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);

        String name = "";
        JSONArray shoots;
        if (jsonObject.has("Name")) {
            name = jsonObject.optString("Name");
        }

        if (jsonObject.has("Surname")) {
            name += " ";
            name += jsonObject.optString("Surname");
        }
        if (jsonObject.has("Shoots")) {
            shoots = jsonObject.optJSONArray("Shoots");

            holder.FullResult.setText(getFullResult(shoots));

            holder.Recycler.setHasFixedSize(true);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(currentActivity);
            MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.Recycler.setAdapter(new MyAdapter(parseShootsToSeries(shoots)));
            holder.Recycler.setLayoutManager(MyLayoutManager);
        }
        holder.Name.setText(name);
        return convertView;
    }

    private JSONArray parseShootsToSeries(JSONArray jArray) {
        JSONArray results = new JSONArray();
        Double tenShootsValue = 0.0;
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

                if ((i + 1) % 10 == 0) {
                    JSONObject newObject = new JSONObject();
                    newObject.put("Value", df.format(tenShootsValue));
                    results.put(newObject);
                    tenShootsValue = 0.0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        try {
            if (jArray.length() % 10 != 0) {
                JSONObject newObject = new JSONObject();
                newObject.put("Value", df.format(tenShootsValue));
                results.put(newObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }

    private String getFullResult(JSONArray jArray) {
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
        }
        resultString += df.format(tenShootsValue);


        return resultString;
    }

    private static class ViewHolder {
        public TextView Name;
        public TextView FullResult;
        public RecyclerView Recycler;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
