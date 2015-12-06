package com.example.soumya.feedthepanda;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.BaseColumns;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by Soumya on 06-12-2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABSE_VERSION = 1;

    public static final String DATABASE_NAME = "feedthepanda.db";
    public static final String TABLE_NAME = "posts";
    public static final String COLUMN_POST_ID = "postid";
    public static final String COLUMN_POST_TITLE = "title";
    public static final String COLUMN_POST_DESCRIPTION = "description";
    public static final String COLUMN_POST_TO_CHANNEL_ID = "post_to_channel_id";
    public static final String COLUMN_POST_LINK = "link";
    public static final String COLUMN_POST_MADE_BY = "made_by";
    public static final String COLUMN_POST_CREATED_ON = "created_on";   // Type: Date

    private static final String[] projection = {
            COLUMN_POST_ID,
            COLUMN_POST_TITLE,
            COLUMN_POST_DESCRIPTION,
            COLUMN_POST_LINK,
            COLUMN_POST_MADE_BY,
            COLUMN_POST_CREATED_ON,
            COLUMN_POST_TO_CHANNEL_ID
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_POST_ID + " INT NOT NULL PRIMARY KEY, " +
                        COLUMN_POST_TITLE + " VARCHAR(255) NOT NULL, " +
                        COLUMN_POST_DESCRIPTION + " VARCHAR(100000), " +
                        COLUMN_POST_TO_CHANNEL_ID + " INT NOT NULL " +
                        COLUMN_POST_LINK + " VARCHAR(255), " +
                        COLUMN_POST_MADE_BY + " VARCHAR(255), " +
                        COLUMN_POST_CREATED_ON + " VARCHAR(1024), " +
                        ");"
        );
    }

    /*public boolean insertPost(Post post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EVENT_ID, event.id);
        contentValues.put(COLUMN_EVENT_NAME, event.name);
        contentValues.put(COLUMN_CATEGORY_IDS, getCommaSeperatedCategories(event.categories));
        contentValues.put(COLUMN_LAST_UPDATED, Event.parseDateToString(event.updated_at));
        contentValues.put(COLUMN_IMAGE_URL, event.image_url);
        contentValues.put(COLUMN_CONTACT, event.contact);
        contentValues.put(COLUMN_IS_REGISTERED, event.registered);
        contentValues.put(COLUMN_IS_TEAM_EVENT, event.team_event);
        contentValues.put(COLUMN_TEAM_ID, event.team_id);
        contentValues.put(COLUMN_EVENT_ELIGIBILITY, event.eligibility);
        contentValues.put(COLUMN_EVENT_JUDGING, event.judging);
        contentValues.put(COLUMN_EVENT_PRIZES, event.prizes);
        contentValues.put(COLUMN_EVENT_RULES, event.rules);
        contentValues.put(COLUMN_TEAM_SIZE, event.team_size);
        contentValues.put(COLUMN_VENUE, event.venue);
        contentValues.put(COLUMN_EVENT_DESCRIPTION, event.description);
        contentValues.put(COLUMN_EVENT_DATE_TIME, Event.parseDateToString(event.event_date_time));
        long check = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return check > 0;
    }

    protected long saveBitmap(SQLiteDatabase database, Bitmap bmp) {
        int size = bmp.getRowBytes() * bmp.getHeight();
        ByteBuffer b = ByteBuffer.allocate(size); bmp.copyPixelsToBuffer(b);
        byte[] bytes = new byte[size];
        b.get(bytes, 0, bytes.length);
        ContentValues cv=new ContentValues();
        cv.put(CHUNK, bytes);
        this.id= database.insert(TABLE, null, cv);
    }*/
}
