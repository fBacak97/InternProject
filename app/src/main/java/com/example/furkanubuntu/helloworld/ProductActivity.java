package com.example.furkanubuntu.helloworld;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        header = (TextView) findViewById(R.id.header);
        description = (TextView) findViewById(R.id.product_infoText);
        productDrawerLayout = (DrawerLayout) findViewById(R.id.product_drawer_layout);
        drawerItemList = new ArrayList<>();
        departmentsList = new ArrayList<>();

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

        drawerListView.addHeaderView(headerView);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
        productDrawerLayout.addDrawerListener(mDrawerToggle);

        fragmentManager = getFragmentManager();

        Intent intent = getIntent();
        header.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        description.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
    }

    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_no_search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this,productDrawerLayout,toolbar,R.string.drawerTitle, R.string.drawerTitle){

            /** Called when a drawer has settled in a completely closed state. */
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

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };
        //This is necessary to change the icon of the Drawer Toggle upon state change. Not necessary in my app atm!!.
        mDrawerToggle.syncState();
    }

    public void selectDrawerItem(int position) {
        if(currentDrawerContent.equals("departmentsTab")) {
            if(position == 1){
                previousDrawerView();
            }
            else {
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("buttonNo",position);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        else if(currentDrawerContent.equals("mainPage")) {
            switch(position){
                case 1:
                    previousDrawerView();
                    startActivity(new Intent(this, MainActivity.class));
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


    class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

}