package com.example.soumya.feedthepanda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by Soumya on 29-11-2015.
 */
public class ChannelAdapter extends RecyclerSwipeAdapter<ChannelAdapter.SimpleViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Channel> objects;
    private Context mContext;

    //  ViewHolder Class

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout;
        TextView channelName;
        TextView channelDescription;
        TextView channelToggleSubscription;
        TextView channelShare;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            channelName = (TextView) itemView.findViewById(R.id.channelName);
            channelDescription = (TextView) itemView.findViewById(R.id.channelDescription);
            channelToggleSubscription = (TextView) itemView.findViewById(R.id.channelToggleSubscription);
            channelShare = (TextView) itemView.findViewById(R.id.channelShare);
        }
    }

    public ChannelAdapter(Context context, ArrayList<Channel> objects) {
        this.mContext = context;
        this.objects = objects;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_channel, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Channel item = objects.get(position);

        viewHolder.channelName.setText((item.getName()) + "  -  Row Position " + position);
        viewHolder.channelDescription.setText(item.getDescription());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        // Drag From Right
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));

        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onClose(SwipeLayout layout) {
                //when the SurfaceView totally cover the BottomView.
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                //you are swiping.
            }

            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                //when user's hand released.
            }
        });

        /*viewHolder.swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((((SwipeLayout) v).getOpenStatus() == SwipeLayout.Status.Close)) {
                    //Start your activity

                    Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getEmailId(), Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " onClick : " + item.getName() + " \n" + item.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });


        viewHolder.channelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Share " + viewHolder.channelName.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.channelToggleSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.channelName.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        // mItemManger is member in RecyclerSwipeAdapter Class
        mItemManger.bindView(viewHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }




    public int getCount() {
        return objects.size();
    }

    public Channel getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleViewHolder holder = null;
        if(convertView == null) {
            holder = new SimpleViewHolder(convertView);
            convertView = inflater.inflate(R.layout.list_item_channel, null);
            holder.channelName = (TextView) convertView.findViewById(R.id.channelName);
            holder.channelDescription = (TextView) convertView.findViewById(R.id.channelDescription);
//            holder.channelImg = (ImageView) convertView.findViewById(R.id.imageChannel);
            convertView.setTag(holder);
        } else {
            holder = (SimpleViewHolder) convertView.getTag();
        }
        holder.channelName.setText(objects.get(position).getName());
        holder.channelDescription.setText(objects.get(position).getDescription());
//        holder.channelImg.setImageResource(objects.get(position).getChannelImage());
        return convertView;
    }
}

