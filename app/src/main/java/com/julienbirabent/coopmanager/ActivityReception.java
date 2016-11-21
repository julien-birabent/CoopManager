package com.julienbirabent.coopmanager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import model.Manager;

/**
 * Created by Julien on 2016-11-13.
 */

public class ActivityReception extends AppCompatActivity {

    private  Button searchButton;
    private  ListView bookList;
    private  EditText searchBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        initActivity();

    }

    private void findViewsById(){
        searchButton = (Button) findViewById(R.id.button_search_reception);
        bookList = (ListView) findViewById(R.id.listView_reception);
        searchBar = (EditText) findViewById(R.id.editText_search_reception);
    }

    private void initActivity(){

        findViewsById();

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

}
