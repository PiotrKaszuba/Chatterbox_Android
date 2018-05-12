package com.put.Chatterbox.Controller.Lobby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.put.Chatterbox.Model.Channel;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class ChannelLobby extends AppCompatActivity implements Lobby {

    final List<Channel> channelList;
    ListAdapter adapter;
    GridView kafelki;
    final ChannelLobby channelLobby;
    public ChannelLobby(){
        channelList = new ArrayList<>() ;
        channelLobby = this;
        adapter = new LobbyAdapter(this, channelList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.channellobby);
        //ustaw ID statyczne w gridview kafelki
        kafelki = findViewById(R.id.ChannelGrid);

        kafelki.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                channelLobby.onItemClick(adapterView, view, i, l);

            }
        });
        this.connectDatabase();
        this.loadView(adapter, kafelki);

    }


    @Override
    public void loadView(ListAdapter chatList, AdapterView view) {
        kafelki.setAdapter(chatList);
    }

    @Override
    public void fetchFromCache() {

    }


    public void connectDatabase(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Channels");

        // Attach a listener to read the data at our posts reference

        ref.orderByChild("lastMessageTimestamp").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                channelList.add(c);
                refresh();
                Log.i("FireInfo: ", "Added channel name: "+ c.channelName);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                channelList.remove(c);
                channelList.add(c);
                refresh();
                Log.i("FireInfo: ", "Changed channel name: "+ c.channelName);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Channel c = dataSnapshot.getValue(Channel.class);
                channelList.remove(c);
                refresh();
                Log.i("FireInfo: ", "Removed channel name: "+ c.channelName);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                channelList.remove(c);
                channelList.add(0, c);
                refresh();
                Log.i("FireInfo: ", "Moved channel name: "+ c.channelName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String t = channelList.get(i).channelName;
        Toast.makeText(channelLobby, "Chose chat with name: " + channelList.get(i).channelName,
                Toast.LENGTH_SHORT).show();

    }
    @Override
    public void refresh() {

        ((BaseAdapter) kafelki.getAdapter()).notifyDataSetChanged();
    }
}
