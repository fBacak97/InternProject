package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 7/4/17.
 */

class imageScrollAdapter extends PagerAdapter {
    ArrayList<String> urlArray;
    Context context;
    final int PIC_COUNT = 4;
    ImageView imageView;

    imageScrollAdapter(ArrayList<String> urlArray, Context context){
        super();
        this.urlArray = urlArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return PIC_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        imageView = new ImageView(context);
        String tempUrl = urlArray.get(position);
        Picasso.with(context).load(tempUrl).into(imageView);
        ((ViewPager) container).addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }
}
