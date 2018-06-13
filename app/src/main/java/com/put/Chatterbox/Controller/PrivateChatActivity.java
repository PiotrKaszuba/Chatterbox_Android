package com.put.Chatterbox.Controller;

/**
 * Created by A on 21.05.2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateChatActivity extends AppCompatActivity {

    private ListView listView;
    //public List<PrivateChatItem> privateChatItems = new ArrayList<PrivateChatItem>();
    private ArrayAdapter<PrivateChatItem> privateChatItemAdapter;
    private String userId;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        context = this;

        listView = (ListView) findViewById(R.id.privateChatList);

        /*privateChatItemAdapter = new PrivateChatLobbyAdapter(this, R.layout.private_chat_lobby_item,  privateChatItems);
        listView.setAdapter(privateChatItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent privateChatIntent = new Intent(context, ChatActivity.class);
                privateChatIntent.putExtra("chatId",  privateChatItems.get(position).getChatId());
                startActivity(privateChatIntent);

            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            userId = uid; // brak danych = crash
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference channelReference = databaseReference.child("PrivateChats");

        channelReference.orderByChild("lastMessageTimestamp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PrivateChatItem privateChatItem = dataSnapshot.getValue(PrivateChatItem.class);
                privateChatItems.add(privateChatItem);
                privateChatItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

       // PrivateChatController.fillPrivateChatItem(this);
        PrivateChatController.checkUserIdChatId(this);
        PrivateChatController.checkSenderIdLastMessage(this);
        PrivateChatController.checkUserIdUsername(this);
        PrivateChatController.changeUserIdUsername(this);


    }

    public void updatePrivateChats(final ArrayList<PrivateChatItem> privateChatItems)
    {
        int a=0;
        privateChatItemAdapter = new PrivateChatLobbyAdapter(this, R.layout.private_chat_lobby_item,  privateChatItems);
        listView.setAdapter(privateChatItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent privateChatIntent = new Intent(context, ChatActivity.class);
                privateChatIntent.putExtra("chatId",  privateChatItems.get(position).getChatId());
                startActivity(privateChatIntent);

            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            userId = uid; // brak danych = crash
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference channelReference = databaseReference.child("PrivateChats");

        channelReference.orderByChild("lastMessageTimestamp").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // PrivateChatItem privateChatItem = dataSnapshot.getValue(PrivateChatItem.class);
              //  privateChatItems.add(privateChatItem);
                privateChatItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
