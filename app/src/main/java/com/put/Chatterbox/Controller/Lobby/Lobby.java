package com.put.Chatterbox.Controller.Lobby;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.put.Chatterbox.Model.Channel;

import java.util.List;

/**
 * Created by Piotr on 2018-04-19.
 */

public interface Lobby {

    public void loadView(ListAdapter chatList, AdapterView view);
    public void fetchFromCache();
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
    public void refresh();
    void connectDatabase();
}
