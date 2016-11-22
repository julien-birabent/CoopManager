package com.julienbirabent.coopmanager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import model.Book;
import model.Copy;


/**
 * Created by Julien on 2016-11-13.
 */

public class PickingActivity extends AppCompatActivity {

    private Button searchButton;
    private ListView bookList;
    private EditText searchBar;

    private ArrayAdapter<String> copiesToPickAdapter ;
    private ArrayList<String> copiesToPick = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);

        searchButton = (Button) findViewById(R.id.button_search_picking);
        bookList = (ListView) findViewById(R.id.listView_picking);
        searchBar = (EditText) findViewById(R.id.editText_search_picking);

        ArrayList<Book> booksTest = new ArrayList<Book>();
        booksTest.add(new Book("isbn", "author", "title","price","nb page", new Copy("Neuf","Attente reception")));

       fillBookListView(booksTest);


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

    private class GetPickingListTask extends AsyncTask<String,String,ArrayList<Book>>{

        @Override
        protected ArrayList<Book> doInBackground(String... params) {

            ArrayList<Book> bookArrayList = new ArrayList<Book>();

            // Ici faire le code pour récupérer la liste correspondant aux paramètres de la
            // demande serveur.


            return bookArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            super.onPostExecute(books);

            // après avoir récupéré la liste de livre corespondant à la recherche via le serveur, on définit
            // cette liste comme la liste a afficher.
            if(books!=null) {
                fillBookListView(books);
            }


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
}
