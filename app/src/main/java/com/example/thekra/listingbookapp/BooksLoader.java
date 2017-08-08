package com.example.thekra.listingbookapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

//import android.content.AsyncTaskLoader;


public class BooksLoader extends AsyncTaskLoader<List<ListingBooks>> {
    private String BUrl;

    public BooksLoader(Context context, String url) {
        super(context);
        BUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ListingBooks> loadInBackground() {
        if (BUrl == null) {
            return null;
        }
        List<ListingBooks> books = HelperMethods.bookData(BUrl);
        return books;
    }
}
