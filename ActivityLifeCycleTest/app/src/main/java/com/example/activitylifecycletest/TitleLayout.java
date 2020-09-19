package com.example.activitylifecycletest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class TitleLayout extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "TitleLayout";
    public ListView listView = null;
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_applestyle,this);
        Button button_back = findViewById(R.id.title_back);
        Button button_edit = findViewById(R.id.title_edit);
        button_back.setOnClickListener(this);
        button_edit.setOnClickListener(this);
        listView = findViewById(R.id.listview_1);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                Log.d(TAG, "onClick: -->title_back is clicked!");
                break;
            case R.id.title_edit:
                Log.d(TAG, "onClick: -->title_edit is clicked!");
                break;
        }
    }
}
