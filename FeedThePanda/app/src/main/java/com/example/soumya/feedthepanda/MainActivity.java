package com.example.soumya.feedthepanda;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    // GCM Listener
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean logged_in = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("logged_in", false);
        if (!logged_in)
        {
            Intent login = new Intent(this, LoginActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
        }

        Intent intent = new Intent(getApplicationContext(),
                RegistrationIntentService.class);
        ComponentName name = startService(intent);
        Log.v("Extra", "Sent intent" + name.toString());
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("Extra", "GCM Started. Hopefully");
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                } else {
                }
            }
        };


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        refreshDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment objFragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(position) {
            case 0:
                objFragment = new FeedFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, objFragment)
                        .commit();
                break;
            case 1:
                objFragment = new AllChannelsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, objFragment)
                        .commit();
                break;
        }

        //PlaceholderFragment.newInstance(position+1)
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }*/

    /*@Override
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
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
    private void refreshDB()
    {
        final DBHelper dbHelper = new DBHelper(this);
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("api_key", "279b41abea9a47d45e1bc416d89a465c").commit();

        DataHolder.channels = dbHelper.getAllChannels();
        DataHolder.feed = dbHelper.getAllPosts();

        final getPostsTask getFeedTask = new getPostsTask(this){
            @Override
            protected void onPostExecute(ArrayList<Post> posts) {
                super.onPostExecute(posts);
                if(posts == null) return;
                if(DataHolder.feed == null) DataHolder.feed= new ArrayList<>();
                for(Post post: posts){
                    DataHolder.feed.add(post);
                    if(dbHelper.getPostFromID(post.get_id()) == null){
                        dbHelper.insertPost(post);
                        Log.v("refreshDB", "Inserting " + post);
                    } else{
                        dbHelper.modifyPost(post);
                        Log.v("refreshDB", "Modifying " + post);

                    }
                }
            }
        };
        getChannelsTask channelsTask = new getChannelsTask(this){
            @Override
            protected void onPostExecute(ArrayList<Channel> channels) {
                super.onPostExecute(channels);
                if (channels == null) return;
                if (DataHolder.channels == null ) DataHolder.channels = new ArrayList<>();
                for(Channel channel: channels){
                    DataHolder.channels.add(channel);
                    if (dbHelper.getChannelFromID(channel.get_id()) == null){
                        dbHelper.insertChannel(channel);
                        Log.v("refreshDB", "Inserting " + channel);
                    } else {
                        dbHelper.modifyChannel(channel);
                        Log.v("refreshDB", "Modifying " + channel);
                    }
                }
                // run this afterwards because db has a dependency.
                getFeedTask.executeOnExecutor(THREAD_POOL_EXECUTOR);
            }
        };
        channelsTask.execute(); // will automatically call refreshFeedTaskInDb
    }
}
