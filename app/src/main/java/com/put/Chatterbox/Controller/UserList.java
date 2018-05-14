package com.put.Chatterbox.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.put.Chatterbox.Controller.Lobby.ChannelLobby;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    TextView logoutView;
    User user;
    ListView userListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");

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
    }

    protected void SignOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
