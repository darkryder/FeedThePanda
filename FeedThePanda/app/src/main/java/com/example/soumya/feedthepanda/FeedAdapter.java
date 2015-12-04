package com.example.soumya.feedthepanda;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leocardz.aelv.library.AelvListViewHolder;

import java.util.ArrayList;

/**
 * Created by Soumya on 29-11-2015.
 */
public class FeedAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private ArrayList<Post> objects;
    private Context mContext;

    private class ViewHolder extends AelvListViewHolder{
        TextView headingTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        CheckBox readCheckBox;
        public ViewHolder() {
            super();
        }
    }

    public FeedAdapter(Context context, int textViewResourceId, ArrayList<Post> listItems) {
        super(context, textViewResourceId, listItems);
        this.objects = listItems;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
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
        Post post = objects.get(position);

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item_post, null);

            LinearLayout postViewWrap = (LinearLayout) convertView.findViewById(R.id.post_wrap);
            holder.setViewWrap(postViewWrap);
            holder.headingTextView = (TextView) convertView.findViewById(R.id.headingTextView);
            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
            holder.readCheckBox = (CheckBox) convertView.findViewById(R.id.readCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.getViewWrap().setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, post.getCurrentHeight()));

        holder.headingTextView.setText(post.getHeading());

        holder.descriptionTextView.setText(post.getDescription());
        holder.dateTextView.setText(post.getCreatedOn().toString());
        holder.readCheckBox.setChecked(post.isRead());
        post.setHolder(holder);

        return convertView;
    }
}
