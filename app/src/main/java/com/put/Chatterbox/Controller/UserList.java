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
import com.put.Chatterbox.Model.PrivateChannel;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.put.Chatterbox.Controller.MainActivity.sessionManager;

public class UserList extends AppCompatActivity {

    TextView logoutView;
    User user;
    ListView userListView;
    ArrayAdapter<String> adapter;
    ArrayAdapter<TextView> textViewArrayAdapter;
    TextView logoutButton;
    public String usernameUser2;
    String uidUser2;
    String uidUser1;


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
            String time;
            time = u.get("timestamp");
            if(time==null) time="1";
            int i=0;
            user = new User();
            user.setUsername(username);
            user.setEmail(e);
            user.setTimestamp(Long.valueOf(time));
        }


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
                        myIntent = new Intent(instance, PrivateChatActivity.class);
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

        final UserList ul = this;

        userListView.setAdapter(adapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);


                System.out.println(parent.getItemAtPosition(position));
                usernameUser2 =(String) parent.getItemAtPosition(position);

                //get current user key
                FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                uidUser1=user.getUid();


                //get user's key we want chat
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
                ref.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                    //Get map of users in datasnapshot
                                    User user = ds.getValue(User.class);
                                    String nick = (String) ds.child("username").getValue();
                                    int a = 0;
                                    if (nick.equals(usernameUser2)) {
                                        uidUser2 = ds.getKey();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });


                UserListController.readFromDatabasePrivateChats(ul);

            }
        });

    }

    public void openPrivateChat(ArrayList<String> privateChats,Map<String,String> privateChatsmap,String uid1,String uid2)
    {
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid() ;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        boolean chatInDb = false;
        String keyChatInDb ="";

        for (Map.Entry<String, String> entry : privateChatsmap.entrySet()) {
            if((userId.equals(uid1) || userId.equals(uid2)) && (entry.getKey().equals(uid1) || entry.getKey().equals(uid2)))
            {
                int a =0;
                chatInDb = true;
                keyChatInDb = entry.getValue();
            }
        }

        if(!chatInDb) {
            PrivateChannel privateChannel = new PrivateChannel(uid1, uid2, System.currentTimeMillis());


            String key = databaseReference.child("PrivateChats").push().getKey();
            databaseReference.child("PrivateChats").child(key).setValue(privateChannel);


            int a = 0;

            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("chatId", key);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            this.startActivity(i);
        }
        else
        {
            int a=0;
            Intent i = new Intent(this, ChatActivity.class);
            i.putExtra("chatId", keyChatInDb);
            i.putExtra("chatType", "ChannelMessages");
            System.out.println("Start ChatActivity  onPostExecute");
            this.startActivity(i);
        }
    }


                        protected void SignOut (View view){
                            FirebaseAuth.getInstance().signOut();
                            sessionManager.logoutUser();
                            //Intent myIntent = new Intent(this, MainActivity.class);
                            //startActivity(myIntent);
                        }

                    }


