package com.put.Chatterbox.Controller.Lobby;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.put.Chatterbox.Model.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Piotr on 2018-05-09.
 */

public class LobbyAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<Channel> channels;

    // 1
    public LobbyAdapter(Context context, List channels) {
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
        Random r= new Random();

        int randomColor = Color.rgb(r.nextInt(256),r.nextInt(255),r.nextInt(256));
        dummyTextView.setBackgroundColor(randomColor);
        //dummyTextView.setBackground();

        dummyTextView.setPadding(10,10,10,10);
        dummyTextView.setGravity(Gravity.CENTER);

        dummyTextView.setText(channels.get(position).channelName);
        dummyTextView.setHeight(150);

        return dummyTextView;
    }
}
