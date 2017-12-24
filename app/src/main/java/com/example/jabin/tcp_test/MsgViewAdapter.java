package com.example.jabin.tcp_test;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Jabin on 2017/12/24.
 */

public class MsgViewAdapter extends RecyclerView.Adapter {

    public Context context;
    public int resId;
    public Integer myId;
    public List<ChatMessage> chatMessages;

    MsgViewAdapter(Context context, @LayoutRes int resId, Integer myId, List<ChatMessage> chatMessages) {
        this.context = context;
        this.resId = resId;
        this.myId = myId;
        this.chatMessages = chatMessages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(context, resId, parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MsgViewHolder msgViewHolder = (MsgViewHolder) holder;
        msgViewHolder.bind(myId, chatMessages.get(position));
    }

    @Override
    public int getItemCount() {
        if (chatMessages == null) {
            return 0;
        }
        return chatMessages.size();
    }
}
