package com.example.activitylifecycletest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener , DialogInterface.OnClickListener{
    private static final String TAG = "NormalActivity";
    private Button button_start_progress_dialog = null;
    private Button button_start_alert_dialog = null;
    private boolean loading_finish = false;
    private ProgressDialog dialog= null;
    private AlertDialog.Builder alertDialog = null;


    private void init_View(){
        button_start_progress_dialog = findViewById(R.id.button_start_progress_dialog);
        button_start_progress_dialog.setOnClickListener(this);
        loading_finish = false;
        button_start_alert_dialog = findViewById(R.id.button_start_alertDialog);
        button_start_alert_dialog.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
        init_View();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_start_progress_dialog:
                dialog = new ProgressDialog(this);
                dialog.setCancelable(false);
                dialog.setTitle("Progress Dialog Test!");
                dialog.setMessage("Loading......");
                dialog.show();
                Log.d(TAG, "onClick: + -->current tid is: " + Thread.currentThread().getId());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 10; i++){
                            Log.d(TAG, "run: in thread " + Thread.currentThread().getId() + " count:  "+ i);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        loading_finish = true;
                        dialog.dismiss(); //Dialog的Dismiss方法可以在主线程和子线程调用
                    }
                }).start();

                break;
            case R.id.button_start_alertDialog:
                alertDialog = new AlertDialog.Builder(NormalActivity.this);
                alertDialog.setTitle("AlertDialog!");
                alertDialog.setPositiveButton("OK", this);
                alertDialog.setNegativeButton("Cancel", this);
                alertDialog.setPositiveButton("Ignore", this);
                alertDialog.show();

            default:
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Log.d(TAG, "onClick: --->alertDialog " + which);
        switch (which){
            default:
                break;
        }

    }
}