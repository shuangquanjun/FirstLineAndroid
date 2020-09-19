package com.example.mycontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    private static final String TAG = "MyContentProvider";
    private static int dbVersion = 1;
    private static UriMatcher uriMatcher;
    private static String Authroity = "com.example.mycontentprovider";
    private static final int TALBE_Book = 1;


    public static final String Uri_Book = "content://" + Authroity +"/Book";
    public static final String Book_column_name = "name";
    public static final String Book_column_author = "author";
    public static final String Book_column_Price = "Price";
    public static final String Book_column_pages = "pages";

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Authroity,"Book", TALBE_Book);
    }

    myDBOpenHelper dbOpenHelper = null;

    public MyContentProvider() {
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int rows = -1;
        switch (uriMatcher.match(uri)){
            case TALBE_Book:
                Log.d(TAG, "delete: ---> is called!");
                rows = dbOpenHelper.getWritableDatabase().delete("Book", selection, selectionArgs);
                break;

            default:
                break;
        }

        return rows;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri uriReturn = null;
        long id = -1;
        SQLiteDatabase db = null;
        if(dbOpenHelper == null){
            dbOpenHelper = new myDBOpenHelper(getContext(),"database_myBook", null, dbVersion);
        }
        db = dbOpenHelper.getWritableDatabase();
        // TODO: Implement this to handle requests to insert a new row.
        switch (uriMatcher.match(uri)){
            case TALBE_Book:
                Log.d(TAG, "insert: ----> insert into book");
                id = db.insert("Book", null, values);
                uriReturn = Uri.parse(uri + "/" + id);
                break;
            default:
                Log.d(TAG, "insert: ---> no matching uri");
                break;

        }
        return uriReturn;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Log.d(TAG, "onCreate: ---> onCreate is called!");
        dbOpenHelper = new myDBOpenHelper(getContext(), "database_myBook", null, dbVersion);
        dbOpenHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case TALBE_Book:
                Log.d(TAG, "query: ---> query book!");
                cursor = dbOpenHelper.getReadableDatabase().query(
                        "Book",
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                Log.d(TAG, "query: --->  no matching uri");
                break;

        }
        //throw new UnsupportedOperationException("Not yet implemented");
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int rows = -1;
        switch ( uriMatcher.match(uri)){
            case TALBE_Book:
                rows = dbOpenHelper.getWritableDatabase().update("Book",values,selection,selectionArgs);
                break;
            default:
                break;
        }
        return rows;
        // TODO: Implement this to handle requests to update one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
