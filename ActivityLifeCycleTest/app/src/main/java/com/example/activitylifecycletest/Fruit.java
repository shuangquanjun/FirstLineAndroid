package com.example.activitylifecycletest;

public class Fruit {
    private String fruit_name;
    private int fruit_image_id;

    public  Fruit(String name, int id){
        fruit_image_id = id;
        fruit_name = name;
    }

    public  String getFruit_name(){
        return fruit_name;
    }

    public int getFruit_image_id(){
        return fruit_image_id;
    }
}
