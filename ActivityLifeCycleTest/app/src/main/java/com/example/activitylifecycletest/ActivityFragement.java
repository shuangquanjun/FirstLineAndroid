package com.example.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ActivityFragement extends AppCompatActivity {
    private static final String TAG = "ActivityFragement";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String str_value = intent.getStringExtra("Extra");
        if(str_value != null &&str_value.equals("Change Right")){
            setContentView(R.layout.another_fragement_layout); //first forget this, can cause crash.
            Log.d(TAG, "onCreate: -->start ");
            AnotherRightFragment anotherRightFragment = new AnotherRightFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            //replace another_fragment_layout's right part
            transaction.replace(R.id.another_fragement_right_framelayout, anotherRightFragment);
            transaction.commit();

        }else{
            setContentView(R.layout.activity_fragement_layout);
            RightFragment rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.right_fragement);
            rightFragment.rightFragement_interface();
            rightFragment.onDestroy();
        }
    }
}