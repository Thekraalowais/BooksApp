package com.example.thekra.listingbookapp;


public class ListingBooks {
    private String lAuthor;
    private String lTitle;

    public ListingBooks(String author, String title) {
        lAuthor = author;
        lTitle = title;

    }
    public String getAuthor(){
        return lAuthor;
    }
    public String getTitle(){
        return lTitle;
    }



}
