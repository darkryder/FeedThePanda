package com.example.soumya.feedthepanda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Soumya on 29-11-2015.
 */
public class ChannelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Channel> objects;

    private class ViewHolder {
        ImageView channelImg;
        TextView channelName;
    }

    public ChannelAdapter(Context context, ArrayList<Channel> objects) {
        inflater = LayoutInflater.from(context);
        this.objects = objects;
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
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_channel, null);
            holder.channelName = (TextView) convertView.findViewById(R.id.nameChannel);
            holder.channelImg = (ImageView) convertView.findViewById(R.id.imageChannel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.channelName.setText(objects.get(position).getName());
        holder.channelImg.setImageResource(objects.get(position).getChannelImage());

        return convertView;
    }
}

