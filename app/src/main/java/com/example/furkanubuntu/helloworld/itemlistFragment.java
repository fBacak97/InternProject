package com.example.furkanubuntu.helloworld;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

/**
 * Created by furkanubuntu on 6/29/17.
 */

public class itemlistFragment extends Fragment {

    JsonAdapter adapter;
    ArrayList<JsonItemOnSale> arrayOfGoods;
    ListView listView;
    View view;
    FragmentManager fragmentManager;
    public static final String choiceString = "ARG_PAGE";
    //String imageSize = "Lets see i think i will need this later"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String apiKey = "AIzaSyAwL2u9ByNL9coBouyJBjtx3UXmb_mtC50";//"AIzaSyCFrT2Vp7pqSBbTecdlzO_bpNkj52iZ04Y";//"AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A";
    String cx = "000741119430587044101:2fdfbkejafg";
    String fileType = "jpg";
    String searchType = "image";
    String searchCriteria;
    String combinedUrl;
    int scrollCount = 0;
    myASyncTask aSyncTask;
    ProgressBar progressBar;

    public static itemlistFragment newInstance(int choice) {
        Bundle args = new Bundle();
        args.putInt(choiceString, choice);
        itemlistFragment fragment = new itemlistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayOfGoods = new ArrayList<JsonItemOnSale>();
        fragmentManager = getActivity().getFragmentManager();

        switch (getArguments().getInt(choiceString)) {
            case 2:
                arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg"));
                arrayOfGoods.add(new JsonItemOnSale("%45", "250$", "Samsung Note 3 Only Bluetooth is ", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg"));
                arrayOfGoods.add(new JsonItemOnSale("%20", "200$", "Xperia Z4 Second Hand", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg"));
                arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Conditionss", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg"));
                break;
            case 3:
                searchCriteria = "movieposters";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask = new myASyncTask(combinedUrl);
                aSyncTask.execute();
                break;
            case 4:
                searchCriteria = "bestalbumcovers";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask2 = new myASyncTask(combinedUrl);
                aSyncTask2.execute();
                break;
            case 5:
                searchCriteria = "videogamecovers";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask3 = new myASyncTask(combinedUrl);
                aSyncTask3.execute();
                break;
            case 6:
                searchCriteria = "laptops";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask4 = new myASyncTask(combinedUrl);
                aSyncTask4.execute();
                break;
            case 7:
                searchCriteria = "iphone+samsung";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask5 = new myASyncTask(combinedUrl);
                aSyncTask5.execute();
                break;
            case 8:
                searchCriteria = "gardening+tools";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask6 = new myASyncTask(combinedUrl);
                aSyncTask6.execute();
                break;
            case 9:
                searchCriteria = "grocery+items";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask7 = new myASyncTask(combinedUrl);
                aSyncTask7.execute();
                break;
            case 10:
                searchCriteria = "beauty+product+care";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask8 = new myASyncTask(combinedUrl);
                aSyncTask8.execute();
                break;
            case 11:
                searchCriteria = "toys";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask9 = new myASyncTask(combinedUrl);
                aSyncTask9.execute();
                break;
            case 12:
                searchCriteria = "clothing+shoes+product";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                myASyncTask aSyncTask10 = new myASyncTask(combinedUrl);
                aSyncTask10.execute();
                break;
            case 13:
                searchCriteria = "sports+products";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
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
        progressBar.getIndeterminateDrawable()
                .setColorFilter(ContextCompat.getColor(view.getContext(), R.color.black), PorterDuff.Mode.SRC_IN );
        listView = (ListView) view.findViewById(R.id.item_listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if (lastInScreen == totalItemCount)//+++++++++++++++++ WE NEED TO SEE İF THE ASYNC TASK İS STİLL RUNNİNG
                {
                    if (aSyncTask == null || aSyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                        scrollCount++;
                        combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                + "&searchType=" + searchType + "&fileType=" + fileType + "&start=" + scrollCount * 10 + "&alt=json";
                        //new myASyncTask(combinedUrl).execute();
                        aSyncTask = new myASyncTask(combinedUrl);
                        aSyncTask.execute();
                        progressBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        listView.setAdapter(adapter);
        return view;
    }

    public class myASyncTask extends AsyncTask {
        String urlString;
        int exceptionNo;

        public myASyncTask(String urlString) {
            super();
            this.urlString = urlString;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            URL url = null;
            StringBuilder builder = null;
            JSONArray jsonArray;
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
                        arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Conditionss", jsonArray.getJSONObject(i).getString("link")));
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
            if (o == null) {
                //CharSequence text = "Returned null";
                //int duration = Toast.LENGTH_SHORT;

               // Toast toast = Toast.makeText(getContext(), text, duration);
                //toast.show();
            }
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
    }

}