package com.example.thekra.listingbookapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class HelperMethods {
    private HelperMethods() {

    }

    public static List<ListingBooks> bookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPResquest(url);
        } catch (IOException e) {
            Log.e("bookData", "Error to make http request", e);
        }
        List<ListingBooks> books = extractBookFromJson(jsonResponse);
        return books;
    }

    private static URL createUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException m) {
            Log.e("Helper Methods", "Problem build URL", m);
        }
        return url;
    }

    private static String makeHTTPResquest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpCpnnection = null;
        InputStream inputStream = null;
        try {
            httpCpnnection = (HttpURLConnection) url.openConnection();
            httpCpnnection.setReadTimeout(10000);
            httpCpnnection.setConnectTimeout(15000);
            httpCpnnection.setRequestMethod("GET");
            httpCpnnection.connect();
            if (httpCpnnection.getResponseCode() == 200) {
                inputStream = httpCpnnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e("makeHttpConnection", "Error response code" + httpCpnnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e("makehttpconnection", "Error retrieve book json result ", e);
        } finally {
            if (httpCpnnection != null) {
                httpCpnnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static List<ListingBooks> extractBookFromJson(String bookJSON) {
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        List<ListingBooks> books = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(bookJSON);
            JSONArray booksArray = jsonResponse.getJSONArray("items");
            for (int i = 0; i < booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject info = currentBook.getJSONObject("volumeInfo");
                String title = info.getString("title");
                String author = info.getString("authors");
                ListingBooks book = new ListingBooks(title, author);
                books.add(book);
            }

        } catch (JSONException e) {
            Log.e("extractBookFromJson", "problem in parsing json result", e);
        }
        return books;
    }


}
