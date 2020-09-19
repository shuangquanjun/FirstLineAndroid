package com.example.usemycontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myremoteserver.IMyAIDL;

public class ActivityforService extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ActivityforService";

    private Button button_start_localservcie = null;
    private Button button_bind_localservcie = null;
    private Button button_stop_localservcie = null;
    private Button button_unbind_localservcie = null;

    protected Button button_start_remoteservcie = null;
    private Button button_bind_remoteservcie = null;
    private Button button_stop_remoteservcie = null;
    protected Button button_unbind_remoteservcie = null;
    private Button button_remote_call = null;

    Intent intentservice = null;
    MyLocalBinder myLocalBinder = null;
    ServiceConnection local_servcieconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: --> is called!");
            //if connected, then myLocalBinder is assigned!
            myLocalBinder = (MyLocalBinder)service;
            myLocalBinder.MyLocalBinder_function_1();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ---->is called!");

        }
    };

     IMyAIDL myAIDL = null;

    ServiceConnection remote_serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: -->remote connected");
            myAIDL = IMyAIDL.Stub.asInterface(service);
            try {
                myAIDL.basicTypes((int)1,(long)5,true,(float) 0.0,(double)0.0, "hello");
                int pid = myAIDL.getCount();
                Log.d(TAG, "onServiceConnected: pid = " + pid);
                //button_start_remoteservcie.setText(pid);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: -->remote disconnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_layout);
        button_start_localservcie = findViewById(R.id.button_start_local_servcie);
        button_start_localservcie.setOnClickListener(this);
        button_bind_localservcie = findViewById(R.id.button_bind_local_servcie);
        button_bind_localservcie.setOnClickListener(this);
        button_unbind_localservcie = findViewById(R.id.button_unbind_local_servcie);
        button_unbind_localservcie.setOnClickListener(this);
        button_stop_localservcie = findViewById(R.id.button_stop_local_servcie);
        button_stop_localservcie.setOnClickListener(this);


        button_start_remoteservcie = findViewById(R.id.button_start_remote_servcie);
        button_start_remoteservcie.setOnClickListener(this);
        button_bind_remoteservcie = findViewById(R.id.button_bind_remote_servcie);
        button_bind_remoteservcie.setOnClickListener(this);
        button_unbind_remoteservcie = findViewById(R.id.button_unbind_remote_servcie);
        button_unbind_remoteservcie.setOnClickListener(this);
        button_stop_remoteservcie = findViewById(R.id.button_stop_remote_servcie);
        button_stop_remoteservcie.setOnClickListener(this);

        button_remote_call = findViewById(R.id.button_remote_call);
        button_remote_call.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_local_servcie:
                intentservice = new Intent("Service_no_AIDL");
                intentservice.setPackage(getPackageName());
                startService(intentservice);
                Log.d(TAG, "onClick: ----> start service!");
                break;

            case R.id.button_bind_local_servcie:
                Log.d(TAG, "onClick: ---->bind service!");
                Intent bindIntent = new Intent("Service_no_AIDL");
                bindIntent.setPackage(getPackageName());
                bindService(bindIntent, local_servcieconnection, Context.BIND_AUTO_CREATE);

                break;
            case R.id.button_unbind_local_servcie:
                Log.d(TAG, "onClick: ---> unbind service!");
                if(myLocalBinder != null) {
                    unbindService(local_servcieconnection);
                }

                break;
            case R.id.button_stop_local_servcie:
                if(intentservice != null){
                    Log.d(TAG, "onClick: ---> stop local service!");
                    stopService(intentservice);
                }else{
                    Log.d(TAG, "onClick: ---> no stop local servcie!");
                }
                break;

            case R.id.button_start_remote_servcie:
                Log.d(TAG, "onClick: ---> start remote");
                Intent intent_remote = new Intent("MyService_Server");
                intent_remote.setPackage("com.example.myremoteserver");
                startForegroundService(intent_remote);
                break;
            case R.id.button_bind_remote_servcie:
                Log.d(TAG, "onClick: ---->bind remote");
                Intent intent_remote_bind = new Intent("MyService_Server");
                intent_remote_bind.setPackage("com.example.myremoteserver");
                bindService(intent_remote_bind, remote_serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.button_unbind_remote_servcie:
                Log.d(TAG, "onClick: ---> unbind remote");
                if(myAIDL != null){
                    unbindService(remote_serviceConnection);
                    myAIDL = null;
                }

                break;
            case R.id.button_stop_remote_servcie:
                Log.d(TAG, "onClick: --->close remote");
                Intent intent_remote_stop = new Intent("MyService_Server");
                intent_remote_stop.setPackage("com.example.myremoteserver");
                stopService(intent_remote_stop);
                myAIDL = null;
                break;

            case R.id.button_remote_call:
                if (myAIDL!= null){
                    try {
                        button_remote_call.setText("pid is " + myAIDL.getCount());

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                break;

        }
    }

}