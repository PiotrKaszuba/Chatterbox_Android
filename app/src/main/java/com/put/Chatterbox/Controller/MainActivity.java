package com.put.Chatterbox.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button signUpButton;
    Button signInButton;
    EditText emailEdit;
    EditText passwordEdit;
    CheckBox remeberMeCheckBox;


    public static SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        signInButton = (Button) findViewById(R.id.logInButton);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        remeberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        ChatController.messageToWebserv("Hey, how are u 2day?");

        System.out.println("REMEBER ME: " + remeberMeCheckBox.isChecked());

        sessionManager = new SessionManager(getApplicationContext());
        //sessionManager.logoutUser();
        sessionManager.checkLogin();

        }

    protected void signUp(View view)
    {
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }

    protected void signIn(View view)
    {

        MainActivityController.signIn(emailEdit.getText().toString(), passwordEdit.getText().toString(), this,remeberMeCheckBox.isChecked());

        //

    }
    protected void forgotPassword(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("We will send you link to reset password");
        alert.setMessage("Your email:");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            String s = input.getText().toString();
            int a =1;
                FirebaseAuth.getInstance().sendPasswordResetEmail(s)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Email sent",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Wrong email",
                                            Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

}
