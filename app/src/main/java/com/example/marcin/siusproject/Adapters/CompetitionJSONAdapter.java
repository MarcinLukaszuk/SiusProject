package com.example.marcin.siusproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.marcin.siusproject.Helper;
import com.example.marcin.siusproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class CompetitionJSONAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public CompetitionJSONAdapter(Context context, LayoutInflater inflater) {
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
            convertView = mInflater.inflate(R.layout.competition_row, null);
            holder = new ViewHolder();
            holder.competitionName = (TextView) convertView.findViewById(R.id.competition_name);

            holder.startDate = (TextView) convertView.findViewById(R.id.start_date_competition);
            holder.endDate = (TextView) convertView.findViewById(R.id.end_date_competition);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JSONObject jsonObject = (JSONObject) getItem(position);
        String competitionName = "";
        String startDate = "";
        String endDate = "";
        if (jsonObject.has("CompetitionName")) {
            competitionName = jsonObject.optString("CompetitionName");
        }
        if (jsonObject.has("StartDate")) {
            startDate = jsonObject.optString("StartDate");
        }
        if (jsonObject.has("EndDate")) {
            endDate = jsonObject.optString("EndDate");
        }

        holder.competitionName.setText(competitionName);
        holder.startDate.setText(Helper.GetDateStringFromDateString( startDate));
        holder.endDate.setText(Helper.GetDateStringFromDateString(endDate));
        return convertView;
    }

    private static class ViewHolder {
        public TextView competitionName;
        public TextView startDate;
        public TextView endDate;
    }

    public void updateData(JSONArray jsonArray) {
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
