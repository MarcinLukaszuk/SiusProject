package com.example.marcin.siusproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcin.siusproject.CustomViewHolders.ShooterSerieViewHolder;
import com.example.marcin.siusproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShooterSerieJSONAdapter extends RecyclerView.Adapter<ShooterSerieViewHolder> {

    JSONArray mJsonArray;

    public ShooterSerieJSONAdapter(JSONArray _mJsonArray) {
        mJsonArray = _mJsonArray;
    }

    public JSONObject getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public ShooterSerieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shoot, parent, false);
        ShooterSerieViewHolder holder = new ShooterSerieViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ShooterSerieViewHolder holder, int position) {
        JSONObject object = getItem(position);
        String stringValue = "";
        if (object.has("Value")) {
            stringValue = object.optString("Value");
        }
        holder.titleTextView.setText(stringValue);
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }
}
