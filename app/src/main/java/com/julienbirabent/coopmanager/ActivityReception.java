package com.julienbirabent.coopmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import model.Book;
import model.Manager;
import utils.HttpUtils;

/**
 * Created by Julien on 2016-11-13.
 */

public class ActivityReception extends AppCompatActivity {

    private  Button searchButton;
    private  ListView bookList;
    private  EditText searchBar;

    // Dernière liste de livre récupérée depuis le serveur
    private ArrayList<Book> lastBooksFetched  = new ArrayList<Book>();
    private ArrayAdapter<String> listViewAdapter ;
    private ArrayList<String> descriptionToDisplay = new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        initActivity();

    }

    private void setListeners(){


    }


    private void findViewsById(){
        searchButton = (Button) findViewById(R.id.button_search_reception);
        bookList = (ListView) findViewById(R.id.listView_reception);
        searchBar = (EditText) findViewById(R.id.editText_search_reception);
    }

    private void initActivity(){

        findViewsById();
        setListeners();

    }

    /**
     * Remplis la liste des livres à partir d'une ArrayList<String> contenant les descrptions des
     * livres
     * @param books
     */
    public void fillBookListView(ArrayList<Book> books){

        // On reset notre liste contenant les description d'item à afficher.
        descriptionToDisplay.clear();

        for(int i = 0; i<books.size();i++){

            descriptionToDisplay.add(books.get(i).toString());

        }
        this.listViewAdapter = new ArrayAdapter<String>(this,R.layout.book_item,descriptionToDisplay);
        this.getBookList().setAdapter(this.listViewAdapter);

    }

    /**
     *  Check si la connection internet est présente et active.
     * @return
     */
    private boolean checkInternetConnection() {

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

    /**
     * Dialogue permettant de demander au manager si oui ou non il veut retirer une copie de la liste
     * des copies en attente de ceuillette.
     */
    protected class ReceptionDialog extends Dialog {



        public ReceptionDialog(Context context, final Book bookSelected) {

            super(context);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        // Si oui, on envoie une requête au serveur avec l'id de la copie à supprimer
                        // pour que celui-ci supprime la copie en question de la base de donnée.
                        case DialogInterface.BUTTON_POSITIVE:
                            if(checkInternetConnection()) {

                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to confirm the reception of this copy ?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    /**
     * Tâches asynchrone ayant pour mission de récupérer la liste des copies en attente de
     * réception à la coopérative.
     */
    protected class GetReceptionListTask extends AsyncTask<String,String,ArrayList<Book>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Book> doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            super.onPostExecute(books);
        }
    }

    /**
     * Tâche ayant pour but de changer l'attribut availability d'une copie en attente de réception.
     */
    protected class ChangeCopyAvailityTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public ListView getBookList() {
        return bookList;
    }

    public void setBookList(ListView bookList) {
        this.bookList = bookList;
    }

    public EditText getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(EditText searchBar) {
        this.searchBar = searchBar;
    }

    public ArrayList<Book> getLastBooksFetched() {
        return lastBooksFetched;
    }

    public void setLastBooksFetched(ArrayList<Book> lastBooksFetched) {
        this.lastBooksFetched = lastBooksFetched;
    }

    public ArrayList<String> getDescriptionToDisplay() {
        return descriptionToDisplay;
    }

    public void setDescriptionToDisplay(ArrayList<String> descriptionToDisplay) {
        this.descriptionToDisplay = descriptionToDisplay;
    }

    public ArrayAdapter<String> getListViewAdapter() {
        return listViewAdapter;
    }

    public void setListViewAdapter(ArrayAdapter<String> listViewAdapter) {
        this.listViewAdapter = listViewAdapter;
    }
}
