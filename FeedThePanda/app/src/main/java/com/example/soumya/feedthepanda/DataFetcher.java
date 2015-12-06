package com.example.soumya.feedthepanda;

import android.content.Context;
import android.os.AsyncTask;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Soumya on 04-12-2015.
 */
public class DataFetcher {
    public static final String API_URL = "http://192.168.51.207:5000/api/v1/";

    public static String getSignedResponse(String url_, String api, boolean POST, HashMap<String, String> params) throws IOException
    {
        URL url;
        url_ = url_ + "?api_key=" + api;
        if(!POST && params != null && params.size() != 0){
            StringBuilder queryString = new StringBuilder(url_);
            for(String i: params.keySet()){
                queryString.append("&" + i + "=" + params.get(i));
            }
            url_ = queryString.toString();
        }
        url = new URL(url_);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if (POST){
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            if (params != null && params.size() != 0){
                for(String i: params.keySet()){
                    urlConnection.setRequestProperty(i, params.get(i));
                }
            }
        }
        if(!POST) urlConnection.setRequestMethod("GET");
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

        Log.v("NetworkCall", url_ + ": " + buffer.toString());

        return buffer.toString();
    }

    public static ArrayList<Channel> getChannelsHelper(String api)
    {
        String resp = null;
        ArrayList<Channel> channels = new ArrayList<>();
        try{
            resp = getSignedResponse(API_URL + "channels", api, false, null);

            JSONArray jsonArrayResponse = new JSONArray(resp);
            for(int i = 0; i < jsonArrayResponse.length(); i++){
                JSONObject channelObj = jsonArrayResponse.getJSONObject(i);
                // TODO
                Channel channel = new Channel(1,"");
                channels.add(channel);
            }
        } catch (IOException e)
        {
            if (resp != null) Log.d("Extra", resp.toString());
            Log.d("Extra", "NetworkError");
        } catch (JSONException e)
        {
            Log.d("Extra", e.toString());
        }
//        catch (ParseException e) {
//            Log.d("Extra", e.toString());
//        }
        return channels;
    }

    public static ArrayList<Post> getPostsHelper(String api)
    {
        String resp = null;

        try {
            resp = getSignedResponse(API_URL + "feed", api, false, null);

            JSONArray jsonResponse = new JSONObject(resp).getJSONArray("posts");
            ArrayList<Post> posts = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            for(int i = 0; i < jsonResponse.length(); i++)
            {
                JSONObject small = jsonResponse.getJSONObject(i);
                posts.add(new Post(
                        small.getInt("id"),
                        small.getString("title"),
                        small.getString("description"),
                        sdf.parse(small.getString("created_at")),
                        new Channel(1, "")
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

    public static ArrayList<Post> getPostsOfChannelHelper(Channel channel, String api){
        String resp = null;
        ArrayList<Post> posts = new ArrayList<>();
        try{
            resp = getSignedResponse(API_URL + "channels/" + Integer.toString(channel.get_id()) + "/posts/", api, false, null);
            JSONArray jsonResponse = new JSONObject(resp).getJSONArray("posts");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            for(int i = 0; i < jsonResponse.length(); i++)
            {
                JSONObject small = jsonResponse.getJSONObject(i);
                posts.add(new Post(
                        small.getInt("id"),
                        small.getString("title"),
                        small.getString("description"),
                        sdf.parse(small.getString("created_at")),
                        channel
                ));
            }
            Log.i("InfoFetch", posts.toString());
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
        return posts;
    }

    public static boolean subscribeToChannelHelper(int channelID, String api)
    {
        String resp = null;
        try{
            resp = getSignedResponse(API_URL + "channels/" + Integer.toString(channelID) + "/subscribe", api, true, null);
            JSONObject jsonObject = new JSONObject(resp);
            return jsonObject.getInt("status") == 200;
        } catch (IOException e)
        {
            if (resp != null) Log.d("Extra", resp.toString());
            Log.d("Extra", "NetworkError");
        } catch (JSONException e)
        {
            Log.d("Extra", e.toString());
        }
        return false;
    }

    public static boolean unsubscribeToChannelHelper(int channelID, String api)
    {
        String resp = null;
        try{
            resp = getSignedResponse(API_URL + "channels/" + Integer.toString(channelID) + "/unsubscribe", api, true, null);
            JSONObject jsonObject = new JSONObject(resp);
            return jsonObject.getInt("status") == 200;
        } catch (IOException e)
        {
            if (resp != null) Log.d("Extra", resp.toString());
            Log.d("Extra", "NetworkError");
        } catch (JSONException e)
        {
            Log.d("Extra", e.toString());
        }
        return false;
    }

    public static boolean markPostsReadHelper(ArrayList<Integer> ids, String api)
    {
        String resp = null;
        try{
            StringBuilder queryString = new StringBuilder('[');
            for(int i: ids) queryString.append(Integer.toString(i) + ",");
            queryString.append(']');
            String q = queryString.toString();
            HashMap<String, String> map = new HashMap<>();
            map.put("ids", q);
            resp = getSignedResponse(
                    API_URL + "posts/mark_read",
                    api,
                    true,
                    map
            );
            JSONObject jsonObject = new JSONObject(resp);
            return jsonObject.getInt("status") == 200;
        } catch (IOException e)
        {
            if (resp != null) Log.d("Extra", resp.toString());
            Log.d("Extra", "NetworkError");
        } catch (JSONException e)
        {
            Log.d("Extra", e.toString());
        }
        return false;
    }
}

class getChannelsTask extends AsyncTask<Void, Void, ArrayList<Channel>>
{
    Context context;
    public getChannelsTask(Context c) {context = c;}
    @Override
    protected ArrayList<Channel> doInBackground(Void... voids) {
        return DataFetcher.getChannelsHelper(PreferenceManager.getDefaultSharedPreferences(context).getString("api_key","lol"));
    }

    @Override
    protected void onPostExecute(ArrayList<Channel> channels) {
        super.onPostExecute(channels);
        Log.v("getChannelsTask", channels == null ? "null" : channels.toString());
    }
}

class getPostsTask extends AsyncTask<Void, Void, ArrayList<Post>>
{
    Context context;
    public getPostsTask(Context c) {context = c;}
    @Override
    protected ArrayList<Post> doInBackground(Void... voids) {
        return DataFetcher.getPostsHelper(PreferenceManager.getDefaultSharedPreferences(context).getString("api_key", "lol"));
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        super.onPostExecute(posts);
        Log.v("getPostsTask", posts == null ? "null" : posts.toString());
    }
}

class getPostsOfChannelTask extends AsyncTask<Void, Void, ArrayList<Post>>
{
    Context context;
    Channel channel;
    public getPostsOfChannelTask(Context c, Channel channel) {context = c; this.channel = channel;}
    @Override
    protected ArrayList<Post> doInBackground(Void... voids) {
        return DataFetcher.getPostsOfChannelHelper(this.channel, PreferenceManager.getDefaultSharedPreferences(context).getString("api_key", "lol"));
    }

    @Override
    protected void onPostExecute(ArrayList<Post> posts) {
        super.onPostExecute(posts);
        Log.v("PostsOfChannel", channel.toString() + ":" + posts == null ? "null" : posts.toString());
    }
}

class subscribeToChannelTask extends AsyncTask<Void, Void, Boolean>
{
    Context context;
    int channelID;
    public subscribeToChannelTask(Context c, int channelID) {context = c; this.channelID = channelID;}

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DataFetcher.subscribeToChannelHelper(this.channelID, PreferenceManager.getDefaultSharedPreferences(context).getString("api_key", "lol"));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.v("subscribe", Integer.toString(channelID) + aBoolean);
    }
}

class unsubscribeToChannelTask extends AsyncTask<Void, Void, Boolean>
{
    Context context;
    int channelID;
    public unsubscribeToChannelTask(Context c, int channelID) {context = c; this.channelID = channelID;}

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DataFetcher.unsubscribeToChannelHelper(this.channelID, PreferenceManager.getDefaultSharedPreferences(context).getString("api_key", "lol"));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.v("unsubscribe", Integer.toString(channelID) + aBoolean);
    }
}

class markPostsRead extends AsyncTask<Void, Void, Boolean>
{
    Context context;
    ArrayList<Integer> postIds;
    public markPostsRead(Context c, ArrayList<Integer> postIds) {context = c; this.postIds= postIds;}

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DataFetcher.markPostsReadHelper(this.postIds, PreferenceManager.getDefaultSharedPreferences(context).getString("api_key", "lol"));
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.v("subscribe", postIds.toString() + ":" + aBoolean);
    }
}
