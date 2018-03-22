package com.put.Chatterbox.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    User user;
    ListView userListView;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        UserListController.readFromDatabase(this);
    }


    public void updateUserList(ArrayList<String> userList) {
        userListView = (ListView) findViewById(R.id.userListView);
        adapter = new ArrayAdapter<String>(this,R.layout.list_view_row,userList);

        userListView.setAdapter(adapter);
    }

}
