package com.example.usemycontentprovider;

import android.os.Binder;
import android.util.Log;

//when using the localservcie, will return this binder back
public class MyLocalBinder extends Binder {
    private static final String TAG = "MyLocalBinder";
    public MyLocalBinder() {
        super();
    }

    public void MyLocalBinder_function_1(){
        Log.d(TAG, "MyLocalBinder_function_1: ----> is called");
    }


}
