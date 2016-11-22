package com.julienbirabent.coopmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import data.JSONBookParser;
import model.Manager;

/**
 * Created by Julien on 2016-11-13.
 */

public class MainActivity  extends AppCompatActivity {

    Button receptionButton;
    Button pickingButton;

    private  static   Manager sessionManager = new Manager();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initActivity();
    }

    private void initActivity(){
        findViewsById();
        setOnClickListener();

        // On récupère les attributs du manager qui se connecte à l'application
        // contenues dans l'intent envoyé par LoginActivity
        Intent intent = getIntent();
        sessionManager.setEmail(intent.getStringExtra(LoginActivity.USER_NAME));
        sessionManager.setPassword(intent.getStringExtra(LoginActivity.TOKEN));
        sessionManager.setId(intent.getStringExtra(LoginActivity.COOPMANAGER_ID));

    }

    private void findViewsById(){
        receptionButton = (Button) findViewById(R.id.reception_button);
        pickingButton = (Button) findViewById(R.id.picking_button);
    }

    public static Manager getSessionManager(){

        if(sessionManager == null){
            sessionManager = new Manager();
        }
        return sessionManager;
    }


    private void setOnClickListener() {

        // On passe a l'activité permettant de recevoir les livres
        receptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityReception.class);
                startActivity(intent);
            }
        });

        // On passe a l'activité permettant de délivrer les livres à l'étudiant
        pickingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PickingActivity.class);
                startActivity(intent);
            }
        });


    }
}
