package com.example.usemycontentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private String uri_table_book = "content://com.example.mycontentprovider/Book";
    private String table_book_col_name = "name";
    private String table_book_col_author = "author";

    Button button_db_query = null;
    Button button_send_notify = null;
    Button button_start_url = null;
    Button button_start_servcie = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_db_query = findViewById(R.id.button_db_query);
        button_db_query.setOnClickListener(this);

        button_send_notify = findViewById(R.id.button_send_notice);
        button_send_notify.setOnClickListener(this);

        button_start_url = findViewById(R.id.button_start_urlactivity);
        button_start_url.setOnClickListener(this);

        button_start_servcie = findViewById(R.id.button_start_Service);
        button_start_servcie.setOnClickListener(this);
    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_db_query:
                Log.d(TAG, "onClick: ---> query is called!");
                Cursor cursor = getContentResolver().query(Uri.parse(uri_table_book), null, "name = ?", new String[]{"Little Prince"},null);
                if(cursor != null && cursor.getCount() >0 ){
                    cursor.moveToFirst();
                    do {
                        Log.d(TAG, "onClick: -->name:" + cursor.getString(cursor.getColumnIndex(table_book_col_name)));
                        Log.d(TAG, "onClick: --->author: " + cursor.getString(cursor.getColumnIndex(table_book_col_author)));
                    }while (cursor.moveToNext());
                }else{
                    Log.d(TAG, "onClick: --> no data found!");
                }

                break;
            case R.id.button_send_notice:

                Intent intent = new Intent(this, MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0,intent, 0);

                String CHANNEL_ID = "159753";
                String CHANNEL_NAME = "渠道名";
                String description = "渠道说明";
                Log.d(TAG, "onClick: ---> send notification!");
                //Notification notification = new NotificationCompat.Builder(getBaseContext()).build();
                Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
                builder.setTicker("New Message!");
                builder.setContentTitle("Hello Notificaion");
                builder.setContentText("Notification send from Button click!");
                builder.setSmallIcon(R.mipmap.ic_launcher);
                //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
                builder.setWhen(System.currentTimeMillis());
                builder.setContentIntent(pi);
                builder.setAutoCancel( true);
                Notification notification = builder.build();
                NotificationManager manager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                //this CHANNEL_ID must be same with notification
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(description);

                manager.createNotificationChannel(channel);
                manager.notify(1, notification);

                Log.d(TAG, "onClick: end---> send notification!");
                break;
            case R.id.button_start_urlactivity:
                Log.d(TAG, "onClick: ---> start url");
                Intent intent_url = new Intent(this, ActivityNetwork.class);
                startActivity(intent_url);
                break;

            case R.id.button_start_Service:
                /* //Test start activity in other application
                Intent intent_service = new Intent("LayoutActivity");
                intent_service.setPackage(getPackageName());//this one will not take effect
                intent_service.setPackage("com.example.activitylifecycletest");
                startActivity(intent_service);
                */
                Intent intent_service = new Intent("StartServiceActivity");
                intent_service.setPackage(getPackageName());//this one will not take effect
                startActivity(intent_service);

                break;
            default:
                break;
        }

    }
}