package com.example.marcin.siusproject.CustomViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.marcin.siusproject.R;

public class ShooterSerieViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;

    public ShooterSerieViewHolder(View v) {
        super(v);
        titleTextView = (TextView) v.findViewById(R.id.shootTextView);
    }
}