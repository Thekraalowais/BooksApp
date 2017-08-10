package com.example.thekra.listingbookapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<ListingBooks>> {

    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_LOADER_ID = 1;
    private ListingBooksAdapter adapter;
    private EditText editText;
    private String result = "";
    private Button button;
    private TextView emptyText;
    View loadingIndicator;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyText = (TextView) findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading);
        editText = (EditText) findViewById(R.id.search_bar);
        button = (Button) findViewById(R.id.button);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(emptyText);
        adapter = new ListingBooksAdapter(this, new ArrayList<ListingBooks>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListingBooks currentBook = adapter.getItem(position);
                Uri bookUrl = Uri.parse(currentBook.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, bookUrl);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                result = editText.getText().toString().trim();
                if (result.matches("")) {
                    Toast.makeText(MainActivity.this, "Please enter book title and author", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (networkInfo != null && networkInfo.isConnected()) {

                    loadingIndicator.setVisibility(View.VISIBLE);
                    getSupportLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);

                } else {


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

        loadingIndicator.setVisibility(View.GONE);
        emptyText.setText(R.string.no);
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

