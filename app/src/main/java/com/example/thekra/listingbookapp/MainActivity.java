package com.example.thekra.listingbookapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<ListingBooks>> {

    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";
    private ListingBooksAdapter adapter;
    private static final int BOOK_LOADER_ID = 1;
    private EditText editText;
    private String result = "";
    private Button button;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyText = (TextView) findViewById(R.id.empty_view);
        editText = (EditText) findViewById(R.id.search_bar);
        button = (Button) findViewById(R.id.button);
        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new ListingBooksAdapter(this, new ArrayList<ListingBooks>());
        listView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                View loadingIndicator = findViewById(R.id.loading);
                loadingIndicator.setVisibility(View.VISIBLE);
                result = editText.getText().toString().trim();
                if (networkInfo != null && networkInfo.isConnected()) {
                    getSupportLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {

                    loadingIndicator = findViewById(R.id.loading);
                    loadingIndicator.setVisibility(View.GONE);

                    emptyText.setText(R.string.no_internet_connection);
                }
            }


        });
    }

    @Override
    public Loader<List<ListingBooks>> onCreateLoader(int id, Bundle args) {
        String search = GOOGLE_BOOKS_URL + result;
        return new BooksLoader(this, search);
    }

    @Override
    public void onLoadFinished(Loader<List<ListingBooks>> loader, List<ListingBooks> books) {
        View loadingIndicator = findViewById(R.id.loading);
        loadingIndicator.setVisibility(View.GONE);
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

