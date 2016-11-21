package com.julienbirabent.coopmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import utils.HttpUtils;


public class LoginActivity extends AppCompatActivity {



    private Button signInButton;
    private EditText userName;
    private EditText token;

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
        token = (EditText) findViewById(R.id.passwordId);
    }
    /**
     * Méthode ou l'on créé l'url permettant d'envoyer la requpete d'authenfication du client.
     * @return
     */
    private String buildAthentificationUrl(){

        // On récupère les données fournis par l'utilisateur
        String userName = getUserName().getText().toString();
        String token = getToken().getText().toString();

        // On créé la chaîne de caractère qui va contenir les paramètres de la requête serveur
        String url = HttpUtils.SERVER_URL + "?"+ HttpUtils.COOPMANAGER_EMAIL + userName +
                HttpUtils.AND + HttpUtils.COOPMANAGER_TOKEN + token;
        return url;
    }

    /**
     * Méthode privée pour byPass le screen d'authentification.
     */
    private void testGoToStudentActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public EditText getUserName() {
        return userName;
    }

    public void setUserName(EditText userName) {
        this.userName = userName;
    }

    public EditText getToken() {
        return token;
    }

    public void setToken(EditText token) {
        this.token = token;
    }
}
