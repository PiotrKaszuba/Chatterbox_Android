package com.put.Chatterbox.Controller;

/**
 * Created by A on 15.05.2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Model.ChatBubble;
import com.put.Chatterbox.Model.Message;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listView;
    private View btnSend;
    private EditText editText;
    private  String userId;
    private String chatId;
    private String chatType;
    private List<ChatBubble> chatBubbles;
    private ArrayAdapter<ChatBubble> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();

        chatId = intent.getStringExtra("chatId");
        chatType = intent.getStringExtra("chatType");

        chatBubbles = new ArrayList<>();



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference channelReference = databaseReference.child(chatType).child(chatId);

       channelReference.orderByChild("timestamp").addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               ChatBubble chatBubble = dataSnapshot.getValue(ChatBubble.class);
               chatBubbles.add(chatBubble);
               adapter.notifyDataSetChanged();
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


        /*databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {



            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.toString());
                    System.out.println(ds.child("content").getValue() + " " + (String) ds.child("senderId").getValue());

                    Message message = ds.getValue(Message.class);


                   // chatBubbles.add(new ChatBubble((String) ds.child("content").getValue(), (String) ds.child("senderId").getValue()));

                    

                    adapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String uid = user.getUid();
            userId = uid; // brak danych = crash
        }

        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);

        //set ListView adapter first

        adapter = new ChatAdapter(this, R.layout.chat_layout_left, chatBubbles, userId);
        listView.setAdapter(adapter);

        //event for button SEND
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatActivity.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    //add message to list

                    //chatBubbles.add(new ChatBubble(editText.getText().toString(),userId));
                    //adapter.notifyDataSetChanged();
                    // [??] Wysylanie wiadomosci do bazy
                    writeNewMessage(userId, editText.getText().toString(), System.currentTimeMillis(),
                           FirebaseDatabase.getInstance().getReference());


                    editText.setText("");
                }
            }
        });
    }

    private void writeNewMessage(String senderId, String content, Long timestamp, DatabaseReference mDatabase) {

        Message message = new Message(content, senderId, timestamp);


        mDatabase.child(chatType).child(chatId).push().setValue(message);
    }
}

