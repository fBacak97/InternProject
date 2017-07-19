package com.example.furkanubuntu.helloworld;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by furkanubuntu on 6/28/17.
 */


public class TabFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    ArrayList<DrawerItem> userTabSelectionItems;
    ArrayList<JsonItemOnSale> wishlistTabSelectionItems;
    ArrayList<JsonItemOnSale> tempWishlistItems;
    ArrayList<JsonItemOnSale> homeTabSelectionItems;
    DrawerAdapter userTabAdapter;
    ListView wishlistTabListView;
    ListView userTabListView;
    ListView homeTabListView;
    public WishlistAdapter wishlistTabAdapter;
    HomeTabAdapter homeTabAdapter;
    ProgressBar progressBar = null;
    View view;
    String combinedUrl;
    String wishlistMaxDepartment;
    String searchesMaxDepartment;
    String clicksMaxDepartment;
    String retrieveType = "wishlist";
    HomeTabASyncTask searchASyncTask;
    int scrollCount = 0;
    Random randomGen = new Random();
    int randomNo = randomGen.nextInt(11) + 25;
    String start = "" + randomNo;
    String apiKey = "AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A"; //"AIzaSyBianBdkjLEijeQL3T0RTMgTDd9ydL8J7Y"; //"AIzaSyAwL2u9ByNL9coBouyJBjtx3UXmb_mtC50"; //"AIzaSyCFrT2Vp7pqSBbTecdlzO_bpNkj52iZ04Y"; // ////
    String cx = "000741119430587044101:2fdfbkejafg";
    String fileType = "jpg";
    String searchType = "image";
    String searchCriteria;
    private int mPage;
    DbHelper helperInstance;
    MainActivity main;

    OnFavoritesAdded mCallback;

    public static TabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnFavoritesAdded) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFavButtonClicked");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        userTabSelectionItems = new ArrayList<>();
        userTabSelectionItems.add(new DrawerItem(" My Profile", R.drawable.account));
        userTabSelectionItems.add(new DrawerItem(" My Orders", R.drawable.cart));
        userTabSelectionItems.add(new DrawerItem(" Location", R.drawable.userlocation));

        main = (MainActivity) getActivity();
        helperInstance = main.getInstance();
        wishlistTabSelectionItems = helperInstance.readWishlist(main.userID);

        homeTabSelectionItems = new ArrayList<>();

        tempWishlistItems = helperInstance.readWishlist(main.userID);
        if(tempWishlistItems.size() > 0)
        {
            HashMap<String, Integer> hmap = new HashMap<>();
            for (int i = 0; i < tempWishlistItems.size(); i++) {
                if (hmap.get(tempWishlistItems.get(i).department) == null)
                    hmap.put(tempWishlistItems.get(i).department, 1);
                else
                    hmap.put(tempWishlistItems.get(i).department, hmap.get(tempWishlistItems.get(i).department) + 1);
            }
            int max = 0;
            Set set = hmap.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if ((int) entry.getValue() > max) {
                    max = (int) entry.getValue();
                    wishlistMaxDepartment = (String) entry.getKey();
                }
            }

            searchCriteria = wishlistMaxDepartment;
            combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                    + "&searchType=" + searchType + "&start=" + start + "&fileType=" + fileType + "&alt=json";
            HomeTabASyncTask aSyncTask = new HomeTabASyncTask(combinedUrl);
            aSyncTask.execute();
        }

        homeTabAdapter = new HomeTabAdapter(getActivity(), homeTabSelectionItems);
        wishlistTabAdapter = new WishlistAdapter(getActivity(),wishlistTabSelectionItems);
        userTabAdapter = new DrawerAdapter(getActivity(), userTabSelectionItems);
    }

    public void selectHomeTabItem(int position){
        helperInstance.addClickCount(homeTabSelectionItems.get(position).department,main.userID);
        startProductActivity(position, "Home");
    }

    public void selectUserTabItem(int position){
        switch(position){
            case 1:
                Intent intent1 = new Intent(getActivity(), CartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Arraylist", (Serializable) helperInstance.readCart(main.userID));
                intent1.putExtra(Intent.EXTRA_INTENT, bundle);
                intent1.putExtra(Intent.EXTRA_TEXT, main.userID);
                startActivity(intent1);
                Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }

    public void selectWishlistTabItem(int position){
        startProductActivity(position, "Wishlist");
    }

    protected void startProductActivity(int position, String flag){
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        if(flag.equals("Home")) {
            intent.putExtra(Intent.EXTRA_TEXT, homeTabSelectionItems.get(position).jsonLink);
            intent.putExtra(Intent.EXTRA_TITLE, homeTabSelectionItems.get(position).department);
            intent.putExtra(Intent.EXTRA_SUBJECT, homeTabSelectionItems.get(position).description);
            intent.putExtra(Intent.EXTRA_TEMPLATE, main.userID);
            intent.putExtra("Department", homeTabSelectionItems.get(position).department);
        }
        else if(flag.equals("Wishlist")){
            intent.putExtra(Intent.EXTRA_TEXT, wishlistTabSelectionItems.get(position).jsonLink);
            intent.putExtra(Intent.EXTRA_TITLE, wishlistTabSelectionItems.get(position).department);
            intent.putExtra(Intent.EXTRA_SUBJECT, wishlistTabSelectionItems.get(position).description);
            intent.putExtra(Intent.EXTRA_TEMPLATE, main.userID);
            intent.putExtra("Department", wishlistTabSelectionItems.get(position).department);
        }
        getActivity().startActivity(intent);
    }

    private class TabFragmentsItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mPage == 2) {
                selectUserTabItem(position);
            }
            else if (mPage == 1){
                selectWishlistTabItem(position);
            }
            else if (mPage == 0)
            {
                selectHomeTabItem(position);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mPage == 2) {
            view = inflater.inflate(R.layout.account_tab_fragment, container, false);
            userTabListView = (ListView) view.findViewById(R.id.accountTabSelections);
            userTabListView.setAdapter(userTabAdapter);
            userTabListView.setOnItemClickListener(new TabFragmentsItemClickListener());
        }
        else if (mPage == 1){
            view = inflater.inflate(R.layout.wishlist_tab_fragment,container,false);
            wishlistTabListView = (ListView) view.findViewById(R.id.wishlistTabSelections);
            wishlistTabListView.setAdapter(wishlistTabAdapter);
            wishlistTabListView.setOnItemClickListener(new TabFragmentsItemClickListener());
            wishlistTabAdapter.notifyDataSetChanged();
        }
        else{
            view = inflater.inflate(R.layout.home_tab_fragment,container,false);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBarTwo);
            progressBar.setVisibility(View.GONE);
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), PorterDuff.Mode.SRC_IN );
            homeTabListView = (ListView) view.findViewById(R.id.recommendedItemList);
            homeTabListView.setOnItemClickListener(new TabFragmentsItemClickListener());
            combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                    + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
            retrieveType = "searches";
            searchesMaxDepartment = helperInstance.readMaxSearchCount(main.userID);
            final ArrayList<String> searchList = helperInstance.readSearches(searchesMaxDepartment,main.userID);
            clicksMaxDepartment = helperInstance.readMaxClickCount(main.userID);
            homeTabListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {
                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (searchList != null){
                        if (lastInScreen == totalItemCount && scrollCount < searchList.size())
                        {
                            if (searchASyncTask == null || searchASyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                                searchCriteria = searchesMaxDepartment + searchList.get(scrollCount);
                                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                                progressBar.setVisibility(View.VISIBLE);
                                searchASyncTask = new HomeTabASyncTask(combinedUrl);
                                searchASyncTask.execute();
                                scrollCount++;
                            }
                        }
                        else if(lastInScreen == totalItemCount && scrollCount == searchList.size()){
                            if (searchASyncTask == null || searchASyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                                retrieveType = "clicks";
                                searchCriteria = clicksMaxDepartment;
                                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                                progressBar.setVisibility(View.VISIBLE);
                                searchASyncTask = new HomeTabASyncTask(combinedUrl);
                                searchASyncTask.execute();
                                scrollCount++;
                            }
                        }
                    }
                }
            });
            homeTabListView.setVisibility(View.VISIBLE);
            homeTabListView.setAdapter(homeTabAdapter);
        }
        return view;
    }

    class WishlistAdapter extends ArrayAdapter<JsonItemOnSale> {
        WishlistAdapter(@NonNull Context context, ArrayList<JsonItemOnSale> favorites) {
            super(context, 0, favorites);
        }

        @NonNull
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
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

    class HomeTabAdapter extends ArrayAdapter<JsonItemOnSale> {
        HomeTabAdapter(Context context, ArrayList<JsonItemOnSale> goods){
            super(context,0,goods);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {

            final JsonItemOnSale anItem = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.hwlayout,parent,false);
            }

            TextView discountAmount = (TextView) convertView.findViewById(R.id.discountText);
            TextView price = (TextView) convertView.findViewById(R.id.priceText);
            TextView description = (TextView) convertView.findViewById(R.id.infoText);
            ImageView productPic = (ImageView) convertView.findViewById(R.id.productPic);
            Button addFavButton = (Button) convertView.findViewById(R.id.favButton);
            Button addCartButton = (Button) convertView.findViewById(R.id.buyButton);

            addFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity main = (MainActivity) getActivity();
                    DbHelper helperInstance = main.getInstance();
                    helperInstance.addWishlist(anItem.jsonLink, anItem.description, anItem.department,main.userID);
                    try{
                        mCallback.onFavButtonPressed(anItem.description, anItem.jsonLink, anItem.department);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity main = (MainActivity) getActivity();
                    DbHelper helperInstance = main.getInstance();   userTabSelectionItems.add(new DrawerItem(" Wishlist", R.drawable.favorite));
                    helperInstance.addCart(anItem.jsonLink,anItem.description,anItem.department,main.userID);
                }
            });

            discountAmount.setText(anItem.discount);
            price.setText(anItem.price);
            description.setText(anItem.description);
            Picasso.with(convertView.getContext()).load(anItem.jsonLink).into(productPic);

            return convertView;
        }
    }

    class HomeTabASyncTask extends AsyncTask {
        String urlString;
        int exceptionNo;

        HomeTabASyncTask(String urlString) {
            super();
            this.urlString = urlString;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            StringBuilder builder = null;
            JSONArray jsonArray;
            Log.d("link", urlString);
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != 200) {
                    throw new IOException(connection.getResponseMessage());
                }

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (IOException e) {
                exceptionNo = 1;
            }

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(builder.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObject != null) {
                try {
                    jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < 10; i++) {
                        Log.d("MYTAG", jsonArray.getJSONObject(i).getString("link"));
                        if(retrieveType.equals("wishlist"))
                            homeTabSelectionItems.add(new JsonItemOnSale("%35", "450$", jsonArray.getJSONObject(i).getString("title"),
                                    jsonArray.getJSONObject(i).getString("link"), wishlistMaxDepartment));
                        else if (retrieveType.equals("searches"))
                            homeTabSelectionItems.add(new JsonItemOnSale("%35", "450$", jsonArray.getJSONObject(i).getString("title"),
                                    jsonArray.getJSONObject(i).getString("link"), searchesMaxDepartment));
                        else
                            homeTabSelectionItems.add(new JsonItemOnSale("%35", "450$", jsonArray.getJSONObject(i).getString("title"),
                                    jsonArray.getJSONObject(i).getString("link"), clicksMaxDepartment));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.d("hello", "In post execute");
            homeTabAdapter.notifyDataSetChanged();
            if(progressBar != null)
                progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
