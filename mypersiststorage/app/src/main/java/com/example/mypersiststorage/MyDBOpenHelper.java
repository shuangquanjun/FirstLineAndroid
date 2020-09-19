package com.example.mypersiststorage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "MyDBOpenHelper";
    private static final String sql_create_table_book = "create table Book ( " +
            "    id     INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    author TEXT," +
            "    Price  REAL," +
            "    pages  INTEGER," +
            "    name   TEXT" +
            " )";

    private String sql_query_table = " select name from sqlite_master where type='table' ";
    private Context mContext = null;
    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_create_table_book);
        Toast.makeText(mContext, "Datatbase is created!", Toast.LENGTH_SHORT).show();
//        Cursor cursor = db.query("sqlite_master",null,null,null,null,null,null);
//        if(cursor != null){
//            String str = cursor.getString(cursor.getColumnIndex("name"));
//            Log.d(TAG, "onCreate:  + str");
//        }
//        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
