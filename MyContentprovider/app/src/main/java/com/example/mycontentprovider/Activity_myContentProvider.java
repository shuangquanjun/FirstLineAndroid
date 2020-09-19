package com.example.mycontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Activity_myContentProvider extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Activity_myContentProvi";

    private Button button_db_insert = null;
    private Button button_db_query = null;
    private Button button_db_update = null;
    private Button button_db_delete = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_layout);

        button_db_insert = findViewById(R.id.button_db_Insert);
        button_db_insert.setOnClickListener(this);
        button_db_query = findViewById(R.id.button_db_query);
        button_db_query.setOnClickListener(this);
        button_db_update = findViewById(R.id.button_db_update);
        button_db_update.setOnClickListener(this);
        button_db_delete = findViewById(R.id.button_db_delete);
        button_db_delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_db_Insert:
                Uri uri_insert = Uri.parse(MyContentProvider.Uri_Book);
                ContentValues values = new ContentValues();
                values.put(MyContentProvider.Book_column_name, "Little Prince");
                values.put(MyContentProvider.Book_column_Price, 10.1);
                values.put(MyContentProvider.Book_column_author, "XXXX");
                values.put(MyContentProvider.Book_column_pages, 352);
                this.getContentResolver().insert(uri_insert, values);
                values.put(MyContentProvider.Book_column_name, "Little Prince-II");
                values.put(MyContentProvider.Book_column_Price, 10.1);
                values.put(MyContentProvider.Book_column_author, "XXXX");
                values.put(MyContentProvider.Book_column_pages, 360);
                getContentResolver().insert(uri_insert, values);
                Log.d(TAG, "onClick: ---->insert is called");
                break;
            case R.id.button_db_query:
                Log.d(TAG, "onClick: ---> query is called");
                Cursor cursor = getContentResolver().query(Uri.parse(MyContentProvider.Uri_Book),
                        null, " name = ?",new String[]{"Little Prince"},null);
                if(cursor != null && cursor.getCount() != 0){
                    cursor.moveToFirst();
                    do{
                        Log.d(TAG, "onClick: -->Query: " + cursor.getString(cursor.getColumnIndex(MyContentProvider.Book_column_name)));
                        Log.d(TAG, "onClick: -->Query: " + cursor.getInt(cursor.getColumnIndex(MyContentProvider.Book_column_pages)));
                    }while (cursor.moveToNext());
                }else{
                    Log.d(TAG, "onClick: ---> no data found!");
                }
                break;
            case R.id.button_db_update:
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyContentProvider.Book_column_pages, 400);
                getContentResolver().update(Uri.parse(MyContentProvider.Uri_Book),contentValues,MyContentProvider.Book_column_name +" =?", new String[]{"Little Prince"});
                Log.d(TAG, "onClick: ---> update is called");
                break;
            case R.id.button_db_delete:
                Log.d(TAG, "onClick: --->delete is called!");
                int rows = getContentResolver().delete(Uri.parse(MyContentProvider.Uri_Book), MyContentProvider.Book_column_name + "=?", new String[]{"Little Prince"});
                Log.d(TAG, "onClick: --->delete rows!" + rows);
                break;
            default:
                break;
        }
    }
}