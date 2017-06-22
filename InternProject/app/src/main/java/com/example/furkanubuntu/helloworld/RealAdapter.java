package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by furkanubuntu on 6/21/17.
 */

public class RealAdapter extends ArrayAdapter<ItemOnSale> {
    public RealAdapter(Context context, ArrayList<ItemOnSale> goods){
        super(context,0,goods);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ItemOnSale anItem = getItem(position);

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
        productPic.setImageResource(anItem.itemPic);

        return convertView;
    }
}
