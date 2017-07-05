package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by furkanubuntu on 7/4/17.
 */

public class imageScrollAdapter extends PagerAdapter {
    String url;
    Context context;
    final int PIC_COUNT = 3;

    public imageScrollAdapter(String url,Context context){
        super();
        this.url = url;
        this.context = context;
    }

    @Override
    public int getCount() {
        return PIC_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        Picasso.with(context).load(url).into(imageView);

        ((ViewPager) container).addView(imageView, 0);

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
