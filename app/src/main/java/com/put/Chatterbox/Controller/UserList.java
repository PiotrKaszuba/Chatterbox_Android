package com.put.Chatterbox.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Controller.Lobby.ChannelLobby;
import com.put.Chatterbox.Model.Message;
import com.put.Chatterbox.Model.PrivateChannel;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import static com.put.Chatterbox.Controller.MainActivity.sessionManager;

public class UserList extends AppCompatActivity {

    TextView logoutView;
    User user;
    ListView userListView;
    ArrayAdapter<String> adapter;
    ArrayAdapter<TextView> textViewArrayAdapter;
    TextView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        logoutButton = (TextView) findViewById(R.id.logoutButton);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

        String username="";
        String e="";

        if(user==null)
        {
            HashMap<String,String> u = sessionManager.getUserDetails();
            username = u.get("name");
            e = u.get("email");
            String time = u.get("timestamp");
            user = new User();
            user.setUsername(username);
            user.setEmail(e);
            user.setTimestamp(Long.valueOf(time));
        }



        int a=0;

        BottomNavigationView bnv = this.findViewById(R.id.bottom_navigation);
        final Context instance = this;
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent myIntent = null;
                switch(item.getItemId()){
                    case R.id.menuChannels:
                        myIntent = new Intent(instance, ChannelLobby.class);
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
        UserListController.readFromDatabase(this);


    }


    public void updateUserList(ArrayList<String> userList) {
        userListView = (ListView) findViewById(R.id.userListView);
        logoutView = (TextView) findViewById(R.id.logoutButton);
        adapter = new ArrayAdapter<String>(this,R.layout.list_view_row,userList);


        userListView.setAdapter(adapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);


                System.out.println(parent.getItemAtPosition(position));
                final String user2 =(String) parent.getItemAtPosition(position);

                //get current user key
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

                final String uidUser1=user.getUid();

                //get picked user key
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference usersdRef = rootRef.child("Users");
                usersdRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String uidUser2 = "";
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            System.out.println(ds.toString());

                            String username = (String) ds.child("username").getValue();
                            System.out.println("username: " + username);



                            if(user2.equals(username)) uidUser2 = ds.getKey();
                        }

                        checkChatsForUser(uidUser1,uidUser2,FirebaseDatabase.getInstance().getReference());
                        createNewPrivateChat(uidUser1,uidUser2,FirebaseDatabase.getInstance().getReference());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());
                    }
                });



            }
        });

    }

    private void createNewPrivateChat(String user1, String user2, DatabaseReference mDatabase) {

        //checkChatsForUser(user1,user2,mDatabase);
        PrivateChannel privateChannel = new PrivateChannel(user1, user2, System.currentTimeMillis());

        //mDatabase.child("PrivateChats").push().setValue(privateChannel);

        String key = mDatabase.child("PrivateChats").push().getKey();
        mDatabase.child("PrivateChats").child(key).setValue(privateChannel);


        int a=0;

        Intent i = new Intent(this, ChatActivity.class);
         i.putExtra("chatId", key);
         i.putExtra("chatType","ChannelMessages");
        this.startActivity(i);

    }



                        protected void SignOut (View view){
                            FirebaseAuth.getInstance().signOut();
                            sessionManager.logoutUser();
                            //Intent myIntent = new Intent(this, MainActivity.class);
                            //startActivity(myIntent);
                        }

    private void checkChatsForUser(final String uid,final String uid2, DatabaseReference mDatabase)
    {
        int i=0;
        final Context actvitiyContext = this.getApplicationContext();
        DatabaseReference chatsRef = mDatabase.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();
                    if(uid.equals(uiddb1) || uid.equals(uiddb2) && uid2.equals(uiddb1) || uid2.equals(uiddb2)) {

                        String key = ds.getKey();
                        int a=0;
                        Intent i = new Intent(actvitiyContext, ChatActivity.class);
                        i.putExtra("chatId", key);
                        i.putExtra("chatType","ChannelMessages");
                        actvitiyContext.startActivity(i);

                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }


        });


    }


                    }


