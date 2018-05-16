package com.put.Chatterbox.Controller.Lobby;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.put.Chatterbox.Controller.ChatActivity;
import com.put.Chatterbox.Controller.UserList;
import com.put.Chatterbox.Model.Channel;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class ChannelLobby extends AppCompatActivity implements Lobby {

    final List<Channel> channelList;
    ListAdapter adapter;
    GridView kafelki;
    final ChannelLobby channelLobby;
    User user;
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
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        BottomNavigationView bnv = this.findViewById(R.id.bottom_navigation);
        final Context instance = this;
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent = null;
                switch(item.getItemId()){

                    case R.id.menuUsers:
                         myIntent = new Intent(instance, UserList.class);
                        myIntent.putExtra("user", user);
                        instance.startActivity(myIntent);
                        break;
                    case R.id.menuPrivate:
                        myIntent = new Intent(instance, UserList.class);
                        myIntent.putExtra("user", user);
                        instance.startActivity(myIntent);
                        break;



                }
                return true;
            }
        });

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
        //przechwytujemy instację danych i ustawiamy miejsce czytania na kanały "Channels"
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Channels");


        //dodajemy listener zmian potomków drzewa o korzeniu w punkcie kanały "Channels" i sortujemy po ostatniej wiadomości
        ref.orderByChild("lastMessageTimestamp").addChildEventListener(new ChildEventListener() {


            //przy dodaniu węzła (oraz po ustawieniu listenera jedno wywołanie na każdy już istniejący węzeł)
            //dodajemy węzeł do list kanałów i odświeżamy widok
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                c.setId(dataSnapshot.getKey());
                channelList.add(c);
                refresh();
                Log.i("FireInfo: ", "Added channel name: "+ c.channelName);
            }

            //przy zmianie węzła usuwamy jego starą wersję i dodajemy nową
            //porównanie jest po niezmiennej wartości - nazwie kanału
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                c.setId(dataSnapshot.getKey());
                channelList.remove(c);
                channelList.add(c);
                refresh();
                Log.i("FireInfo: ", "Changed channel name: "+ c.channelName);
            }

            //przy usunięciu węzła usuwamy go z listy i odświeżamy widok
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Channel c = dataSnapshot.getValue(Channel.class);
                c.setId(dataSnapshot.getKey());
                channelList.remove(c);
                refresh();
                Log.i("FireInfo: ", "Removed channel name: "+ c.channelName);
            }

            //przy przesunięciu węzła tzn zmianie kolejności po posortowaniu usuwamy węzeł i dodajemy na początku listy - najnowsza wiadomość
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Channel c = dataSnapshot.getValue(Channel.class);
                c.setId(dataSnapshot.getKey());
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

        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().;
        for(DataSnapshot ds : databaseReference.("Channels"))*/

        String chId = channelList.get(i).getId();

        Intent chatIntent = new Intent(this, ChatActivity.class);
        chatIntent.putExtra("channelId", channelList.get(i).getId());
        startActivity(chatIntent);


    }
    @Override
    public void refresh() {

        ((BaseAdapter) kafelki.getAdapter()).notifyDataSetChanged();
    }
}
