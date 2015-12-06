package com.example.soumya.feedthepanda;

import com.leocardz.aelv.library.AelvListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Soumya on 29-11-2015.
 */
public class Post extends AelvListItem{

    private int _id;
    private String heading;
    private String description;
    private String madeBy;
    private Date createdOn;
    private boolean isRead;
    private Channel channel;
    private String link;
    private int drawable;

    public Post(int id, String heading, String description, Date createdOn, Channel channel) {
        this(id, heading, description, null, createdOn, false, channel);
    }

    public Post(int id, String heading, String description, boolean isRead, Channel channel) {
        this(id, heading, description, null, null, isRead, channel);
    }

    public Post(int id, String heading, String description, String madeBy, Date createdOn,Channel channel) {
        this(id, heading, description, madeBy, createdOn, false, channel);
    }

    public Post(int id, String heading, String description, String madeBy, Date createdOn, boolean isRead, Channel channel) {
        super();
        this._id = id;
        this.heading = heading;
        this.description = description;
        this.madeBy = madeBy;
        this.createdOn = createdOn;
        this.isRead = isRead;
        this.channel = channel;
        this.drawable = R.drawable.down_arrow;
    }

    public static Date StringToDateParser(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public void copyFrom(Post freshPost){
        Post f = freshPost;
        _id = f._id; heading = f.heading; description = f.description;
        madeBy = f.madeBy; createdOn = f.createdOn; isRead = f.isRead;
        channel = f.channel; drawable = f.drawable;
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

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}