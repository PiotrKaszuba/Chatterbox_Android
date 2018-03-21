package com.put.Chatterbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.put.Chatterbox.Controller.SignUpController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText usernameEdit,emailEdit,passwordEdit,confirmPasswordEdit;
    ImageButton goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        confirmPasswordEdit = (EditText) findViewById(R.id.confirmPasswordEdit);

        goBackButton = (ImageButton) findViewById(R.id.goBackButton);
    }

    protected  void goBack(View view){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }

    protected void signUp(View view) {

        if(isValidUsername(usernameEdit.getText().toString())){
            if(isValidEmail(emailEdit.getText().toString())) {
                if(checkPasswords(passwordEdit.getText().toString(), confirmPasswordEdit.getText().toString())) {
                    Long timestamp = System.currentTimeMillis();
                    SignUpController.signUp(usernameEdit.getText().toString(),passwordEdit.getText().toString(),emailEdit.getText().toString(),timestamp,this);
                } else {
                    Toast.makeText(this, "The passwords are diffrent", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Incorrect email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Incorrect username", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPasswords(String pass, String confirmPass)
    {
        if(pass.length()<6) return false;
        if(pass.equals(confirmPass)) return true;
        else return false;
    }

    private boolean isValidEmail(String email){
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    private boolean isValidUsername(String username) {
        String pattern = "^[a-zA-Z0-9]{4,12}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(username);
        return m.matches();

    }


}
