package com.example.furkanubuntu.helloworld;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by furkanubuntu on 6/29/17.
 */

public class itemlistFragment extends Fragment {
    OnFavoritesAdded mCallback;

    MainActivity main;
    JsonAdapter adapter;
    ArrayList<JsonItemOnSale> arrayOfGoods;
    ListView listView;
    View view;
    FragmentManager fragmentManager;
    public static final String choiceString = "ARG_PAGE";
    //String imageSize = "Lets see i think i will need this later"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String apiKey = "AIzaSyAwL2u9ByNL9coBouyJBjtx3UXmb_mtC50"; // "AIzaSyCFrT2Vp7pqSBbTecdlzO_bpNkj52iZ04Y"; //"AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A"; //"AIzaSyBianBdkjLEijeQL3T0RTMgTDd9ydL8J7Y"; //
    String cx = "000741119430587044101:2fdfbkejafg";
    String fileType = "jpg";
    String searchType = "image";
    String searchCriteria;
    String combinedUrl;
    int scrollCount = 0;
    myASyncTask aSyncTask;
    ProgressBar progressBar;
    SearchView searchView;
    String searchInput = "";
    String aSyncTaskType = "normal";
    String departmentName;
    boolean userScrolled = false;

    public static itemlistFragment newInstance(int choice) {
        Bundle args = new Bundle();
        args.putInt(choiceString, choice);
        itemlistFragment fragment = new itemlistFragment();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayOfGoods = new ArrayList<>();
        fragmentManager = getActivity().getFragmentManager();

        switch (getArguments().getInt(choiceString)) {
            case 2:
                departmentName = "booksaudiobooks";
                searchCriteria = "books+audiobooks";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask = new myASyncTask(combinedUrl);
                aSyncTask.execute();
                break;
            case 3:
                departmentName = "moviestv";
                searchCriteria = "movieposters";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask1 = new myASyncTask(combinedUrl);
                aSyncTask1.execute();
                break;
            case 4:
                departmentName = "music";
                searchCriteria = "bestalbumcovers";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask2 = new myASyncTask(combinedUrl);
                aSyncTask2.execute();
                break;
            case 5:
                departmentName = "videogames";
                searchCriteria = "videogamecovers";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask3 = new myASyncTask(combinedUrl);
                aSyncTask3.execute();
                break;
            case 6:
                departmentName = "computersoffice";
                searchCriteria = "laptops";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask4 = new myASyncTask(combinedUrl);
                aSyncTask4.execute();
                break;
            case 7:
                departmentName = "electronics";
                searchCriteria = "iphone+samsung";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask5 = new myASyncTask(combinedUrl);
                aSyncTask5.execute();
                break;
            case 8:
                departmentName = "garden";
                searchCriteria = "gardening+tools";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask6 = new myASyncTask(combinedUrl);
                aSyncTask6.execute();
                break;
            case 9:
                departmentName = "grocery";
                searchCriteria = "grocery+items";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask7 = new myASyncTask(combinedUrl);
                aSyncTask7.execute();
                break;
            case 10:
                departmentName = "beauty";
                searchCriteria = "beauty+product+care";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask8 = new myASyncTask(combinedUrl);
                aSyncTask8.execute();
                break;
            case 11:
                departmentName = "kids";
                searchCriteria = "toys";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask9 = new myASyncTask(combinedUrl);
                aSyncTask9.execute();
                break;
            case 12:
                departmentName = "clothing";
                searchCriteria = "clothing+shoes+product";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask10 = new myASyncTask(combinedUrl);
                aSyncTask10.execute();
                break;
            case 13:
                departmentName = "sportsoutdoors";
                searchCriteria = "sports+products";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                aSyncTaskType = "normal";
                myASyncTask aSyncTask11 = new myASyncTask(combinedUrl);
                aSyncTask11.execute();
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_listview, container, false);

        adapter = new JsonAdapter(view.getContext(), arrayOfGoods);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), PorterDuff.Mode.SRC_IN );
        searchView = (SearchView) getActivity().findViewById(R.id.searchBar);


        listView = (ListView) view.findViewById(R.id.item_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main = (MainActivity) getActivity();
                DbHelper helperInstance = main.getInstance();
                helperInstance.addClickCount(departmentName,main.userID);
                startProductActivity(position);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    userScrolled = true;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (searchInput.length() == 0) {
                    int lastInScreen = firstVisibleItem + visibleItemCount;
                    if (lastInScreen == totalItemCount && userScrolled)
                    {
                        if (aSyncTask == null || aSyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                            scrollCount++;
                            combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                    + "&searchType=" + searchType + "&fileType=" + fileType + "&start=" + scrollCount * 10 + "&alt=json";
                            aSyncTaskType = "normal";
                            aSyncTask = new myASyncTask(combinedUrl);
                            aSyncTask.execute();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String userInput) {
                if(userInput.length() > 0){
                    if(wordChecker(userInput)){
                        MainActivity main = (MainActivity) getActivity();
                        DbHelper helperInstance = main.getInstance();
                        helperInstance.addSearch(userInput, departmentName, main.userID);
                        helperInstance.addSearchCount(departmentName, main.userID);
                        searchCriteria = departmentName + "+" + userInput;
                        combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                        aSyncTaskType = "search";
                        myASyncTask aSyncTask = new myASyncTask(combinedUrl);
                        aSyncTask.execute();
                        return true;
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getActivity(), "Not a meaningful word.", Toast.LENGTH_SHORT);
                        toast.show();
                        return false;
                    }
                }
                Toast toast = Toast.makeText(getActivity(), "Please enter a word before submitting." ,Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                searchInput = s;
//                if(s.length() == 0){
//                    adapter = new JsonAdapter(view.getContext(), arrayOfGoods);
//                    listView.setAdapter(adapter);
//                }
//                else if(s.length() > 0){
//                    arrayOfGoods2.clear();
//                    for (int i = 0; i < arrayOfGoods.size(); i++) {
//                        if (arrayOfGoods.get(i).description.toLowerCase().contains(s.toLowerCase())){
//                            arrayOfGoods2.add(arrayOfGoods.get(i));
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                    adapter = new JsonAdapter(view.getContext(), arrayOfGoods2);
//                    listView.setAdapter(adapter);
//                }
                return true;
            }
        });
        return view;
    }

    protected void startProductActivity(int position){
        Intent intent = new Intent(getActivity(),ProductActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,arrayOfGoods.get(position).jsonLink);
        intent.putExtra(Intent.EXTRA_TITLE,searchCriteria);
        intent.putExtra(Intent.EXTRA_SUBJECT,arrayOfGoods.get(position).description);
        intent.putExtra("Department",arrayOfGoods.get(position).department);
        main = (MainActivity) getActivity();
        intent.putExtra(Intent.EXTRA_TEMPLATE, main.userID);
        getActivity().startActivity(intent);
    }

    private class myASyncTask extends AsyncTask {
        String urlString;

        myASyncTask(String urlString) {
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
            } catch (Exception e) {
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
                    if(aSyncTaskType.equals("search"))
                        arrayOfGoods.clear();
                    for (int i = 0; i < 10; i++) {
                        arrayOfGoods.add(new JsonItemOnSale("%35", "450$", jsonArray.getJSONObject(i).getString("title"),
                                jsonArray.getJSONObject(i).getString("link"),departmentName));
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
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

    protected boolean wordChecker(String word) {
        try {
            InputStream stream = getResources().openRawResource(R.raw.americanenglish);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class JsonAdapter extends ArrayAdapter<JsonItemOnSale> {
        JsonAdapter(Context context, ArrayList<JsonItemOnSale> goods){
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
                    mCallback.onFavButtonPressed(anItem.description, anItem.jsonLink, anItem.department);
                }
            });

            addCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity main = (MainActivity) getActivity();
                    DbHelper helperInstance = main.getInstance();
                    helperInstance.addCart(anItem.jsonLink, anItem.description, anItem.department,main.userID);
                }
            });

            discountAmount.setText(anItem.discount);
            price.setText(anItem.price);
            description.setText(anItem.description);
            Picasso.with(convertView.getContext()).load(anItem.jsonLink).fit().into(productPic);

            return convertView;
        }
    }

}