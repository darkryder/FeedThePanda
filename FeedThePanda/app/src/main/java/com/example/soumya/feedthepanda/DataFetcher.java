package com.example.soumya.feedthepanda;

import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Soumya on 04-12-2015.
 */
public class DataFetcher {
    public static final String API_URL = "http://192.168.51.207:5000/api/v1/";

    public static String getSignedResponse(String url_, String api) throws IOException
    {
        URL url = new URL(url_);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) throw new IOException("InputStream was null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) return null;

        return buffer.toString();
    }


    public static ArrayList<Post> getPosts(String api)
    {
        String resp = null;

        try {
            URL url = new URL(DataFetcher.API_URL + "feed?api_key=" + api);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) throw new IOException("InputStream was null");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) return null;

            resp = buffer.toString();
            JSONArray jsonResponse = new JSONObject(resp).getJSONArray("posts");
            ArrayList<Post> posts = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            for(int i = 0; i < jsonResponse.length(); i++)
            {
                if( i == 4)continue;
                JSONObject small = jsonResponse.getJSONObject(i);
                posts.add(new Post(
                        small.getInt("id"),
                        small.getString("title"),
                        small.getString("description"),
                        sdf.parse(small.getString("created_at")),
                        new Channel("")
                ));
            }
            Log.i("InfoFetch", posts.toString());
            return posts;  //channels.toArray(new Channel[channels.size()]);
        } catch (IOException e)
        {
            if (resp != null) Log.d("Extra", resp.toString());
            Log.d("Extra", "NetworkError");
        } catch (JSONException e)
        {
            Log.d("Extra", e.toString());
        } catch (ParseException e) {
            Log.d("Extra", e.toString());
        }
        return null;
    }



    public static String getAuthenticatedResponse(String url_, String cookie_data) throws IOException {
        URL url = new URL(url_);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        List params = new ArrayList();

//        List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("firstParam", paramValue1));
//        params.add(new BasicNameValuePair("secondParam", paramValue2));
//        params.add(new BasicNameValuePair("thirdParam", paramValue3));

        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) throw new IOException("InputStream was null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        if (buffer.length() == 0) return null;

        return buffer.toString();
    }

}
