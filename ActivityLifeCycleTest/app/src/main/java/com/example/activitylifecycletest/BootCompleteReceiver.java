package com.example.activitylifecycletest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "onReceive: -->");
        String str = intent.getDataString();
        Toast.makeText(context, "Got brocastcast" + intent.getStringExtra("Extra"), Toast.LENGTH_SHORT).show();
    }
}
