package com.put.Chatterbox.Controller;

import android.app.Service;
import android.database.Observable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static com.put.Chatterbox.Controller.MainActivity.sessionManager;


public class UserListController {




    public UserListController() {
    }



    public static void readFromDatabase(final UserList instance) {

        final String activeUser = instance.user.username;

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("Users");
        usersdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> usernamesArrayList = new ArrayList<String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.toString());

                    String username = (String) ds.child("username").getValue();
                    System.out.println("username: " + username);
                   if(!activeUser.equals(username)) usernamesArrayList.add(username);
                    System.out.println("arr size in for: " + usernamesArrayList.size());
                }

                instance.updateUserList(usernamesArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

}