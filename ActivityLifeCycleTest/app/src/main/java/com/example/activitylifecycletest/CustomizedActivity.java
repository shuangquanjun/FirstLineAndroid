package com.example.activitylifecycletest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CustomizedActivity extends AppCompatActivity {

    private static final String TAG = "CustomizedActivity";
    private ListView listView = null;

    private String[] array  = {"Red", "Orange", "Blue", "Sky"};
    private List<Fruit> fruitList = new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customized_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        TitleLayout titleLayout = findViewById(R.id.TitleLayout);
        listView = titleLayout.listView;
        //android.R.layout.simple_list_item_1 is a built-in layout
        //simple_textview_adapter();// Try another one

        for(int i = 0; i < array.length; i++){
            Fruit fruit = new Fruit(array[i], 0);
            fruitList.add(fruit);
        }

        FruitAdapter fruitAdapter = new FruitAdapter(this, R.layout.fruit_item_layout, fruitList);
        listView.setAdapter(fruitAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CustomizedActivity.this, "Position is " + fruitList.get(position).getFruit_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void simple_textview_adapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,array);
        listView.setAdapter(arrayAdapter);

    }


}