package com.example.thekra.listingbookapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListingBooksAdapter extends ArrayAdapter<ListingBooks> {

    public ListingBooksAdapter(Context context, List<ListingBooks> books) {

        super(context, 0, books);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);

        }
        ListingBooks currentPosition = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        title.setText(currentPosition.getTitle());
        author.setText(currentPosition.getAuthor());

        return listItemView;
    }
}
