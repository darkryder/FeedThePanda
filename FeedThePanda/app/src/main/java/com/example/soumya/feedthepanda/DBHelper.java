package com.example.soumya.feedthepanda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Soumya on 06-12-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 1;

    public static final String DATABASE_NAME = "feedthepanda.db";

    public static final String TABLE_NAME_POST = "posts";
    public static final String COLUMN_POST_ID = "postid";
    public static final String COLUMN_POST_TITLE = "title";
    public static final String COLUMN_POST_DESCRIPTION = "description";
    public static final String COLUMN_POST_TO_CHANNEL_ID = "post_to_channel_id";
    public static final String COLUMN_POST_LINK = "link";
    public static final String COLUMN_POST_MADE_BY = "made_by";
    public static final String COLUMN_POST_CREATED_ON = "created_on";   // Type: Date
    public static final String COLUMN_POST_IS_READ = "is_read";
    //TODO Images Post

    public static final String TABLE_NAME_CHANNEL = "channels";
    public static final String COLUMN_CHANNEL_ID = "channelid";
    public static final String COLUMN_CHANNEL_TITLE = "title";
    public static final String COLUMN_CHANNEL_DESCRIPTION = "description";
    public static final String COLUMN_CHANNEL_SUBSCRIPTION_TYPE = "subscription_type";
    public static final String COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED = "is_approved";
    public static final String COLUMN_CHANNEL_RSS_LINK = "rss_link";
    public static final String COLUMN_CHANNEL_IS_SUBSCRIBED = "is_subscribed";
    // TODO Images Channel

    private static final String[] post_projection = {
            COLUMN_POST_ID,
            COLUMN_POST_TITLE,
            COLUMN_POST_DESCRIPTION,
            COLUMN_POST_LINK,
            COLUMN_POST_MADE_BY,
            COLUMN_POST_CREATED_ON,
            COLUMN_POST_TO_CHANNEL_ID,
            COLUMN_POST_IS_READ
    };

    private static final String[] channel_projection = {
            COLUMN_CHANNEL_ID,
            COLUMN_CHANNEL_TITLE,
            COLUMN_CHANNEL_DESCRIPTION,
            COLUMN_CHANNEL_SUBSCRIPTION_TYPE,
            COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED,
            COLUMN_CHANNEL_RSS_LINK,
            COLUMN_CHANNEL_IS_SUBSCRIBED
    };

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    public static void deleteDatabase(Context context)
    {
        context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CHANNEL);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_POST + " (" +
                        COLUMN_POST_ID + " INT NOT NULL PRIMARY KEY, " +
                        COLUMN_POST_TITLE + " VARCHAR(255) NOT NULL, " +
                        COLUMN_POST_DESCRIPTION + " VARCHAR(100000), " +
                        COLUMN_POST_TO_CHANNEL_ID + " INT NOT NULL, " +
                        COLUMN_POST_LINK + " VARCHAR(255), " +
                        COLUMN_POST_MADE_BY + " VARCHAR(255), " +
                        COLUMN_POST_CREATED_ON + " VARCHAR(1024), " +
                        COLUMN_POST_IS_READ + " INT " +
                        ");"
        );

        db.execSQL("CREATE TABLE " + TABLE_NAME_CHANNEL + " (" +
                        COLUMN_CHANNEL_ID + " INT NOT NULL PRIMARY KEY, " +
                        COLUMN_CHANNEL_TITLE + " VARCHAR(255) NOT NULL, " +
                        COLUMN_CHANNEL_DESCRIPTION + " VARCHAR(100000), " +
                        COLUMN_CHANNEL_SUBSCRIPTION_TYPE + " INT NOT NULL, " +
                        COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED + " INT, " +
                        COLUMN_CHANNEL_RSS_LINK + " VARCHAR(1000), " +
                        COLUMN_CHANNEL_IS_SUBSCRIBED + " INT " +
                        ");"
        );
    }

    public boolean insertPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();

        // check that channel exists already in db
        if((post.getChannel() == null) || getChannelFromID(post.getChannel().get_id()) == null)
        {return false;}
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_POST_ID, post.get_id());
        contentValues.put(COLUMN_POST_TITLE, post.getHeading());
        contentValues.put(COLUMN_POST_DESCRIPTION, post.getDescription());
        contentValues.put(COLUMN_POST_MADE_BY, post.getMadeBy());
        contentValues.put(COLUMN_POST_LINK, post.getLink());
        contentValues.put(COLUMN_POST_CREATED_ON, post.getCreatedOn().toString());
        contentValues.put(COLUMN_POST_TO_CHANNEL_ID, post.getChannel().get_id());
        contentValues.put(COLUMN_POST_IS_READ, post.isRead()?1:0);
        long check = db.insert(TABLE_NAME_POST, null, contentValues);
        db.close();
        return check > 0;
    }

    public boolean insertChannel(Channel channel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CHANNEL_ID, channel.get_id());
        contentValues.put(COLUMN_CHANNEL_TITLE, channel.getName());
        contentValues.put(COLUMN_CHANNEL_DESCRIPTION, channel.getDescription());
        contentValues.put(COLUMN_CHANNEL_SUBSCRIPTION_TYPE, channel.getSubscriptionType().id);
        contentValues.put(COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED, channel.isApproved()?1:0);
        contentValues.put(COLUMN_CHANNEL_RSS_LINK, channel.getRssLink());
        contentValues.put(COLUMN_CHANNEL_IS_SUBSCRIBED, channel.isSubscribed()?1:0);
        long check = db.insert(TABLE_NAME_CHANNEL, null, contentValues);
        db.close();
        return check > 0;
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_POST, null);
        while(c.moveToNext()) {
            posts.add(getPostFromCursor(c));
        }
        c.close();
        db.close();
        return posts;
    }

    private Post getPostFromCursor(Cursor c) {
        if(c==null) {
            return null;
        }
        int postId = c.getInt(c.getColumnIndexOrThrow(COLUMN_POST_ID));
        String postTitle = c.getString(c.getColumnIndexOrThrow(COLUMN_POST_TITLE));
        String postDescription = c.getString(c.getColumnIndexOrThrow(COLUMN_POST_DESCRIPTION));
        String postLink = c.getString(c.getColumnIndexOrThrow(COLUMN_POST_LINK));
        String postMadeBy = c.getString(c.getColumnIndexOrThrow(COLUMN_POST_MADE_BY));
        Date postcreatedOn = Post.StringToDateParser(c.getString(c.getColumnIndexOrThrow(COLUMN_POST_CREATED_ON)));
        Channel postChannel = getChannelFromID(c.getInt(c.getColumnIndexOrThrow(COLUMN_POST_TO_CHANNEL_ID)));
        Boolean postIsRead = c.getInt(c.getColumnIndexOrThrow(COLUMN_POST_IS_READ))==1?true:false;
        Post post = new Post(postId, postTitle, postDescription, postMadeBy, postcreatedOn, postIsRead, postChannel);
        return post;
    }

    public ArrayList<Channel> getAllChannels() {
        ArrayList<Channel> channels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_CHANNEL, null);
        while(c.moveToNext()) {
            channels.add(getChannelFromCursor(c));
        }
        c.close();
        db.close();
        return channels;
    }

    private Channel getChannelFromCursor(Cursor c) {
        if(c==null || c.getCount() <= 0) {
            return null;
        }
        int channelId = c.getInt(c.getColumnIndexOrThrow(COLUMN_CHANNEL_ID));
        String channelTitle = c.getString(c.getColumnIndexOrThrow(COLUMN_CHANNEL_TITLE));
        String channelDescription = c.getString(c.getColumnIndexOrThrow(COLUMN_CHANNEL_DESCRIPTION));
        ChannelSubscriptionType channelSubscriptionType = ChannelSubscriptionType.resolveToCategory(c.getInt(c.getColumnIndexOrThrow(COLUMN_CHANNEL_SUBSCRIPTION_TYPE)));
        Boolean channelIsMembershipApproved = c.getInt(c.getColumnIndexOrThrow(COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED))==1?true:false;
        String rssLink = c.getString(c.getColumnIndexOrThrow(COLUMN_CHANNEL_RSS_LINK));
        Boolean channelIsSubscribed = c.getInt(c.getColumnIndexOrThrow(COLUMN_CHANNEL_IS_SUBSCRIBED))==1?true:false;
        Channel channel = new Channel(channelId, channelTitle, channelDescription, channelIsSubscribed, channelIsMembershipApproved, rssLink, channelSubscriptionType);
        return channel;
    }

    public Post getPostFromID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME_POST,
                post_projection,
                COLUMN_POST_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if(c==null || c.getCount() <= 0) {
            db.close();
            return null;
        }
        c.moveToFirst();
        Post post = getPostFromCursor(c);
        c.close();
        db.close();
        return post;
    }

    public Channel getChannelFromID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                TABLE_NAME_CHANNEL,
                channel_projection,
                COLUMN_CHANNEL_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if(c==null || c.getCount() <= 0) {
            db.close();
            return null;
        }
        c.moveToFirst();
        Channel channel = getChannelFromCursor(c);
        c.close();
        db.close();
        return channel;
    }

    public void modifyPost(Post freshPost) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_POST + " SET " +
                COLUMN_POST_TITLE + " = \"" + DatabaseUtils.sqlEscapeString(freshPost.getHeading()) + "\", " +
                COLUMN_POST_DESCRIPTION + " = \"" + DatabaseUtils.sqlEscapeString(freshPost.getDescription()) + "\", " +
                COLUMN_POST_LINK + " = \"" + DatabaseUtils.sqlEscapeString(freshPost.getLink()) + "\", " +
                COLUMN_POST_MADE_BY + " = \"" + freshPost.getMadeBy() + "\", " +
                COLUMN_POST_CREATED_ON + " = \"" + freshPost.getCreatedOn().toString() + "\", " +
                COLUMN_POST_TO_CHANNEL_ID + " = " + freshPost.getChannel().get_id() + ", " +
                COLUMN_POST_IS_READ + " = " + Integer.toString(freshPost.isRead()?1:0) + " " +
                " WHERE " + COLUMN_POST_ID + " = " + freshPost.get_id() +
                ";");

        db.close();
    }

    public void modifyChannel(Channel freshChannel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME_CHANNEL + " SET " +
                COLUMN_CHANNEL_TITLE + " = \"" + DatabaseUtils.sqlEscapeString(freshChannel.getName()) + "\", " +
                COLUMN_CHANNEL_DESCRIPTION + " = \"" + DatabaseUtils.sqlEscapeString(freshChannel.getDescription()) + "\", " +
                COLUMN_CHANNEL_SUBSCRIPTION_TYPE + " = " + freshChannel.getSubscriptionType().id + ", " +
                COLUMN_CHANNEL_IS_MEMBERSHIP_APPROVED + " = " + Integer.toString(freshChannel.isApproved()?1:0) + ", " +
                COLUMN_CHANNEL_RSS_LINK + " = \"" + DatabaseUtils.sqlEscapeString(freshChannel.getRssLink()) + "\", " +
                COLUMN_CHANNEL_IS_SUBSCRIBED + " = " + Integer.toString(freshChannel.isSubscribed()?1:0) +  " " +
                " WHERE " + COLUMN_CHANNEL_ID + " = " + freshChannel.get_id() +
                ";");
        db.close();
    }

    // TODO
    // getPostAccordingToID
    // getChannelFromID
    // getAllChannels
    // getAllPosts
    // insertPost
    // insertChannel
    // modifyPost
    // modifyChannel

    //TODO Images
}
