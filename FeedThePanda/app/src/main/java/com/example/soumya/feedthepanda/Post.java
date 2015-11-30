package com.example.soumya.feedthepanda;

import java.util.Date;

/**
 * Created by Soumya on 29-11-2015.
 */
public class Post {

    private String heading;
    private String description;
    private String madeBy;
    private Date createdOn;
    private boolean isRead;
    private Channel channel;
    private String link;

    public Post(String heading, String description, Date createdOn, Channel channel) {
        this(heading, description, null, createdOn, false, channel);
    }

    public Post(String heading, String description, boolean isRead, Channel channel) {
        this(heading, description, null, null, isRead, channel);
    }

    public Post(String heading, String description, String madeBy, Date createdOn,Channel channel) {
        this(heading, description, madeBy, createdOn, false, channel);
    }

    public Post(String heading, String description, String madeBy, Date createdOn, boolean isRead, Channel channel) {
        this.heading = heading;
        this.description = description;
        this.madeBy = madeBy;
        this.createdOn = createdOn;
        this.isRead = isRead;
        this.channel = channel;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}