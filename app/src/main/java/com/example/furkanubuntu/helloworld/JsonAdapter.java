package com.example.furkanubuntu.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/30/17.
 */

class JsonAdapter extends ArrayAdapter<JsonItemOnSale> {
    JsonAdapter(Context context, ArrayList<JsonItemOnSale> goods){
        super(context,0,goods);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

//        final JsonItemOnSale anItem = getItem(position);
//
//        if(convertView == null){
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hwlayout,parent,false);
//        }
//
//        TextView discountAmount = (TextView) convertView.findViewById(R.id.discountText);
//        TextView price = (TextView) convertView.findViewById(R.id.priceText);
//        TextView description = (TextView) convertView.findViewById(R.id.infoText);
//        ImageView productPic = (ImageView) convertView.findViewById(R.id.productPic);
//        Button addFavButton = (Button) convertView.findViewById(R.id.favButton);
//
//        addFavButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DbHelper helperInstance = new DbHelper(getContext());
//                helperInstance.addWishlist(anItem.jsonLink, anItem.description, anItem.department);
//            }
//        });
//
//        discountAmount.setText(anItem.discount);
//        price.setText(anItem.price);
//        description.setText(anItem.description);
//        Picasso.with(convertView.getContext()).load(anItem.jsonLink).into(productPic);
//        //productPic.setAdapter(new imageScrollAdapter(anItem.jsonLink,getContext()));

        return convertView;
    }
}