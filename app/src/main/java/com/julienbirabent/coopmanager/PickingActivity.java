package com.julienbirabent.coopmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import data.BookHttpClient;
import data.JSONBookParser;
import data.JSONCopyParser;
import model.Book;
import model.Copy;
import utils.HttpUtils;


/**
 * Created by Julien on 2016-11-13.
 */

public class PickingActivity extends AppCompatActivity {

    private Button searchButton;
    private ListView bookList;
    private EditText searchBar;

    // Dernière liste de livre récupérée depuis le serveur
    private ArrayList<Book> lastBooksFetched  = new ArrayList<Book>();
    private ArrayAdapter<String> copiesToPickAdapter ;
    private ArrayList<String> copiesToPick = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);

        findViewsById();
        setListeners();

        lastBooksFetched.add(new Book("isbn", "author", "title","price","nb page", new Copy("Neuf","Attente reception")));
        lastBooksFetched.add(new Book("isbn2", "author2", "title2","price2","nb page2", new Copy("Neuf","Attente reception")));
        fillBookListView(lastBooksFetched);



    }

    private void findViewsById(){
        searchButton = (Button) findViewById(R.id.button_search_picking);
        bookList = (ListView) findViewById(R.id.listView_picking);
        searchBar = (EditText) findViewById(R.id.editText_search_picking);

    }

    private void setListeners(){

        // Quand on appuie sur le bouton, l'application se charge de récupérer la liste des copies
        // qui sont réservées.
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInternetConnection()){
                    // http://serveur_url/copies.json?availability=reserved
                    String url = HttpUtils.SERVER_URL + HttpUtils.COPIES + HttpUtils.JSON +"?"+
                            HttpUtils.AVAILABIlITY_PARAM + HttpUtils.RESERVED;
                    // Envoie de la requête au serveur
                    GetPickingListTask getPickingListTask = new GetPickingListTask();
                    getPickingListTask.execute(url);
                }
            }
        });

        // Chaque item de la liste est mappé à un objet Book. Sur l'appuie d'un item, on ouvre
        // un dialogue qui donne la possibilité au manager de supprimer la copie sélectionnée
        // de la base de donnée de la coop.
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                PickingDialog pickingDialog = new PickingDialog(view.getContext(), bookSelected);


            }

        });
    }

    /**
     * Remplis la liste des livres à partir d'une ArrayList<String> contenant les descrptions des
     * livres
     * @param books
     */
    public void fillBookListView(ArrayList<Book> books){

        // On reset notre liste contenant les description d'item à afficher.
        copiesToPick.clear();

        for(int i = 0; i<books.size();i++){

            copiesToPick.add(books.get(i).toString());

        }
        this.copiesToPickAdapter = new ArrayAdapter<String>(this,R.layout.book_item,copiesToPick);
        this.getBookList().setAdapter(this.copiesToPickAdapter);

    }

    /**
     * Dialogue permettant de demander au manager si oui ou non il veut retirer une copie de la liste
     * des copies en attente de ceuillette.
     */
    protected class PickingDialog extends Dialog{



        public PickingDialog(Context context, final Book bookSelected) {

            super(context);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        // Si oui, on envoie une requête au serveur avec l'id de la copie à supprimer
                        // pour que celui-ci supprime la copie en question de la base de donnée.
                        case DialogInterface.BUTTON_POSITIVE:
                            if(checkInternetConnection()) {
                                // On récupère l'id de la copie a supprimer
                                String copieId = bookSelected.getCopy().getCopyId();
                                // On construit l'url de la requête
                                String url = HttpUtils.SERVER_URL + HttpUtils.COPIES
                                        + HttpUtils.DELETE + copieId;
                                // On envoie au serveur la requete de suppression de copie
                                RemoveCopyTask removeCopyTask = new RemoveCopyTask();
                                removeCopyTask.execute(url);
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
            builder.setMessage("Do you want this copy to be remove from the picking list ?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    protected class GetPickingListTask extends AsyncTask<String,String,ArrayList<Book>>{

        @Override
        protected ArrayList<Book> doInBackground(String... params) {



            ArrayList<Copy> copiesArrayList = new ArrayList<Copy>();
            ArrayList<Book> bookArrayList = new ArrayList<Book>();
            JSONBookParser jsonBookParser = new JSONBookParser();
            // On récupère toutes les copies en attente de reception
            BookHttpClient bookHttpClient = new BookHttpClient();

            // http://URL_SERVER:3000/copies.json?availability=reserved
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

            // après avoir récupéré la liste de livre corespondant à la recherche via le serveur, on définit
            // cette liste comme la liste a afficher.
            if(books!=null) {
                // On sauvegarde la dernière liste fetched du serveur
                setLastBooksFetched(books);
                // On remplis la listeView contenant les descriptions des bouquins
                fillBookListView(books);
            }


        }
    }

    /**
     * Tâche asynchrone en charge de supprimer une copie de la base de donnée liée au serveur.
     */
    protected class RemoveCopyTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            //Param [0] : url à envoyer pour supprimer la copie.

            //http://url_serveur/copies/delete/*id_copy*
            BookHttpClient bookHttpClient = new BookHttpClient();
            String response = bookHttpClient.sendGet(params[0]);


            return response;
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

    public ArrayAdapter<String> getCopiesToPickAdapter() {
        return copiesToPickAdapter;
    }

    public void setCopiesToPickAdapter(ArrayAdapter<String> copiesToPickAdapter) {
        this.copiesToPickAdapter = copiesToPickAdapter;
    }

    public ArrayList<String> getCopiesToPick() {
        return copiesToPick;
    }

    public void setCopiesToPick(ArrayList<String> copiesToPick) {
        this.copiesToPick = copiesToPick;
    }

    public ArrayList<Book> getLastBooksFetched() {
        return lastBooksFetched;
    }

    public void setLastBooksFetched(ArrayList<Book> lastBooksFetched) {
        this.lastBooksFetched = lastBooksFetched;
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
}
