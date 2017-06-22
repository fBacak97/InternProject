package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView drawerListView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> array = new ArrayList<>();

        for(int i = 0; i < 10;i++) {
            array.add("abc");
            array.add("dddd");
            array.add("hello");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.drawer_string_item,array);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        drawerListView.setAdapter(arrayAdapter);
        //drawerListView.setOnItemClickListener(new DrawerOnClick());



        //ArrayList<String> choices = new ArrayList<>();
        //choices.add("Check your favorites");
        //choices.add("Bought items");

        //ArrayAdapter adapter2 = new ArrayAdapter<>(this,R.layout.drawer_string_item,choices);
        //ListView drawerListView = (ListView) findViewById(R.id.drawerListView);
        //drawerListView.setAdapter(adapter2);

    }


    public void nextActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
