package com.julienbirabent.coopmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Julien on 2016-11-13.
 */

public class PickingActivity extends AppCompatActivity {

    private Button searchButton;
    private ListView bookList;
    private EditText searchBar;

    private ArrayAdapter<String> bookListAdapter ;
    private ArrayList<String> booksString = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking);

        searchButton = (Button) findViewById(R.id.button_search_picking);
        bookList = (ListView) findViewById(R.id.listView_picking);
        searchBar = (EditText) findViewById(R.id.editText_search_picking);

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

    public ArrayAdapter<String> getBookListAdapter() {
        return bookListAdapter;
    }

    public void setBookListAdapter(ArrayAdapter<String> bookListAdapter) {
        this.bookListAdapter = bookListAdapter;
    }

    public ArrayList<String> getBooksString() {
        return booksString;
    }

    public void setBooksString(ArrayList<String> booksString) {
        this.booksString = booksString;
    }

    /**
     * Remplis la liste des livres à partir d'une ArrayList<String> contenant les descrptions des
     * livres
     * @param booksString
     */
    public void fillBookListView(ArrayList<String> booksString){

        this.bookListAdapter = new ArrayAdapter<String>(this,R.layout.book_item,booksString);
        this.getBookList().setAdapter(this.bookListAdapter);

    }


}
