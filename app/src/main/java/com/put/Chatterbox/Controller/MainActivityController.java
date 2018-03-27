package com.put.Chatterbox.Controller;



import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.put.Chatterbox.Model.User;

import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 2018-03-21.
 */

public class MainActivityController {



    public static void signIn(String email, String password, final MainActivity instance){

      // User user = new User();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                
                String userKey = dataSnapshot.getKey();

                DatabaseReference usersRef =  FirebaseDatabase.getInstance().getReference().child("Users").child(userKey);
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String username = dataSnapshot.child("username").getValue(String.class);
                        Long timestamp = dataSnapshot.child("timestamp").getValue(Long.class);

                        User user = new User(username,email,timestamp);
                        Intent myIntent = new Intent(instance, UserList.class);
                        myIntent.putExtra("user", user);
                        instance.startActivity(myIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                usersRef.addListenerForSingleValueEvent(eventListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        };


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(instance, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(user.getUid());
                            ref.addListenerForSingleValueEvent(postListener);
                            Toast.makeText(instance, "Log in successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(instance, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}
