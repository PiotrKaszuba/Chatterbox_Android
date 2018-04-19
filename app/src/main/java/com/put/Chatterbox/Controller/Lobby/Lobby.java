package com.put.Chatterbox.Controller.Lobby;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * Created by Piotr on 2018-04-19.
 */

public interface Lobby {

    public void loadView(Adapter chatList, AdapterView view);
    public Adapter fetchFromCache();
    public Adapter fetchFromDatabase();
    public void onItemClick(AdapterView parent, View v, int position, long id);
    public void refresh(AdapterView view);
}
