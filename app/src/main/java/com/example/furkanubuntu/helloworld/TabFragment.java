package com.example.furkanubuntu.helloworld;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 6/28/17.
 */


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

        MainActivity main = (MainActivity) getActivity();
        DbHelper helperInstance = main.getInstance();
        wishlistTabSelectionItems = helperInstance.readWishlist(main.userID);
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

    class WishlistAdapter extends ArrayAdapter<JsonItemOnSale> {
        WishlistAdapter(@NonNull Context context, ArrayList<JsonItemOnSale> favorites) {
            super(context, 0, favorites);
        }

        @NonNull
        public View getView(final int position, View convertView, ViewGroup parent)
        {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.wishlist_item,parent,false);
            }

            TextView description = (TextView) convertView.findViewById(R.id.wishlistItemName);
            ImageView productPic = (ImageView) convertView.findViewById(R.id.wishlistPic);
            Button button = (Button) convertView.findViewById(R.id.removeFavButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice){
                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;

                                case DialogInterface.BUTTON_POSITIVE:
                                    MainActivity main = (MainActivity) getActivity();
                                    DbHelper helperInstance = main.getInstance();
                                    helperInstance.removeWishlist(wishlistTabSelectionItems.get(position).jsonLink,main.userID);
                                    wishlistTabSelectionItems.remove(position);
                                    wishlistTabAdapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to remove this item?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });


            Picasso.with(convertView.getContext()).load(wishlistTabSelectionItems.get(position).jsonLink).fit().into(productPic);
            description.setText(wishlistTabSelectionItems.get(position).description);

            return convertView;
        }
    }
}
