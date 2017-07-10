package com.example.furkanubuntu.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
            wishlistTabListView.setAdapter(wishlistTabAdapter);
            wishlistTabListView.setOnItemClickListener(new UserTabItemClickListener());
        }
        return view;
    }
}