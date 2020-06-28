package com.home.logintest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.home.logintest.data.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class PhotoHandler extends SQLiteOpenHelper implements BaseHandler<Photo> {
    public static String TABLE_NAME = "photos";
    public static String KEY_ID = "id";
    public static String KEY_PHOTO_PATH = "photopath";
    public static String KEY_AUTHOR_ID = "authorid";
    public static String KEY_PHOTO_NAME = "description";

    public PhotoHandler(@Nullable Context context) {
        super(context, DatabaseSettings.DB_NAME, null, DatabaseSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_PHOTO_NAME + " TEXT," +
                KEY_PHOTO_PATH + " TEXT," +
                KEY_AUTHOR_ID + " INTEGER" +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void create(Photo item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( KEY_ID, item.getId() );
        values.put( KEY_PHOTO_NAME, item.getPhotoName() );
        values.put( KEY_PHOTO_PATH, item.getPhotoPath() );
        values.put(KEY_AUTHOR_ID, item.getAuthorId());
        db.insert( TABLE_NAME, null, values );
        db.close();
    }

    @Override
    public List<Photo> getAll() {
        List<Photo> photos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PhotoHandler.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String photoName = cursor.getString(1);
                String photoPath = cursor.getString(2);
                int authorId = Integer.parseInt(cursor.getString(3));
                Photo photo = new Photo(id, photoName, photoPath, authorId);
                photos.add(photo);
            } while(cursor.moveToNext());
        }
        db.close();
        return photos;
    }
}
