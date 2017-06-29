package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/29/17.
 */

public class itemlistFragment extends Fragment {

    RealAdapter adapter;
    ArrayList<ItemOnSale> arrayOfGoods;
    ListView listView;
    View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayOfGoods = new ArrayList<ItemOnSale>();

        arrayOfGoods.add(new ItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", R.drawable.index));
        arrayOfGoods.add(new ItemOnSale("%45", "250$", "Samsung Note 3 Only Bluetooth is damaged", R.drawable.inde3x));
        arrayOfGoods.add(new ItemOnSale("%20", "200$", "Xperia Z4 Second Hand", R.drawable.sonyxperiaz4));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_listview,container, false);

        adapter = new RealAdapter(getActivity(), arrayOfGoods);
        listView = (ListView) view.findViewById(R.id.item_listview);
        listView.setAdapter(adapter);
        return view;
    }


}