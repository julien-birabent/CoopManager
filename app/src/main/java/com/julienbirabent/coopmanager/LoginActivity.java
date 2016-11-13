package com.julienbirabent.coopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class LoginActivity extends AppCompatActivity {

    private Button signInButton;
    private EditText userName;
    private EditText password;
    private EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViewsById();
        setOnClickListener();


    }
    private void setOnClickListener(){
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getViewsById(){
        signInButton = (Button)findViewById(R.id.signinButton);
        userName = (EditText) findViewById(R.id.usernameId);
        password = (EditText) findViewById(R.id.passwordId);
        phoneNumber = (EditText) findViewById((R.id.phoneId));
    }
}
