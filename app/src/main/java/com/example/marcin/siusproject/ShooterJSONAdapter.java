package com.example.marcin.siusproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;


public class ShooterJSONAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public ShooterJSONAdapter(Context context, LayoutInflater inflater) {
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
        ShooterJSONAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.shooter_row, null);
            holder = new ShooterJSONAdapter.ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.shooter_name);
            holder.Surname = (TextView) convertView.findViewById(R.id.shooter_surname);
            holder.Shoots = (TextView) convertView.findViewById(R.id.shooter_shoots);
            holder.FullResult = (TextView) convertView.findViewById(R.id.shooter_full_result);
            convertView.setTag(holder);
        } else {
            holder = (ShooterJSONAdapter.ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);

        String name = "";
        String surname = "";
        JSONArray shoots;
        if (jsonObject.has("Name")) {
            name = jsonObject.optString("Name");
        }

        if (jsonObject.has("Surname")) {
            surname = jsonObject.optString("Surname");
        }
        if (jsonObject.has("Shoots")) {
            shoots = jsonObject.optJSONArray("Shoots");
            holder.Shoots.setText(parseShootsToSeries(shoots));
            holder.FullResult.setText(getFullResult(shoots));


        }


        holder.Name.setText(name);
        holder.Surname.setText(surname);
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
                    tenShootsValue +=  Double.parseDouble(stringValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if ((i + 1) % 10 == 0) {
                resultString += df.format( tenShootsValue);
                resultString += " | ";
                tenShootsValue = 0.0;
            }
        }
        if (jArray.length() % 10 != 0) {
            resultString += tenShootsValue;
        }
        return resultString;
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
                    tenShootsValue +=  Double.parseDouble(stringValue);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }   resultString += df.format(tenShootsValue);


        return resultString;
    }

    private static class ViewHolder {
        public TextView Name;
        public TextView Surname;
        public TextView Shoots;
        public TextView FullResult;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
