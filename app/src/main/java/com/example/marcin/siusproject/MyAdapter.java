package com.example.marcin.siusproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    JSONArray mJsonArray;

    public MyAdapter(JSONArray _mJsonArray) {
        mJsonArray = _mJsonArray;
    }

    public JSONObject getItem(int position) {
        return mJsonArray.optJSONObject(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shoot, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
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
