package com.example.soumya.feedthepanda;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Soumya on 15-09-2015.
 */
public class JSONResponseFetcher {

//    private static final String API_URL = "http://192.168.51.234:5000/";
    private static final String API_URL = "http://www.bing.com/";

    /*
        Get the JSON Data from API when signed in i.e. data particular to user
     */
    public static String getJSONSignedDataFromAPI(String url_, String cookieData) throws IOException {
        // TODO
        return null;
    }

    /*
        Get General Data From API available to all users
        Takes URL as input
     */
    public static String getJSONDataFromAPI(String url_) throws IOException {
        URL url = new URL(url_);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer response = new StringBuffer();
        if(inputStream == null) throw new IOException("InputStream was null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while((line = reader.readLine()) != null) {
            response.append(line + "\n");
        }

        if(response.length() == 0) return null;
        Log.e("Bleh",response.toString());
        return response.toString();
    }

    /*
        Get Category Data From API
        Returns a string containing the JSON response.
     */
    public static String getCategoryDataFromAPI() {
        final String LOG_TAG = "FETCH_CATEGORY_DATA";
        String categoryJSONResponse = null;

        try {
            final String resource = "categories";
            final String format = "json";

            Uri callableUri = Uri.parse(API_URL).buildUpon().appendPath(resource + "." + format).build();
            categoryJSONResponse = getJSONDataFromAPI(callableUri.toString());
            Log.v("Bleh", categoryJSONResponse.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException in getting categories from API" + e.toString());
        }

        if(categoryJSONResponse.length() == 0) return null;
        return categoryJSONResponse;
    }

    /*
        Get All Post Data From API
        Returns a string containing the JSON response.
     */
    public static String getAllPostDataFromAPI() {
        final String LOG_TAG = "FETCH_POST_DATA";
        String postJSONResponse = null;

        try {
            final String resource = "allPosts";
            final String format = "json";

            Uri callableUri = Uri.parse(API_URL).buildUpon().appendPath(resource + "." + format).build();
            postJSONResponse = getJSONDataFromAPI(callableUri.toString());
            Log.v("Bleh", postJSONResponse.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException in getting all posts from API" + e.toString());
        }
        if(postJSONResponse.length() == 0) return null;
        return postJSONResponse;
    }

    /*
        Extract Category names and Data from a string
        Takes input a string which it converts to JSONObject and then extracts data
     */
    public static ArrayList extractCategoriesFromJSONResponse(String response) throws JSONException{
        // TODO

        if(response == null) return null;

        ArrayList<String> categoryList = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(response);
            if(mainArray.length() == 0) return null;

            for(int i = 0; i < mainArray.length(); i++) {
                categoryList.add(mainArray.get(i).toString().trim());
            }
            Log.v("Bleh", categoryList.toString());
            return categoryList;
        } catch (JSONException e) {
            Log.e("Bleh", e.toString());
            return null;
        }
    }

    public static ArrayList extractAllPostsForParticularCategoryFromJSONResponse(String responseCategory) throws JSONException {

        if(responseCategory == null) return null;

        ArrayList<String> postsForCategoryList = new ArrayList<>();

        try {
            JSONArray mainArray = new JSONArray(responseCategory);
            if(mainArray.length() == 0) return null;

            for(int i = 0; i < mainArray.length(); i++) {
                postsForCategoryList.add(mainArray.get(i).toString().trim());
            }
            Log.v("Bleh", postsForCategoryList.toString());
            return postsForCategoryList;
        } catch (JSONException e) {
            Log.e("Bleh", e.toString());
            return null;
        }
    }

    public static String extractAllPostsForAllCategoriesFromJSONResponse(String mainResponse, ArrayList categories) {
        if(mainResponse == null) return null;

        try {
            JSONObject obj = new JSONObject(mainResponse);
            ArrayList<ArrayList> categoryToPost = new ArrayList<>();

            for(int i = 0; i < categories.size(); i++) {

            }

        } catch (JSONException e) {
            Log.e("Bleh", e.toString());
            return null;
        }
        return null;
    }

}
