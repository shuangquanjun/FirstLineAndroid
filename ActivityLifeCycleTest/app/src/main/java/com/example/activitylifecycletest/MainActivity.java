package com.example.activitylifecycletest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_start_main = null;
    private Button button_start_dialog = null;
    private Button button_start_layoutActivity = null;
    private Button button_start_customizedlayout_Activity = null;
    private Button button_start_fragment = null;
    private Button button_start_fragment_right = null;
    private Button button_sendbroadcast = null;

    private EditText editText = null;

    private IntentFilter intentFilter = null;
    private NetworkChangeReceiver networkChangeReceiver = null;

    class NetworkChangeReceiver extends BroadcastReceiver{
        private static final String TAG = "NetworkChangeReceiver";
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: --->broadcast received!" + intent.getExtras());

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ---->is called!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_start_main = findViewById(R.id.button_start_normal_activity);
        button_start_dialog = findViewById(R.id.button_start_dialog_activity);
        button_start_layoutActivity = findViewById(R.id.button_start_layout_activity);
        button_start_customizedlayout_Activity = findViewById(R.id.button_start_customizedlayout_activity);
        button_start_fragment = findViewById(R.id.button_start_fragment);
        button_start_fragment_right = findViewById(R.id.button_start_fragment_right);


        editText = findViewById(R.id.edittext_1);

        button_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the normal activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NormalActivity.class);
                startActivity(intent);
                //finish();//want to test onsave
            }
        });
        button_start_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start the dialog activity
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });
        button_start_layoutActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("LayoutActivity");
                startActivity(intent);
            }
        });
        button_start_customizedlayout_Activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, CustomizedActivity.class);
                startActivity(intent);
            }
        });

        button_start_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, ActivityFragement.class);
                startActivity(intent);
            }
        });

        button_start_fragment_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, ActivityFragement.class);
                intent.putExtra("Extra", "Change Right");
                startActivity(intent);

            }
        });
        button_sendbroadcast = findViewById(R.id.button_sendbroadcast);
        button_sendbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent("com.example.activitylifecycletest.myboradcast");
                myIntent.putExtra("Extra", "Hello boradcst!");
                Log.d(TAG, "onClick: --->broadcast sent!");
                myIntent.setPackage("com.example.activitylifecycletest"); //静态注册的广播，需要加package，才能的到
                //sendBroadcast(myIntent);
                sendOrderedBroadcast(myIntent, null);
            }
        });

        if(savedInstanceState != null){
            String tmp_data = savedInstanceState.getString("tmp_data");
            if(tmp_data != null){
                editText.setText(tmp_data);
            }
        }

        register_boradcast_receiver();
    }

    private void register_boradcast_receiver() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //intentFilter.addAction("com.example.activitylifecycletest.myboradcast");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ---->is called!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ---->is called!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ---->is called!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ---->is called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ---->is called!");
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ---->is called!");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String tmp_data = editText.getText().toString();
        if(tmp_data != null){
            Log.d(TAG, "onSaveInstanceState: save edittext data!");
            outState.putString("tmp_data", tmp_data);
        }


    }
}