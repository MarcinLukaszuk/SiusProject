package com.example.marcin.siusproject.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marcin.siusproject.CustomViewHolders.ShooterShootViewHolder;
import com.example.marcin.siusproject.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShooterShootJSONAdapter extends RecyclerView.Adapter<ShooterShootViewHolder> {

    JSONArray mJsonArray;

    public ShooterShootJSONAdapter(JSONArray _mJsonArray) {
        mJsonArray = _mJsonArray;
    }

    public JSONObject getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public ShooterShootViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shoot, parent, false);
        ShooterShootViewHolder holder = new ShooterShootViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ShooterShootViewHolder holder, int position) {
        JSONObject object = getItem(position);
        String stringValue = "";
        if (object.has("value")) {
            stringValue = object.optString("value");
        }
        holder.titleTextView.setText(stringValue);
    }

    @Override
    public int getItemCount() {
        return mJsonArray.length();
    }
}
