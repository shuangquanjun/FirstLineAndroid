package com.example.usemycontentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityNetwork extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "ActivityNetwork";
    private Button button_request = null;
    private Button button_threadHandler = null;
    private Button button_asynctask = null;
    private ProgressBar progressBar = null;
    private TextView tx = null;

    private HandlerThread handlerThread = null; //new HandlerThread("Handler-Thread");
    private Handler thread_handler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.network_layout);

        init_UIComponent();
        init_HandlerThread();
    }

    private void init_HandlerThread() {
        handlerThread = new HandlerThread("HandlerThread-1");
        handlerThread.start();
        //this handler will run in handlerThread
        thread_handler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case 1001:
                        Log.d(TAG, "handleMessage: --->thread handler: " + Thread.currentThread().getId());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run: ---> send the runnable!");
                            }
                        }, 1000);
                        break;
                    default:
                        Log.d(TAG, "handleMessage: -->no handler");
                        break;
                }
                //super.handleMessage(msg);
            }
        };
    }

    private void init_UIComponent() {
        button_request = findViewById(R.id.button_url_request);
        button_request.setOnClickListener(this);

        button_threadHandler = findViewById(R.id.button_threadhandler);
        button_threadHandler.setOnClickListener(this);
        tx = findViewById(R.id.tv_url);

        button_asynctask = findViewById(R.id.button_asyncstask);
        button_asynctask.setOnClickListener(this);

        progressBar = findViewById(R.id.progressbar);
    }

    //This handler will run in mainthread
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(ActivityNetwork.this, "finshed!", Toast.LENGTH_SHORT).show();
                    button_request.setText("finished!");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_url_request:
                Log.d(TAG, "onClick: --> request clicked!");

                new Thread(){
                    Response response;
                    String resp_data = null;
                    @Override
                    public void run() {
                        //super.run();
                        OkHttpClient client = new OkHttpClient();
                        try {
                            response = client.newCall(new Request.Builder().url("https://www.baidu.com").build()).execute();
                            resp_data = response.body().string();
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        showResponds(resp_data);
                    }
                }.start();


                break;
            case R.id.button_threadhandler:
                Message message = new Message();
                message.what = 1001;
                thread_handler.sendMessage(message);
                break;

            case R.id.button_asyncstask:
                DownloadAsyncTask task = new DownloadAsyncTask();
                task.execute();
            default:
                break;
        }
    }

    private void showResponds(final String respond){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tx.setText(respond);
            }
        });
    }

    class DownloadAsyncTask extends AsyncTask<Void, Integer, Boolean>{
        int progress = 0;
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute: ---> is called!");
            //super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(progress);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                while(progress <= 100){
                    Thread.sleep(1000);
                    Log.d(TAG, "doInBackground: --->update value");
                    progress +=10;
                    publishProgress(progress + 10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(Integer ...values) {
            Log.d(TAG, "onProgressUpdate: --->set value");
            progressBar.setProgress(values[0]);
            //super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d(TAG, "onPostExecute: ----> called!" + result);
            //super.onPostExecute(result);
        }

    }

}