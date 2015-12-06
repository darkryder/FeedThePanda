package com.example.soumya.feedthepanda;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.leocardz.aelv.library.Aelv;
import com.leocardz.aelv.library.AelvCustomAction;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.CardsEffect;

import java.util.ArrayList;


public class AllChannelsFragment extends android.support.v4.app.Fragment {

    ArrayList<Channel> objects;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_channels, container, false);


        objects = new ArrayList<Channel>();
        mockItems();

        ChannelAdapter channelAdapter = new ChannelAdapter(getActivity(), objects);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_channel);
        listView.setAdapter(channelAdapter);

        return rootView;
    }

    public void mockItems() {
        objects.add(new Channel(1, "Sheetu"));
        objects.add(new Channel(2, "FindMyStuff"));
        objects.add(new Channel(3, "IIITD"));
        objects.add(new Channel(1, "Sheetu"));
        objects.add(new Channel(2, "FindMyStuff"));
        objects.add(new Channel(3, "IIITD"));
        objects.add(new Channel(1, "Sheetu"));
        objects.add(new Channel(2, "FindMyStuff"));
        objects.add(new Channel(3, "IIITD"));
        objects.add(new Channel(1, "Sheetu"));
        objects.add(new Channel(2, "FindMyStuff"));
        objects.add(new Channel(3, "IIITD"));

    }
}
