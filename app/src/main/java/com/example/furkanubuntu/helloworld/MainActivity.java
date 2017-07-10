package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView drawerListView;
    DrawerLayout drawerLayout;
    Fragment fragment;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    FragmentManager fragmentManager;
    DrawerAdapter drawerAdapter;
    ArrayList<DrawerItem> drawerItemList;
    ArrayList<DrawerItem> departmentsList;
    String currentDrawerContent = "mainPage";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewPager viewPager = (ViewPager)  findViewById(R.id.viewpager);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager(),MainActivity.this));
        searchView = (SearchView) findViewById(R.id.searchBar);
        searchView.setVisibility(View.INVISIBLE);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.favorite);
        tabLayout.getTabAt(2).setIcon(R.drawable.account);

        //Divider copy paste from stackOverflow study carefully!
        View linearRoot = tabLayout.getChildAt(0);
        if (linearRoot instanceof LinearLayout) {
            ((LinearLayout) linearRoot).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.white));
            drawable.setSize(2, 1);
            ((LinearLayout) linearRoot).setDividerPadding(10);
            ((LinearLayout) linearRoot).setDividerDrawable(drawable);
        }


        // ---------- Navigation Drawer ------------
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

        drawerAdapter = new DrawerAdapter(this,drawerItemList);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        drawerListView.setAdapter(drawerAdapter);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View headerView = inflater.inflate(R.layout.drawer_header, null,false);

        drawerListView.addHeaderView(headerView);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());


        //----- Toolbar ---------
        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
        drawerLayout.addDrawerListener(mDrawerToggle);

        fragmentManager = getFragmentManager();

        fragment = new emptyFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.productFragment,fragment).hide(fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        if(getIntent().getIntExtra("buttonNo",0) > 0){
            int buttonNo = (getIntent().getIntExtra("buttonNo",0));
            Log.d("mytag","" + buttonNo);
            fragment = itemlistFragment.newInstance(buttonNo);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.productFragment, fragment).show(fragment);
            fragmentTransaction.commit();
            searchView.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    // Below 2 methods study them carefully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    public void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerTitle, R.string.drawerTitle){

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
                fragment = itemlistFragment.newInstance(position);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.productFragment, fragment).show(fragment);
                fragmentTransaction.commit();
                searchView.setVisibility(View.VISIBLE);
                drawerLayout.closeDrawers();
            }
        }
        else if(currentDrawerContent.equals("mainPage")) {
            switch(position){
                case 1:
                    fragment = new emptyFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.productFragment,fragment);
                    fragmentTransaction.commit();
                    previousDrawerView();
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
            drawerLayout.closeDrawers();
            Log.d("MYTAG","Make invisible");
            searchView.setVisibility(View.INVISIBLE);
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

}
