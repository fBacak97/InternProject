package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/28/17.
 */

// In this case, the fragment displays simple text based on the page
public class TabFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ArrayList<DrawerItem> userTabSelectionItems;
    ArrayList<JsonItemOnSale> wishlistTabSelectionItems;
    DrawerAdapter userTabAdapter;
    ListView wishlistTabListView;
    ListView userTabListView;
    WishlistAdapter wishlistTabAdapter;
    View view;
    Button button;
    WishlistTabAdapter adapter;

    private int mPage;

    public static TabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        userTabSelectionItems = new ArrayList<>();
        userTabSelectionItems.add(new DrawerItem(" My Profile", R.drawable.account));
        userTabSelectionItems.add(new DrawerItem(" Notifications", R.drawable.notifications));
        userTabSelectionItems.add(new DrawerItem(" My Orders", R.drawable.cart));
        userTabSelectionItems.add(new DrawerItem(" Wishlist", R.drawable.favorite));
        userTabSelectionItems.add(new DrawerItem(" Location", R.drawable.userlocation));

        wishlistTabSelectionItems = new ArrayList<>();
        wishlistTabSelectionItems.add(new JsonItemOnSale("a","a","abc","http://cdn3.volusion.com/jebff.evopn/v/vspfiles/photos/510-2.jpg?1457531829","books+audiobooks"));
        //wishlistTabSelectionItems.add(new JsonItemOnSale("a","a","abc2","http://cdn3.volusion.com/jebff.evopn/v/vspfiles/photos/510-2.jpg?1457531829"));
    }

    public void selectUserTabItem(int position){
        switch(position){
            case 0:
                TextView abc = (TextView) view.findViewById(R.id.userName);
                abc.setText("\n\naa");
                break;
            default:
                break;
        }
    }

    public void selectWishlistTabItem(int position){
        Intent intent = new Intent(getActivity(),ProductActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,wishlistTabSelectionItems.get(position).jsonLink);
        intent.putExtra(Intent.EXTRA_TITLE,wishlistTabSelectionItems.get(position).department);
        intent.putExtra(Intent.EXTRA_SUBJECT,wishlistTabSelectionItems.get(position).description);
        getActivity().startActivity(intent);
    }

    private class UserTabItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mPage == 2) {
                selectUserTabItem(position);
            }
            else if (mPage == 1){
                selectWishlistTabItem(position);
            }
            else
            {
                //abc
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mPage == 2) {
            view = inflater.inflate(R.layout.account_tab_fragment, container, false);
            userTabAdapter = new DrawerAdapter(view.getContext(), userTabSelectionItems);
            userTabListView = (ListView) view.findViewById(R.id.accountTabSelections);
            userTabListView.setAdapter(userTabAdapter);
            userTabListView.setOnItemClickListener(new UserTabItemClickListener());
        }
        else if (mPage == 1){
            view = inflater.inflate(R.layout.wishlist_tab_fragment,container,false);
            wishlistTabAdapter = new WishlistAdapter(view.getContext(),wishlistTabSelectionItems);
            wishlistTabListView = (ListView) view.findViewById(R.id.wishlistTabSelections);
            adapter = new WishlistTabAdapter();
            wishlistTabListView.setAdapter(adapter);
            //wishlistTabListView.setAdapter(wishlistTabAdapter);
            wishlistTabListView.setOnItemClickListener(new UserTabItemClickListener());
        }
        return view;
    }

    class WishlistTabAdapter implements ListAdapter {

        JsonItemOnSale itemOnSale;

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            MainActivity main = (MainActivity) getActivity();
            DbHelper helperInstance = main.getInstance();
            itemOnSale = helperInstance.readWishlist(position);
            return itemOnSale;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item, parent, false);
            }
            TextView description = (TextView) convertView.findViewById(R.id.wishlistItemName);
            ImageView productPic = (ImageView) convertView.findViewById(R.id.wishlistPic);
            Picasso.with(convertView.getContext()).load(itemOnSale.jsonLink).fit().into(productPic);
            description.setText(itemOnSale.description);

            return convertView;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int i) {
            return false;
        }

    }

        class WishlistAdapter extends ArrayAdapter<JsonItemOnSale> {
        WishlistAdapter(@NonNull Context context, ArrayList<JsonItemOnSale> favorites) {
            super(context, 0, favorites);
        }

        @NonNull
        public View getView(int position, View convertView, ViewGroup parent)
        {

            //JsonItemOnSale anItem = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item,parent,false);
            }

            TextView description = (TextView) convertView.findViewById(R.id.wishlistItemName);
            ImageView productPic = (ImageView) convertView.findViewById(R.id.wishlistPic);
            MainActivity main = (MainActivity) getActivity();
            DbHelper helperInstance = main.getInstance();
            JsonItemOnSale item = helperInstance.readWishlist(position);
            Picasso.with(convertView.getContext()).load(item.jsonLink).fit().into(productPic);
            description.setText(item.description);

            return convertView;
        }
    }
}
