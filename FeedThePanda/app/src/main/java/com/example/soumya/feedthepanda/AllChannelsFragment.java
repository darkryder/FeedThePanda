package com.example.soumya.feedthepanda;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import com.daimajia.swipe.util.Attributes;
import com.daimajia.*;

public class AllChannelsFragment extends android.support.v4.app.Fragment {

    ArrayList<Channel> objects;
    View rootView;

    private TextView channelEmptyView;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_all_channels, container, false);

        channelEmptyView = (TextView) getActivity().findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        // Layout Managers:
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Item Decorator:
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.abc_btn_borderless_material)));
        // mRecyclerView.setItemAnimator(new FadeInLeftAnimator());

        objects = new ArrayList<Channel>();
        mockItems();

        if (objects.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            channelEmptyView.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            channelEmptyView.setVisibility(View.GONE);
        }

        // Creating Adapter object
        ChannelAdapter mAdapter = new ChannelAdapter(getActivity(), objects);


        // Setting Mode to Single to reveal bottom View for one item in List
        // Setting Mode to Mutliple to reveal bottom Views for multile items in List
        ((ChannelAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

        /* Scroll Listeners */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        /*ChannelAdapter channelAdapter = new ChannelAdapter(getActivity(), objects);
        ListView listView = (ListView) rootView.findViewById(R.id.listView_channel);
        listView.setAdapter(channelAdapter);*/

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
