package com.julienbirabent.coopmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import data.JSONBookParser;

/**
 * Created by Julien on 2016-11-13.
 */

public class MainActivity  extends AppCompatActivity {

    Button receptionButton;
    Button pickingButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receptionButton = (Button) findViewById(R.id.reception_button);
        pickingButton = (Button) findViewById(R.id.picking_button);

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
