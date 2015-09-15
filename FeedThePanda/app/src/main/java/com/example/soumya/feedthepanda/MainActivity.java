package com.example.soumya.feedthepanda;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FetchCategoriesTask(this).execute();
        /*try {
            JSONResponseFetcher.extractCategoriesFromJSONResponse(JSONResponse);
        } catch (JSONException e) {
            Log.e("Bleh", e.toString());
        }*/
    }

    private class FetchCategoriesTask extends AsyncTask<Void, Void, String> {
        final String LOG_TAG = "FETCH_ALL_CATEGORIES";
        Context context;

        public FetchCategoriesTask(Context context){
            this.context = context;
            /*this(PreferenceManager.getDefaultSharedPreferences(context).getString(
                    context.getString(R.string.api_auth_token), "nope"
            ));*/
        }

        @Override
        protected String doInBackground(Void... voids) {
            Log.v(LOG_TAG, "Fetching events task started");
            return JSONResponseFetcher.getCategoryDataFromAPI();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
