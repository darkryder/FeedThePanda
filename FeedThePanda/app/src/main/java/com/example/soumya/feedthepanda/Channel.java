package com.example.soumya.feedthepanda;


import android.media.Image;

import java.util.ArrayList;

/**
 * Created by Soumya on 29-11-2015.
 */
public class Channel {

    private String name;
    private String description;
    private int channelImage;
    private ChannelSubscriptionType subscriptionType;
    private boolean approved;
    private String rssLink;
    private boolean isSubscribed;
    private ArrayList<User> adminList;

    public Channel(String name) {
        this(name, null, false, null);
    }

    public Channel(String name, String description) {
        this(name, description, false, null);
    }

    public Channel(String name, String description, boolean isSubscribed, ArrayList<User> admins) {
        this.name = name;
        this.description = description;
        this.isSubscribed = isSubscribed;
        this.adminList = admins;
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

    public ArrayList<User> getAdminList() {
        return adminList;
    }

    public void setAdminList(ArrayList<User> adminList) {
        this.adminList = adminList;
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
}
