package com.put.Chatterbox.Controller;

/**
 * Created by A on 21.05.2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class PrivateChatActivity extends AppCompatActivity {

    private ListView listView;
    private List<PrivateChatItem> privateChatItems;
    private ArrayAdapter<PrivateChatItem> privateChatItemAdapter;
    private String userId;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_chat);
        context = this;

        listView = (ListView) findViewById(R.id.privateChatList);

        privateChatItems = new ArrayList<>();
        privateChatItemAdapter = new PrivateChatLobbyAdapter(this, R.layout.private_chat_lobby_item, privateChatItems);
        listView.setAdapter(privateChatItemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent privateChatIntent = new Intent(context, ChatActivity.class);
                privateChatIntent.putExtra("chatId", privateChatItems.get(position).getChatId());
                startActivity(privateChatIntent);

            }
        });


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            userId = uid; // brak danych = crash
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference channelReference = databaseReference.child("Users").child(userId);

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
        });


    }
}
