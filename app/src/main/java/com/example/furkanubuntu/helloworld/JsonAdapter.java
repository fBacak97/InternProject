package com.example.furkanubuntu.helloworld;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/30/17.
 */

public class JsonAdapter extends ArrayAdapter<JsonItemOnSale> {
    public JsonAdapter(Context context, ArrayList<JsonItemOnSale> goods){
        super(context,0,goods);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        JsonItemOnSale anItem = getItem(position);
        //itemlistFragment itemlistFragment = new itemlistFragment();

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hwlayout,parent,false);
        }

        TextView discountAmount = (TextView) convertView.findViewById(R.id.discountText);
        TextView price = (TextView) convertView.findViewById(R.id.priceText);
        TextView description = (TextView) convertView.findViewById(R.id.infoText);
        ImageView productPic = (ImageView) convertView.findViewById(R.id.productPic);

        discountAmount.setText(anItem.discount);
        price.setText(anItem.price);
        description.setText(anItem.description);
        Picasso.with(convertView.getContext()).load(anItem.jsonLink).into(productPic);

        //productPic.setImageResource(anItem.jsonLink);

        return convertView;
    }
}