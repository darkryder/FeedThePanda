package com.example.soumya.feedthepanda;


import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Soumya on 29-11-2015.
 */
public class Channel {

    private int _id;
    private String name;
    private String description;
    private int channelImage;
    private ChannelSubscriptionType subscriptionType;
    private boolean approved;
    private String rssLink;
    private boolean isSubscribed;

    public Channel(int id, String name) {
        this(id, name, null, false, false, null, ChannelSubscriptionType.MEMBER);
    }

    public Channel(int id, String name, String description) {
        this(id, name, description, false, false, null, ChannelSubscriptionType.MEMBER);
    }

    public Channel(int id, String name, String description, boolean isSubscribed) {
        this(id, name, description, isSubscribed, false, null, ChannelSubscriptionType.MEMBER);
    }

    public Channel(int id, String name, String description, boolean isSubscribed, boolean isApproved, String rssLink, ChannelSubscriptionType subscriptionType) {
        this._id = id;
        this.name = name;
        this.description = description;
        this.isSubscribed = isSubscribed;
        this.approved = isApproved;
        this.rssLink = rssLink;
        this.subscriptionType = subscriptionType;
    }

    public void copyFrom(Channel freshChannel){
        Channel f = freshChannel;
        _id = f._id; name = f.name; description = f.description;
        isSubscribed = f.isSubscribed; approved = f.approved;
        rssLink = f.rssLink; subscriptionType = f.subscriptionType;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

    public int getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(int channelImage) {
        this.channelImage = channelImage;
    }

    public ChannelSubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(ChannelSubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getRssLink() {
        return rssLink;
    }

    public void setRssLink(String rssLink) {
        this.rssLink = rssLink;
    }

    @Override
    public boolean equals(Object o) {
        Channel other = (Channel) o;
        return _id == other.get_id() && name.equals(other.name) && description.equals(other.description)
                && channelImage == other.channelImage && subscriptionType.equals(other.subscriptionType)
                && approved == other.approved && rssLink.equals(other.rssLink) && isSubscribed == other.isSubscribed;
    }
}
