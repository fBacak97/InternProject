package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.database.Cursor;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item,parent,false);
        }

        TextView description = (TextView) convertView.findViewById(R.id.wishlistItemName);
        ImageView productPic = (ImageView) convertView.findViewById(R.id.wishlistPic);

        DbHelper helperInstance = new DbHelper(getContext());
        JsonItemOnSale item = helperInstance.readWishlist(position);
        Picasso.with(convertView.getContext()).load(item.jsonLink).fit().into(productPic);
        description.setText(item.description);

        return convertView;
    }
}
