package com.julienbirabent.coopmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

        searchButton = (Button) findViewById(R.id.button_search_reception);
        bookList = (ListView) findViewById(R.id.listView_reception);
        searchBar = (EditText) findViewById(R.id.editText_search_reception);

    }
}
