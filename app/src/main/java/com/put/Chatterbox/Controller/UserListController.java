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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static void readFromDatabasePrivateChats(final UserList instance)
    {

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid() ;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference chatsRef = databaseReference.child("PrivateChats");
        chatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> privateChatsList = new ArrayList<String>();
                Map<String,String> privateChatsMap = new HashMap<String,String>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println( ds.toString());

                    String uiddb1 = (String) ds.child("idUser1").getValue();
                    String uiddb2 = (String) ds.child("idUser2").getValue();

                    int a=0;

                    System.out.println("User1: " + uiddb1 + "     User2: " + uiddb2);

                    if(userId.equals(uiddb1))
                    {
                        privateChatsList.add(uiddb2);
                        privateChatsMap.put(uiddb2,ds.getKey());
                    }
                    if(userId.equals(uiddb2))
                    {
                        privateChatsList.add(uiddb1);
                        privateChatsMap.put(uiddb1,ds.getKey());
                    }
                }
                    int b=0;
                instance.openPrivateChat(privateChatsList,privateChatsMap,userId,instance.uidUser2);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }

}