package com.put.Chatterbox.Controller.Lobby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class ChannelLobby extends AppCompatActivity implements Lobby {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }


    @Override
    public void loadView(Adapter chatList, AdapterView view) {

    }

    @Override
    public Adapter fetchFromCache() {
        return null;
    }

    @Override
    public Adapter fetchFromDatabase() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView parent, View v, int position, long id) {

    }

    @Override
    public void refresh(AdapterView view) {

    }
}
