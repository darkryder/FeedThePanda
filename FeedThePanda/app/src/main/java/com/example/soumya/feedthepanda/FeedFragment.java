package com.example.soumya.feedthepanda;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.leocardz.aelv.library.Aelv;
import com.leocardz.aelv.library.AelvCustomAction;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.CardsEffect;
import com.twotoasters.jazzylistview.effects.SlideInEffect;
import com.twotoasters.jazzylistview.effects.WaveEffect;
import com.twotoasters.jazzylistview.effects.ZipperEffect;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FeedFragment extends android.support.v4.app.Fragment {

    private static ArrayList<Post> objects;
    View rootView;

    private static boolean fetched = false;
    private static ArrayList<Post> fetched_posts = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        objects = new ArrayList<Post>();
        mockItems();

        FeedAdapter taskAdapter = new FeedAdapter(getActivity(), R.layout.list_item_post, objects);
        JazzyListView listView = (JazzyListView) rootView.findViewById(R.id.listview_posts);
        listView.setTransitionEffect(new CardsEffect());
        listView.setMaxAnimationVelocity(100);
        listView.setAdapter(taskAdapter);

        // Setup
        // Aelv aelv = new Aelv(true, 200, listItems, listView, adapter);
        final Aelv aelv = new Aelv(true, 200, objects, listView, taskAdapter, new AelvCustomAction() {
            @Override
            public void onEndAnimation(int position) {
                Log.v("Extra", objects.toString());
                Post post = objects.get(position);
//                objects.get(position).setDrawable(objects.get(position).isOpen() ? R.drawable.up_arrow : R.drawable.down_arrow);
                if(post.isOpen()) {
//                    objects.get(position).setDescription(objects.get(position).getDescription());
                    post.setCurrentHeight(post.getDescription().length());
                } else {
                    if(post.getDescription().length()>500) {
                        post.setCurrentHeight(post.getDescription().substring(1,500).length());
                    } else {
                        post.setCurrentHeight(post.getDescription().length());
                    }
//                    objects.get(position).setDescription(objects.get(position).getDescription().substring(1,500));
//                    objects.get(position).setCurrentHeight(objects.get(position).getDescription().substring(1,500).length());
                }
                /*objects.get(position).setDescription(objects.get(position).isOpen()
                        ? objects.get(position).getDescription()
                        : (objects.get(position).getDescription().substring(1,500)) + "...");*/
                // TODO Breaks while clicking
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Log.v("Extra", "position " + position);
                Log.v("Extra", "position " + objects.toString());
                Log.v("Extra", "position " + objects.get(position).toString());
                Log.v("Extra", "Toggle View: " + position + objects.get(position).toString());
                aelv.toggle(view, position);
            }
        });

        return rootView;
    }

    private void mockItems() {

        final int COLLAPSED_HEIGHT_1 = 150, COLLAPSED_HEIGHT_2 = 200, COLLAPSED_HEIGHT_3 = 250;
        final int EXPANDED_HEIGHT_1 = 250, EXPANDED_HEIGHT_2 = 300, EXPANDED_HEIGHT_3 = 350, EXPANDED_HEIGHT_4 = 400;

        FetchPosts task = new FetchPosts(getActivity());
        task.execute();

        if(fetched)
        {
            objects = fetched_posts;
            Log.v("Extra", objects.toString());
            for(int i = 0; i < objects.size(); i++)
            {
                Post current = objects.get(i);
                if (current.getDescription().length() > COLLAPSED_HEIGHT_2){
                    current.setUp(COLLAPSED_HEIGHT_2, current.getDescription().length(), false);
                } else {
                    current.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
                }
            }
        }

        if (!fetched)
        {
            Post listItem = new Post(1, "Bleh", "How Are you?", new Date(), new Channel(3, "Sheetu"));
            // setUp IS REQUIRED
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = new Post(1, "Bleh", "How Are you?", new Date(), new Channel(2, "FindMyStuff"));
            // setUp IS REQUIRED
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = new Post(1, "Bleh", "How Are you?", new Date(), new Channel(1, ""));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Aditi", "Lorem Ipsum", new Date(), new Channel(3, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc bibendum in odio in vulputate. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam elit erat, posuere at lacus et, ornare ultrices augue. In auctor ut tellus at scelerisque. Vivamus tempor magna et nisl accumsan scelerisque vel elementum ipsum. Praesent non dignissim sem, id egestas dui. Nam eu fringilla dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nullam tincidunt sapien eget magna faucibus dapibus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean rutrum, justo eu fringilla convallis, diam libero rutrum erat, lacinia euismod nibh metus at nulla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam rutrum turpis non viverra dictum.", new Date(), new Channel(4, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(4, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(3, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(3, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(3, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(2, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(2, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(1, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);

            listItem = (new Post(1, "Bleh", "How Are you?", new Date(), new Channel(1, "")));
            if(listItem.getDescription().length()>COLLAPSED_HEIGHT_2) {
                listItem.setUp(COLLAPSED_HEIGHT_2, listItem.getDescription().length(), false);
            } else {
                listItem.setUp(COLLAPSED_HEIGHT_2, COLLAPSED_HEIGHT_2, false);
            }
            objects.add(listItem);
            Log.v("Extra", objects.toString());
        }
    }


    private class FetchPosts extends AsyncTask<Void, Void, ArrayList<Post>>
    {
        Context c;
        public FetchPosts(Context c) {this.c = c;}

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {
            return DataFetcher.getPostsHelper(PreferenceManager.getDefaultSharedPreferences(c).getString("api_key", "nope"));
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            super.onPostExecute(posts);
//            Log.v("Extra", posts.toString() + fetched + fetched_posts.toString());
            objects = posts;
            fetched = true;
            fetched_posts = posts;
        }
    }
}
