package com.example.myremoteserver;

import android.os.RemoteException;
import android.system.Os;
import android.util.Log;

import androidx.annotation.NonNull;

class MyBinder extends IMyAIDL.Stub {
    private static final String TAG = "MyBinder";
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
        Log.d(TAG, "basicTypes: ----> is called!");
    }

    @Override
    public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
        return super.unlinkToDeath(recipient, flags);
    }

    @Override
    public int getCount() throws RemoteException {
        Log.d(TAG, "getCount: --> is called ");
        return Os.getpid();
    }
}
