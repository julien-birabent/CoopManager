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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import data.BookHttpClient;
import data.JSONBookParser;
import data.JSONCopyParser;
import model.Book;
import model.Copy;
import model.Manager;
import utils.BookSorter;
import utils.HttpUtils;
import utils.UrlBuilder;

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
        // initiliasation de l'activité
        initActivity();
    }



    private void setListeners(){

        getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternetConnection()){
                    // Récupérer la liste des copies en attente de réception.

                    GetReceptionListTask getReceptionListTask = new GetReceptionListTask();
                    getReceptionListTask.execute(UrlBuilder.getReceptionListUrl());

                }
            }
        });

        getBookList().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Notre liste contient exclusivement des TextView,
                // En fesant ca on récupère donc le TextView selectionné.
                TextView tv = (TextView) view;
                // On récupère l'objet Book qui a été sélectionné dans la liste afin de disposer
                // de toutes ses informations.
                Book bookSelected = getLastBooksFetched().get(position);
                // On ouvre le dialogue pour savoir si le manager veut retirer cette copie de la
                // liste des copies de la coop
                ReceptionDialog receptionDialog = new ReceptionDialog(view.getContext(), bookSelected);
            }
        });

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
                        // Si oui, on envoie une requête au serveur avec l'id de la copie à modifier
                        // pour que celui-ci modifie l'état en cours de la copie à available
                        case DialogInterface.BUTTON_POSITIVE:
                            if(checkInternetConnection()) {
                                // On récupère l'id de la copie a supprimer
                                String copieId = bookSelected.getCopy().getCopyId();
                                // On envoie au serveur la requete de suppression de copie
                                ChangeCopyAvailityTask changeCopyAvailityTask = new ChangeCopyAvailityTask();
                                changeCopyAvailityTask.execute(UrlBuilder.receiveCopyUrl(copieId));
                                // On supprime le livre de la liste pour la cohérence de l'interface
                                getLastBooksFetched().remove(bookSelected);
                                fillBookListView(getLastBooksFetched());
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


            ArrayList<Copy> copiesArrayList = new ArrayList<Copy>();
            ArrayList<Book> bookArrayList = new ArrayList<Book>();
            JSONBookParser jsonBookParser = new JSONBookParser();
            // On récupère toutes les copies en attente de reception
            BookHttpClient bookHttpClient = new BookHttpClient();

            // http://URL_SERVER:3000/copies.json?availability=waiting_for_reception
            String allCopies = bookHttpClient.sendGet(params[0]);

            // On convertit les Copy format JSON en objet Copy
            JSONCopyParser jsonCopyParser = new JSONCopyParser();
            copiesArrayList = jsonCopyParser.parseManyCopies(allCopies);
            // Pour chaque copie, on fait une requête dans la BD pour trouver le Book associé
            // On convertit la description JSON du Book en objet Book et on associe la Copy à ce Book
            for(int i= 0 ;i<copiesArrayList.size();i++){
                // http://url_serveur/books/*id_copy*.json
                String stringBook = bookHttpClient.sendGet(HttpUtils.SERVER_URL + HttpUtils.BOOKS
                        + copiesArrayList.get(i).getBookId() + HttpUtils.JSON);

                Book book = jsonBookParser.parseBook(stringBook);
                book.setCopy(copiesArrayList.get(i));
                bookArrayList.add(book);
            }
            return bookArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            super.onPostExecute(books);

            if(books!= null){
                // On trie la liste de copies selon les paramètres de recherche du manager.
                books = BookSorter.sortByExpression(getSearchBar().getText().toString(),books);
                // On sauvegarde la dernière liste fetched du serveur
                setLastBooksFetched(books);
                // On remplis la listeView contenant les descriptions des bouquins
                fillBookListView(books);
            }
        }
    }

    /**
     * Tâche ayant pour but de changer la disponibilité d'une copie après la confirmation de sa
     * réception par le manager de coop.
     */
    protected class ChangeCopyAvailityTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            BookHttpClient bookHttpClient = new BookHttpClient();
            String response = bookHttpClient.sendGet(params[0]);

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
