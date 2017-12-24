package com.example.jabin.tcp_test;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jabin on 2017/12/24.
 */

public class MsgViewHolder extends RecyclerView.ViewHolder {

    public TextView tvFromId;
    public TextView tvContent;

    public MsgViewHolder(Context context, @LayoutRes int lay, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(lay, parent, false));
        tvFromId = itemView.findViewById(R.id.from_id);
        tvContent = itemView.findViewById(R.id.content);
    }

    public void bind(final Integer myId, final ChatMessage item) {
        if (myId == item.fromId) {
            tvFromId.setTextColor(Color.GREEN);
            tvContent.setTextColor(Color.GREEN);
            tvFromId.setGravity(Gravity.END);
            tvContent.setGravity(Gravity.END);
        } else {
            tvFromId.setTextColor(Color.RED);
            tvContent.setTextColor(Color.RED);
            tvFromId.setGravity(Gravity.START);
            tvContent.setGravity(Gravity.START);
        }

        tvFromId.setText(item.fromId + "");
        tvContent.setText(item.content);
    }
}
