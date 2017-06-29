package com.example.furkanubuntu.helloworld;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by furkanubuntu on 6/21/17.
 */

public class SecondActivity extends Activity {



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);

        ArrayList<ItemOnSale> arrayOfGoods = new ArrayList<ItemOnSale>();

        arrayOfGoods.add(new ItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", R.drawable.index));
        arrayOfGoods.add(new ItemOnSale("%45", "250$", "Samsung Note 3 Only Bluetooth is damaged", R.drawable.inde3x));
        arrayOfGoods.add(new ItemOnSale("%20", "200$", "Xperia Z4 Second Hand", R.drawable.sonyxperiaz4));

        RealAdapter adapter = new RealAdapter(this, arrayOfGoods);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        //Part2 Apple
        //ArrayList<AnItem> arrayOfItems = new ArrayList<AnItem>();
        //for(int i = 0; i<4;i++) {
        //    arrayOfItems.add(new AnItem("abc", "aaa", R.drawable.apple_ex));
        //}
        //UserAdapter adapter = new UserAdapter(this, arrayOfItems);
        //ListView listView = (ListView) findViewById(R.id.listview);
        //listView.setAdapter(adapter);

        //Part1 Trying first
        //ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_view, randomArray);
        //ListView listView = (ListView) findViewById(R.id.listview);
        //listView.setAdapter(adapter);

    }
}