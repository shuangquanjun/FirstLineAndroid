package com.example.mypersiststorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Permission;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button button_file = null;
    private Button button_file_store = null;
    private Button button_file_read = null;

    private Button button_sp = null;
    private Button button_sp_store = null;
    private Button button_sp_read = null;

    private Button button_db_create = null;
    private Button button_db_store = null;
    private Button button_DB_query = null;

    private Button button_make_call = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_buttons_file();
        init_button_sp();
        init_button_db();

        init_button_makecall();
    }

    private final int request_call_code = 1;
    private void init_button_makecall() {
        button_make_call = findViewById(R.id.button_makecall);
        button_make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    calling();
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE},request_call_code);
                    //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_call_code);
                }
            }
        });
    }

    private void calling() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);//will cause permission denialed!
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case request_call_code:
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED&&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    calling();
                }else{
                    Toast.makeText(this, "Cant not make call", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }

    private void init_button_db() {
        button_db_create = findViewById(R.id.button_db_create);
        button_db_create.setOnClickListener(this);
        button_db_store = findViewById(R.id.button_db_store);
        button_db_store.setOnClickListener(this);
        button_DB_query = findViewById(R.id.button_db_query);
        button_DB_query.setOnClickListener(this);
    }

    private void init_button_sp() {
        button_sp = findViewById(R.id.button_sp);
        button_sp.setOnClickListener(this);
        button_sp_store = findViewById(R.id.button_sp_store);
        button_sp_store.setOnClickListener(this);
        button_sp_read = findViewById(R.id.button_sp_read);
        button_sp_read.setOnClickListener(this);
    }

    private void init_buttons_file() {
        button_file = findViewById(R.id.button_file);
        button_file.setOnClickListener(this);
        button_file_store = findViewById(R.id.button_file_store);
        button_file_store.setOnClickListener(this);
        button_file_read = findViewById(R.id.button_file_read);
        button_file_read.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String fileName = "StoredFiles";
        switch (v.getId()){
        //file realted operations
            case R.id.button_file:
                //place to create the file
                FileOutputStream fileOutputStream = null;
                //If use append, then if exist, will append data,
                // if use MODE_PRIVATE, then will overwrite the data

                // String str_dri = getDataDir().toString();
                // File file = new File(str_dri + "/files/StoredFiles");
                // if(file.exists()){
                //      Toast.makeText(this, "File exists", Toast.LENGTH_SHORT).show();
                //      break;
                // }

                try {
                    fileOutputStream = openFileOutput(fileName, MODE_APPEND);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "File access failed!", Toast.LENGTH_SHORT).show();
                }finally {
                    if(fileOutputStream != null){
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(this, "File Created!", Toast.LENGTH_SHORT).show();

                break;
            case R.id.button_file_store:
                Log.d(TAG, "onClick: ----> file store!");
                FileOutputStream fout = null;
                BufferedWriter writer = null;
                try {
                    //every time we call openFileOutput, will create a new one
                    fout = openFileOutput(fileName, MODE_APPEND);
                    writer = new BufferedWriter(new OutputStreamWriter(fout));
                    writer.write("Test content for file: ABCD" + 1);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(writer != null){
                        try {
                            writer.close();
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
            case R.id.button_file_read:
                FileInputStream fileInputStream = null;
                BufferedReader reader = null;
                String content = null;

                try {
                    fileInputStream = openFileInput(fileName);
                    reader = new BufferedReader(new InputStreamReader(fileInputStream));
                    content = reader.readLine();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
                break;

            //shared preference
            case R.id.button_sp:
                SharedPreferences sp = getSharedPreferences("sp_test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Name", "Richard");
                editor.putInt("Age", 40);
                editor.putBoolean("Man", true);
                editor.commit();
                Toast.makeText(this, "Shared Preference Created", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_sp_store:
                //to see if the value is updated by this operation
                SharedPreferences sp_1 = getSharedPreferences("sp_test", MODE_PRIVATE);
                SharedPreferences.Editor editor_1 = sp_1.edit();
                editor_1.putString("Name", "Leo");
                editor_1.putInt("Age", 32);
                editor_1.putBoolean("Man", false);
                editor_1.commit();
                Toast.makeText(this, "Shared Preference updated", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button_sp_read:
                SharedPreferences sp_read = getSharedPreferences("sp_test", MODE_PRIVATE);
                StringBuffer str_read_build = new StringBuffer();
                String tmp = sp_read.getString("Name", "Error");
                str_read_build.append(tmp + " ");

                int age= sp_read.getInt("Age", -1);
                str_read_build.append(age + " ");

                boolean flag = sp_read.getBoolean("Gender", false);
                str_read_build.append(flag + " ");

                Toast.makeText(this, str_read_build.toString(), Toast.LENGTH_SHORT).show();
                //need to call commit to create the file
                getSharedPreferences("Testing", MODE_PRIVATE).edit().putString("name", "test").commit();

                break;
            case R.id.button_db_create:
                MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(this, "MyDatabase", null, 1);
                myDBOpenHelper.getWritableDatabase(); //when call get, will call helper's oncreate

                break;
            case R.id.button_db_store:
                MyDBOpenHelper myDBOpenHelper_1 = new MyDBOpenHelper(this,"MyDatabase", null, 1);
                SQLiteDatabase sqLiteDatabase = myDBOpenHelper_1.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "Little Prince");
                contentValues.put("author", "AAAA");
                contentValues.put("Price", 10.1);
                contentValues.put("pages", 300);
                sqLiteDatabase.insert("Book",null,contentValues);

                break;
            case R.id.button_db_query:
                MyDBOpenHelper myDBOpenHelper_query = new MyDBOpenHelper(this, "MyDatabase", null,1);
                SQLiteDatabase db = myDBOpenHelper_query.getReadableDatabase();
                Cursor cursor = db.query("Book", null,null,null,null,null,null);
                if(cursor != null){
                    cursor.moveToFirst();
                    do{
                        String book_name = cursor.getString(cursor.getColumnIndex("name"));
                        //Toast.makeText(this, book_name, Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: --> book name is: "+ book_name);
                    }while (cursor.moveToNext());
                }
                cursor.close();

                db.delete("Book", "name = ?", new String[]{"Little Prince"});
                break;

            default:
                break;
        }
    }

}