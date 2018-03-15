package com.put.Chatterbox.Controller;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.put.Chatterbox.Model.User;
import com.put.Chatterbox.SignUp;

import static android.content.ContentValues.TAG;

/**
 * Created by Piotr on 2018-03-15.
 */

public class SignUpController {

    private static void writeNewUser(String userId, String userName, String email, Long timestamp, DatabaseReference mDatabase) {
        User user = new User(userName, email, timestamp);

        mDatabase.child("users").child(userId).setValue(user);
    }

    public static void signUp(final String userName,final String password,final String email,final Long timestamp, final SignUp instance){
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(instance, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            writeNewUser(user.getUid(), userName, email, timestamp, mDatabase);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            Toast.makeText(instance, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }

}
