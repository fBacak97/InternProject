package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ViewPager viewPager = (ViewPager)  findViewById(R.id.viewpager);
        viewPager.setAdapter(new pagerAdapter(getSupportFragmentManager(),MainActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon);


        // ---------- Navigation Drawer ------------
        drawerItemList = new ArrayList<>();
        departmentsList = new ArrayList<>();

        drawerItemList.add(new DrawerItem("\nHome",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nDepartments",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nFire & Kindle",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nPrime",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nYour Orders",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nAccount",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nWishlist",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nToday's Deals",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nRecommendations",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nGift Cards",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nSubscribe & Save",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nChange Country",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nRecently Viewed Items",R.drawable.icon));
        drawerItemList.add(new DrawerItem("\nContact Us",R.drawable.icon));

        departmentsList.add(new DrawerItem("\nBack",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nBooks & Audiobooks",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nMovies & TV",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nMusic",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nApps & Video Games",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nComputers & Office",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nElectronics",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nHome, Garden & Tools",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nGrocery & Beverage",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nHealth & Beauty",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nToys, Kids & Baby",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nClothing & Shoes",R.drawable.icon));
        departmentsList.add(new DrawerItem("\nSports & Outdoors",R.drawable.icon));

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

        //ArrayList<String> choices = new ArrayList<>();
        //choices.add("Check your favorites");
        //choices.add("Bought items");

        //ArrayAdapter adapter2 = new ArrayAdapter<>(this,R.layout.drawer_string_item,choices);
        //ListView drawerListView = (ListView) findViewById(R.id.drawerListView);
        //drawerListView.setAdapter(adapter2);

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
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        //This is necessary to change the icon of the Drawer Toggle upon state change. Not necessary in my app atm!!.
        mDrawerToggle.syncState();
    }

    public void nextActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void selectDrawerItem(int position) {
        if(currentDrawerContent.equals("departmentsTab")) {
            switch (position){
                case 1:
                    previousDrawerView();
                    break;
                case 2:
                    fragment = new itemFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.productFragment,fragment).show(fragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                default:
                    break;
            }

        }
        else if(currentDrawerContent.equals("mainPage")) {
            switch(position){
                case 1:
                    fragment = new emptyFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.productFragment,fragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
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
        if (currentDrawerContent.equals("mainPage"))
            drawerLayout.closeDrawers();
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
