package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/23/17.
 */

class DrawerAdapter extends ArrayAdapter<DrawerItem> {

    DrawerAdapter(Context context, ArrayList<DrawerItem> choices){
        super(context,0,choices);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DrawerItem drawerItem = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item,parent,false);
        }

        TextView choiceText = (TextView) convertView.findViewById(R.id.drawer_itemName);
        ImageView choiceIcon = (ImageView) convertView.findViewById(R.id.drawer_icon);

        choiceText.setText(drawerItem.choice);
        choiceIcon.setImageResource(drawerItem.choiceIcon);

        return convertView;
    }
}
