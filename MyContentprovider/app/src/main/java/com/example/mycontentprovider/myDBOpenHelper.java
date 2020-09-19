package com.example.mycontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

class myDBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "myDBOpenHelper";
    private Context mContext;

    private static final String sql_create_table_book = "create table Book ( " +
            "    id     INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    author TEXT," +
            "    Price  REAL," +
            "    pages  INTEGER," +
            "    name   TEXT" +
            " )";


    public myDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override  
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: --->start to create DB.");

        db.execSQL(sql_create_table_book);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ----> need to update DB.");
    }
}
