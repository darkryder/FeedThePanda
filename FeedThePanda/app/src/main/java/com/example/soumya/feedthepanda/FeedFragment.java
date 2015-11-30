package com.example.soumya.feedthepanda;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FeedFragment extends android.support.v4.app.Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        ArrayList<Post> objects = new ArrayList<Post>();

        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));
        objects.add(new Post("Aditi", "Lorem Ipsum", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc bibendum in odio in vulputate. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aliquam elit erat, posuere at lacus et, ornare ultrices augue. In auctor ut tellus at scelerisque. Vivamus tempor magna et nisl accumsan scelerisque vel elementum ipsum. Praesent non dignissim sem, id egestas dui. Nam eu fringilla dui. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nullam tincidunt sapien eget magna faucibus dapibus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aenean rutrum, justo eu fringilla convallis, diam libero rutrum erat, lacinia euismod nibh metus at nulla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam rutrum turpis non viverra dictum.", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));
        objects.add(new Post("Bleh", "How Are you?", new Date(), new Channel("")));

        FeedAdapter taskAdapter = new FeedAdapter(getActivity(), objects);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_posts);
        listView.setAdapter(taskAdapter);
        return rootView;
    }

}
