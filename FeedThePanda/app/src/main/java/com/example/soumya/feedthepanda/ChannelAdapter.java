/*
package com.example.soumya.feedthepanda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

*/
/**
 * Created by Soumya on 29-11-2015.
 *//*

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Post> objects;

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView channelImg;
        public TextView channelName;

        */
/*public ViewHolder() {
            //super(itemView);
            channelImg = (ImageView)itemView.findViewById(R.id.listItemChannelImage);
            channelName = (TextView)itemView.findViewById(R.id.listItemChannelName);
        }*//*

    }

    public ChannelAdapter(Context context, ArrayList<Post> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
    }

    public int getCount() {
        return objects.size();
    }

    public Post getItem(int position) {
        return objects.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_channel, null);
            holder. = (TextView) convertView.findViewById(R.id.headingTextView);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.readCheckBox = (CheckBox) convertView.findViewById(R.id.readCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.headingTextView.setText(objects.get(position).getHeading());
        holder.descriptionTextView.setText(objects.get(position).getDescription());
        holder.dateTextView.setText(objects.get(position).getCreatedOn().toString());
        holder.readCheckBox.setChecked(objects.get(position).isRead());
        return convertView;
    }
}

*/
