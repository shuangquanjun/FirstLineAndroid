package com.example.activitylifecycletest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<Fruit> {
    private int resource_id;

    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        resource_id = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // position是要画的那个view，
        Fruit fruit = getItem(position);
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource_id, parent, false); //why false
        }else{
            view = convertView;
        }
        ImageView imageView = view.findViewById(R.id.fruit_image);
        TextView textView = view.findViewById(R.id.fruit_name);
        imageView.setImageResource(R.mipmap.ic_view);//use nine-pitch
        textView.setText(fruit.getFruit_name());
        return view;

        //return super.getView(position, convertView, parent);
    }
}
