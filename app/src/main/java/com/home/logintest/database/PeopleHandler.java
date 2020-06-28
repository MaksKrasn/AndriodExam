package com.home.logintest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.home.logintest.data.model.LoggedInUser;

import java.util.ArrayList;
import java.util.List;

public class PeopleHandler extends SQLiteOpenHelper implements BaseHandler<LoggedInUser> {
    public static String TABLE_NAME = "people";
    public static String KEY_ID = "id";
    public static String KEY_DISPLAYNAME = "displayname";
    public static String KEY_PASSWORD = "password";

    public PeopleHandler(@Nullable Context context) {
        super( context, DatabaseSettings.DB_NAME, null, DatabaseSettings.DB_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_DISPLAYNAME + " TEXT," +
                KEY_PASSWORD + " TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void create(LoggedInUser item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( KEY_ID, item.getId() );
        values.put( KEY_DISPLAYNAME, item.getDisplayName() );
        values.put( KEY_PASSWORD, item.getPassword() );
        db.insert( TABLE_NAME, null, values );
        db.close();
    }

    @Override
    public List<LoggedInUser> getAll() {
        List<LoggedInUser> people = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PeopleHandler.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String displayName = cursor.getString(1);
                String password = cursor.getString(2);
                LoggedInUser person = new LoggedInUser(id, displayName, password);
                people.add(person);
            } while(cursor.moveToNext());
        }
        db.close();
        return people;
    }
}
