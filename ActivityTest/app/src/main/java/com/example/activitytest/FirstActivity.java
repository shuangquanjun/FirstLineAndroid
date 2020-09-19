package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    private static final String TAG = "FirstActivity";
    private Button button_1 = null;
    private Button button_start_second = null;
    private Button button_3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "You clicked button_1", Toast.LENGTH_SHORT).show();

                //todo: need to know the constructors of intent.
                Intent intent = new Intent();
                intent.setClass(FirstActivity.this, SecondActivity.class);
                intent.putExtra("extra_data", "Hello, SecondActivity!");
                startActivity(intent);
            }
        });
        button_start_second = findViewById(R.id.button_start_second);
        button_start_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button_start_second is clicked!");
                Intent intent = new Intent();
                // if we didnot add category, then intent constructor will add default
                intent.setAction("com.example.activitytest.ACTION_START");
                intent.addCategory("com.example.activitytest.myCategory");
                //intent.addCategory("com.example.activitytest.my");//when start activity cannot find category, it will crash
                //startActivity(intent);

                startActivityForResult(intent, 1 /*magic code for testing*/);
            }
        });



        button_3  = findViewById(R.id.button_3);
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick:  Button_3 clicked!");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                //chrome and third activity will be on the option
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: -------->here");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String str = data.getStringExtra("ret_value");
                Log.d(TAG, "onActivityResult: message is: " + str);
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked remove!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh_item:
                Toast.makeText(this, "You clicked refresh!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }
}