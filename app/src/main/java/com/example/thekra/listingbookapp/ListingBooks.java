package com.example.thekra.listingbookapp;


public class ListingBooks {
    private String lAuthor;
    private String lTitle;
    private String lUrl;

    public ListingBooks(String author, String title, String url) {
        lAuthor = author;
        lTitle = title;
        lUrl = url;

    }

    public String getAuthor() {
        return lAuthor;
    }

    public String getTitle() {
        return lTitle;
    }

    public String getUrl() {
        return lUrl;
    }


}
