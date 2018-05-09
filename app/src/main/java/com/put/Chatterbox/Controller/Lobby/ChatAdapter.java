package com.put.Chatterbox.Controller.Lobby;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.put.Chatterbox.Model.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotr on 2018-05-09.
 */

public class ChatAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Channel> channels;

    // 1
    public ChatAdapter(Context context, List channels) {
        this.mContext = context;
        this.channels = channels;
    }

    // 2
    @Override
    public int getCount() {
        return channels.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Channel getItem(int position) {
        return channels.get(position);
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(channels.get(position).channelName);

        return dummyTextView;
    }
}
