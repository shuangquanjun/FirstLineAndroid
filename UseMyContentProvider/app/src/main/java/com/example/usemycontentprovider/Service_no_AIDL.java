package com.example.usemycontentprovider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Service_no_AIDL extends Service {
    private static final String TAG = "Service_no_AIDL";
    Thread thread = null;
    private static int count = 0;
    
    public Service_no_AIDL() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: --->is called");
        super.onCreate();
        count = 0;
        thread = new Thread(){
            @Override
            public void run() {
                super.run();
               while(count <= 10){
                    try {
                        sleep(1000);
                        Log.d(TAG, "run: ---> thread is running " + count++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ----> is called!");
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return new MyLocalBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ---->is called");
        count = 10;
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ---> is called!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ---> is called!");
    }
}
