package com.example.furkanubuntu.helloworld;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements OnFavoritesAdded{
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
    int lastClickedPosition;
    SearchView searchView;
    DbHelper helperInstance;
    ViewPager viewPager;
    String username;
    int userID;
    TabLayout tabLayout;
    android.support.v4.view.PagerAdapter pagerAdapter;
    TextView mainHeader;
    float dX;
    float dY;
    int lastAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clearCache24Hours(this);

        helperInstance = new DbHelper(getBaseContext());
        helperInstance.getReadableDatabase();

        Intent intent = getIntent();
        userID = intent.getIntExtra(Intent.EXTRA_TITLE,0);
        username = helperInstance.getUsername(userID);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),MainActivity.this,"","","");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager)  findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        searchView = (SearchView) findViewById(R.id.searchBar);
        searchView.setVisibility(View.INVISIBLE);
        mainHeader = (TextView) findViewById(R.id.mainHeader);
        FloatingActionButton cartButton = (FloatingActionButton) findViewById(R.id.cartButton);
        cartButton.setImageResource(R.drawable.cart);
        cartButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = view.getX() - event.getRawX();
                        dY = view.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        view.setY(event.getRawY() + dY);
                        view.setX(event.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;

                    case MotionEvent.ACTION_UP:
                        if (lastAction == MotionEvent.ACTION_DOWN) {
                            Intent intent = new Intent(MainActivity.this, CartActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Arraylist", (Serializable) helperInstance.readCart(userID));
                            intent.putExtra(Intent.EXTRA_INTENT, bundle);
                            intent.putExtra(Intent.EXTRA_TEXT, userID);
                            startActivity(intent);
                        }
                        break;

                    default:
                        return false;
                }
                return true;
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.favorite);
        tabLayout.getTabAt(2).setIcon(R.drawable.account);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    mainHeader.setText("Recommended Items");
                else if (tab.getPosition() == 1)
                    mainHeader.setText("My Wishlist");
                else if (tab.getPosition() == 2)
                    mainHeader.setText("My Account");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

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
        drawerItemList.add(new DrawerItem(" My Cart",R.drawable.cart));
        drawerItemList.add(new DrawerItem(" My Account",R.drawable.account));
        drawerItemList.add(new DrawerItem(" Wishlist",R.drawable.favorite));
        drawerItemList.add(new DrawerItem(" Today's Deals",R.drawable.money));
        drawerItemList.add(new DrawerItem(" Recommendations",R.drawable.temp));
        drawerItemList.add(new DrawerItem(" Change Country",R.drawable.userlocation));
        drawerItemList.add(new DrawerItem(" Logout",R.drawable.logout));
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
        TextView userWelcome = headerView.findViewById(R.id.drawerHeader);
        userWelcome.setText("Hello, " + username);
        drawerListView.addHeaderView(headerView);
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        //----- Toolbar ---------
        setupToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);getSupportFragmentManager().executePendingTransactions();
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
        getSupportFragmentManager().executePendingTransactions();
        TabFragment tabFragment = (TabFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":1");
        if(tabFragment != null) {
            if (tabFragment.wishlistTabSelectionItems.size() != helperInstance.readWishlist(userID).size()) {
                tabFragment.wishlistTabSelectionItems = helperInstance.readWishlist(userID);
                tabFragment.wishlistTabAdapter.notifyDataSetChanged();
            }
        }
        if (getIntent().getIntExtra("buttonNo", 0) > 0) {
            int buttonNo = (getIntent().getIntExtra("buttonNo", 0));
            fragment = itemlistFragment.newInstance(buttonNo);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.productFragment, fragment).show(fragment);
            fragmentTransaction.commit();
            searchView.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

    public void clearCacheMidnight(Context context){
        try {
            File dir = new File(context.getCacheDir().getAbsolutePath() + "/picasso-cache");
            File[] files = dir.listFiles();
            String todaysDate = DateFormat.getDateInstance().format(new Date()).substring(0,2);
            for (int i = 0; i < files.length; i++) {
                String creationDate = DateFormat.getDateInstance().format(new Date(files[i].lastModified())).substring(0,2);
                if(Integer.parseInt(creationDate) < Integer.parseInt(todaysDate)) {
                    File file = new File(files[i].getAbsolutePath());
                    file.delete();
                }
            }
        } catch (Exception e){
          e.printStackTrace();
        }
    }

    public void clearCache24Hours(Context context){
        try {
            File dir = new File(context.getCacheDir().getAbsolutePath() + "/picasso-cache");
            File[] files = dir.listFiles();
            long currentTimeInMilliseconds = System.currentTimeMillis();
            for (int i = 0; i < files.length; i++) {
                long lastModifiedTime = files[i].lastModified();
                if (currentTimeInMilliseconds - lastModifiedTime > 86400000) {
                    files[i].delete();
                }
            }
        } catch (Exception e){
            e.printStackTrace();

        }
    }

    // Below 2 methods study them carefully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void setupDrawerToggle(){
        mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerTitle, R.string.drawerTitle){

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
        // Necessary to change toggle state.
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
                case 3:
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Arraylist", (Serializable) helperInstance.readCart(userID));
                    intent.putExtra(Intent.EXTRA_INTENT, bundle);
                    intent.putExtra(Intent.EXTRA_TEXT, userID);
                    startActivity(intent);
                    break;
                case 9:
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    break;
                case 10:
                    Intent intent3 = new Intent(this, DevContactActivity.class);
                    startActivity(intent3);
                    break;
                default:
                    break;
            }
        }
    }

    public void previousDrawerView(){
        if (currentDrawerContent.equals("mainPage")) {
            tabLayout.setupWithViewPager(viewPager);
            drawerLayout.closeDrawers();
            searchView.setVisibility(View.INVISIBLE);
            pagerAdapter.notifyDataSetChanged();
        }
        else if (currentDrawerContent.equals("departmentsTab")) {
            drawerAdapter = new DrawerAdapter(this, drawerItemList);
            drawerListView.setAdapter(drawerAdapter);
            currentDrawerContent = "mainPage";
        }
    }

    public DbHelper getInstance(){
        return helperInstance;
    }

    @Override
    public void onFavButtonPressed(String description, String link, String department) {
        getSupportFragmentManager().executePendingTransactions();
        TabFragment tabFragment = (TabFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.viewpager+":1");
        if(arrayContainChecker(tabFragment.wishlistTabSelectionItems, link)) {
            tabFragment.wishlistTabSelectionItems.add(new JsonItemOnSale("450$", "450$", description, link, department));
            tabFragment.wishlistTabAdapter.notifyDataSetChanged();
        }
    }

    public boolean arrayContainChecker(ArrayList<JsonItemOnSale> arrayList, String link){
        for (int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).jsonLink.equals(link))
                return false;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && (searchView.getVisibility() == View.VISIBLE)){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commit();
            searchView.setVisibility(View.INVISIBLE);
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("ExitCommand", true);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
            lastClickedPosition = position;
        }
    }
}
