package com.example.thekra.listingbookapp;

import android.app.LoaderManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<ListingBooks>> {

    private final static String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private ListingBooksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        adapter = new ListingBooksAdapter(this, new ArrayList<ListingBooks>());
        listView.setAdapter(adapter);

    }


    @Override
    public Loader<List<ListingBooks>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, GOOGLE_BOOKS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<ListingBooks>> loader, List<ListingBooks> books) {
        adapter.clear();
        if (books != null || !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ListingBooks>> loader) {
        adapter.clear();
    }


}

