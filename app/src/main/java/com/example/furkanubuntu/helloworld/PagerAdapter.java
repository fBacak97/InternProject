package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by furkanubuntu on 6/28/17.
 */

class PagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Home", "Wishlist", "Account" };
    private Context context;
    String description;
    String link;
    String department;
    Map<Integer, String> mFragmentTags;

    PagerAdapter(FragmentManager fm, Context context, String description, String link, String department) {
        super(fm);
        this.context = context;
        this.description = description;
        this.link = link;
        this.department = department;
        mFragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object obj = super.instantiateItem(container, position);
        if (obj instanceof TabFragment) {
            // record the fragment tag here.
            TabFragment f = (TabFragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
            Log.d("TAGTAG",mFragmentTags.toString());
        }
        return obj;
    }

    @Override
    public TabFragment getItem(int position) {
        TabFragment fragment = TabFragment.newInstance(position);
        if(position == 1 && description.length() > 0)
            fragment.wishlistTabSelectionItems.add(new JsonItemOnSale("Random","Random",description,link,department));

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}