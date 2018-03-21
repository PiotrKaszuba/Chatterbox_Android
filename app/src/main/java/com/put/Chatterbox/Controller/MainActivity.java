package com.put.Chatterbox.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.put.Chatterbox.R;

public class MainActivity extends AppCompatActivity {

    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUpButton = (Button) findViewById(R.id.signUpButton);
    }

    protected void signUp(View view)
    {
        Intent myIntent = new Intent(this, SignUp.class);
        startActivity(myIntent);
    }


}
