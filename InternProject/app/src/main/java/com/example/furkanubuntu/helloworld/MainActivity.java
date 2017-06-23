package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView drawerListView;
    DrawerLayout drawerLayout;
    Fragment fragment;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<String> array;
    ArrayList<String> array2;
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;
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

        /////////
        drawerItemList = new ArrayList<>();
        departmentsList = new ArrayList<>();

        drawerItemList.add(new DrawerItem("Home",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Departments",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Fire & Kindle",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Prime",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Your Orders",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Account",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Wishlist",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Today's Deals",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Recommendations",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Gift Cards",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Subscribe & Save",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Change Country",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Recently Viewed Items",R.drawable.icon));
        drawerItemList.add(new DrawerItem("Contact Us",R.drawable.icon));

        departmentsList.add(new DrawerItem("Back",R.drawable.icon));
        departmentsList.add(new DrawerItem("Books & Audiobooks",R.drawable.icon));
        departmentsList.add(new DrawerItem("Movies & TV",R.drawable.icon));
        departmentsList.add(new DrawerItem("Music",R.drawable.icon));
        departmentsList.add(new DrawerItem("Apps & Video Games",R.drawable.icon));
        departmentsList.add(new DrawerItem("Computers & Office",R.drawable.icon));
        departmentsList.add(new DrawerItem("Electronics",R.drawable.icon));
        departmentsList.add(new DrawerItem("Home, Garden & Tools",R.drawable.icon));
        departmentsList.add(new DrawerItem("Grocery & Beverage",R.drawable.icon));
        departmentsList.add(new DrawerItem("Health & Beauty",R.drawable.icon));
        departmentsList.add(new DrawerItem("Toys, Kids & Baby",R.drawable.icon));
        departmentsList.add(new DrawerItem("Clothing & Shoes",R.drawable.icon));
        departmentsList.add(new DrawerItem("Sports & Outdoors",R.drawable.icon));


        drawerAdapter = new DrawerAdapter(this,drawerItemList);
        drawerListView = (ListView) findViewById(R.id.drawerListView);
        drawerListView.setAdapter(drawerAdapter);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View headerView = inflater.inflate(R.layout.drawer_header, null,false);

        drawerListView.addHeaderView(headerView);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        setupDrawerToggle();
        drawerLayout.addDrawerListener(mDrawerToggle);

        fragmentManager = getFragmentManager();

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
                if(currentDrawerContent == "mainPage") {
                    drawerAdapter = new DrawerAdapter(getBaseContext(), drawerItemList);
                    drawerListView.setAdapter(drawerAdapter);
                }
                else if (currentDrawerContent == "departmentsTab"){
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

    public void selectItem(int position) {
        if(currentDrawerContent == "departmentsTab") {
            switch (position){
                case 1:
                    previousDrawerView();
                    break;
                case 2:
                    fragment = new itemFragment();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.productFragment,fragment);
                    fragmentTransaction.commit();
                    drawerLayout.closeDrawers();
                    break;
                default:
                    break;
            }

        }
        else if(currentDrawerContent == "mainPage") {
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
        if (currentDrawerContent == "mainPage")
            drawerLayout.closeDrawers();
        else if (currentDrawerContent == "departmentsTab") {
            drawerAdapter = new DrawerAdapter(this, drawerItemList);
            drawerListView.setAdapter(drawerAdapter);
            currentDrawerContent = "mainPage";
        }
    }

    class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
        }
    }
}
