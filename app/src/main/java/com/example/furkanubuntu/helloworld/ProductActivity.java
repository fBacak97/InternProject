package com.example.furkanubuntu.helloworld;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by furkanubuntu on 7/4/17.
 */

public class ProductActivity extends AppCompatActivity {


    ListView drawerListView;
    DrawerLayout productDrawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    FragmentManager fragmentManager;
    DrawerAdapter drawerAdapter;
    ArrayList<DrawerItem> drawerItemList;
    ArrayList<DrawerItem> departmentsList;
    String currentDrawerContent = "departmentsTab";
    TextView header;
    TextView description;
    imageScrollAdapter adapter;
    String apiKey = "AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A"; //"AIzaSyBianBdkjLEijeQL3T0RTMgTDd9ydL8J7Y";  //"AIzaSyAwL2u9ByNL9coBouyJBjtx3UXmb_mtC50"; //"AIzaSyCFrT2Vp7pqSBbTecdlzO_bpNkj52iZ04Y";
    String cx = "000741119430587044101:2fdfbkejafg";
    int randomNo;
    int userID;
    String start;
    String fileType = "jpg";
    String searchType = "image";
    String searchCriteria;
    String combinedUrl;
    ArrayList<String> linkArray;
    ViewPager viewPager;
    TabLayout swipeDots;
    DbHelper helperInstance;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        swipeDots = (TabLayout) findViewById(R.id.swipeDots);
        viewPager = (ViewPager) findViewById(R.id.productSlider);
        header = (TextView) findViewById(R.id.header);
        description = (TextView) findViewById(R.id.product_infoText);
        productDrawerLayout = (DrawerLayout) findViewById(R.id.product_drawer_layout);
        drawerItemList = new ArrayList<>();
        departmentsList = new ArrayList<>();
        linkArray = new ArrayList<>();
        Random randomGen = new Random();
        randomNo = randomGen.nextInt(21) + 5;
        start = "" + randomNo;
        Button addFavButton = (Button) findViewById(R.id.productFavButton);
        Button addCartButton = (Button) findViewById(R.id.productBuyButton);

        helperInstance = new DbHelper(this);

        drawerItemList.add(new DrawerItem(" Home",R.drawable.home));
        drawerItemList.add(new DrawerItem(" Departments",R.drawable.store));
        drawerItemList.add(new DrawerItem(" Fire & Kindle",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Prime",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Your Orders",R.drawable.cart));
        drawerItemList.add(new DrawerItem(" Account",R.drawable.account));
        drawerItemList.add(new DrawerItem(" Wishlist",R.drawable.favorite));
        drawerItemList.add(new DrawerItem(" Today's Deals",R.drawable.money));
        drawerItemList.add(new DrawerItem(" Recommendations",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Gift Cards",R.drawable.giftcard));
        drawerItemList.add(new DrawerItem(" Subscribe & Save",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Change Country",R.drawable.userlocation));
        drawerItemList.add(new DrawerItem(" Recently Viewed Items",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Contact Us",R.drawable.temp));

        departmentsList.add(new DrawerItem(" Back",R.drawable.back));
        departmentsList.add(new DrawerItem(" Books & Audiobooks",R.drawable.book));
        departmentsList.add(new DrawerItem(" Movies & TV",R.drawable.movies));
        departmentsList.add(new DrawerItem(" Music",R.drawable.music));
        departmentsList.add(new DrawerItem(" Apps & Video Games",R.drawable.game));
        departmentsList.add(new DrawerItem(" Computers & Office",R.drawable.laptop));
        departmentsList.add(new DrawerItem(" Electronics",R.drawable.electronics));
        departmentsList.add(new DrawerItem(" Home, Garden & Tools",R.drawable.outdoors));
        departmentsList.add(new DrawerItem(" Grocery & Beverage",R.drawable.temp));
        departmentsList.add(new DrawerItem(" Health & Beauty",R.drawable.temp));
        departmentsList.add(new DrawerItem(" Toys, Kids & Baby",R.drawable.children));
        departmentsList.add(new DrawerItem(" Clothing & Shoes",R.drawable.temp));
        departmentsList.add(new DrawerItem(" Sports & Outdoors",R.drawable.sports));

        drawerAdapter = new DrawerAdapter(this,departmentsList);
        drawerListView = (ListView) findViewById(R.id.secondDrawerListView);
        drawerListView.setAdapter(drawerAdapter);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View headerView = inflater.inflate(R.layout.drawer_header, null,false);

        swipeDots.setupWithViewPager(viewPager, true);

        drawerListView.addHeaderView(headerView);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
        productDrawerLayout.addDrawerListener(mDrawerToggle);

        fragmentManager = getFragmentManager();

        intent = getIntent();
        linkArray.add(intent.getStringExtra(Intent.EXTRA_TEXT));
        searchCriteria = intent.getStringExtra(Intent.EXTRA_TITLE);
        description.setText(intent.getStringExtra(Intent.EXTRA_SUBJECT));
        header.setText(intent.getStringExtra(Intent.EXTRA_SUBJECT));
        userID = intent.getIntExtra(Intent.EXTRA_TEMPLATE, 0);
        combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                + "&searchType=" + searchType + "&start=" + start + "&fileType=" + fileType + "&alt=json";
        UniqueASyncTask aSyncTask = new UniqueASyncTask(combinedUrl);
        aSyncTask.execute();

        addFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wishlistContainChecker(userID)) {
                    helperInstance.removeWishlist(linkArray.get(0),userID);
                }
                else{
                    helperInstance.addWishlist(linkArray.get(0),description.getText().toString(),intent.getStringExtra("Department"),userID);
                }
            }
        });
    }

    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_no_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this,productDrawerLayout,toolbar,R.string.drawerTitle, R.string.drawerTitle){

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if(currentDrawerContent.equals("mainPage")) {
                    drawerAdapter = new DrawerAdapter(getBaseContext(), drawerItemList);
                    drawerListView.setAdapter(drawerAdapter);
                }
                else if (currentDrawerContent.equals("departmentsTab")){
                    drawerAdapter = new DrawerAdapter(getBaseContext(), departmentsList);
                    drawerListView.setAdapter(drawerAdapter);
                }
            }

            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        //This is necessary to change the icon of the Drawer Toggle upon state change. Not necessary in my app atm!!.
        mDrawerToggle.syncState();
    }

    public boolean wishlistContainChecker(int userID){
        ArrayList<JsonItemOnSale> list = helperInstance.readWishlist(userID);
        for(int i = 0; i < list.size(); i++){
            if(intent.getStringExtra(Intent.EXTRA_TEXT).equals(list.get(i).jsonLink))
                return true;
        }
      return false;
    }

    public boolean cartContainChecker(int userID){
        ArrayList<JsonItemOnSale> list = helperInstance.readCart(userID);
        for(int i = 0; i < list.size(); i++){
            if(intent.getStringExtra(Intent.EXTRA_TEXT).equals(list.get(i).jsonLink))
                return true;
        }
        return false;
    }

    public void selectDrawerItem(int position) {
        if(currentDrawerContent.equals("departmentsTab")) {
            if(position == 1){
                previousDrawerView();
            }
            else {
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("buttonNo",position);
                intent.putExtra(Intent.EXTRA_TITLE, userID);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else if(currentDrawerContent.equals("mainPage")) {
            switch(position){
                case 1:
                    previousDrawerView();
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra(Intent.EXTRA_TITLE, userID);
                    startActivity(intent);
                    break;
                case 2:
                    drawerAdapter = new DrawerAdapter(this, departmentsList);
                    drawerListView.setAdapter(drawerAdapter);
                    currentDrawerContent = "departmentsTab";
                    break;
                default:
                    break;
            }
        }
    }

    public void previousDrawerView(){
        if (currentDrawerContent.equals("mainPage")) {
            productDrawerLayout.closeDrawers();
        }
        else if (currentDrawerContent.equals("departmentsTab")) {
            drawerAdapter = new DrawerAdapter(this, drawerItemList);
            drawerListView.setAdapter(drawerAdapter);
            currentDrawerContent = "mainPage";
        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

    private class UniqueASyncTask extends AsyncTask {
        String urlString;

        private UniqueASyncTask(String urlString) {
            super();
            this.urlString = urlString;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            StringBuilder builder = null;
            JSONArray jsonArray;

            if(urlString.contains(" ")){
                        urlString = urlString.replaceAll("\\s", "");
            }

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

                e.printStackTrace();
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
                    for (int i = 0; i < 3; i++) {
                        linkArray.add(jsonArray.getJSONObject(i).getString("link"));
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

            adapter = new imageScrollAdapter(linkArray,getBaseContext());
            viewPager.setAdapter(adapter);
            if (o == null) {
                CharSequence text = "Returned null";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getBaseContext(), text, duration);
                toast.show();
            }
            adapter.notifyDataSetChanged();
        }
    }

}
