package com.put.Chatterbox.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.put.Chatterbox.Controller.SignUp;
import com.put.Chatterbox.R;

import com.put.Chatterbox.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button signUpButton;
    Button logInButton;
    EditText emailEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
    }

    protected void signUp(View view)
    {
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }

    protected void logIn(View view){


    }

}
