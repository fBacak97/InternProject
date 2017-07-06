package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 7/4/17.
 */

class WishlistAdapter extends ArrayAdapter<JsonItemOnSale> {
    WishlistAdapter(@NonNull Context context, ArrayList<JsonItemOnSale> favorites) {
        super(context, 0, favorites);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent)
    {
        JsonItemOnSale anItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item,parent,false);
        }

        TextView description = (TextView) convertView.findViewById(R.id.wishlistItemName);
        ImageView productPic = (ImageView) convertView.findViewById(R.id.wishlistPic);


        description.setText(anItem.description);
        Picasso.with(convertView.getContext()).load(anItem.jsonLink).resize(100,100).into(productPic);

        return convertView;
    }
}
