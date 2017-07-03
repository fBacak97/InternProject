package com.example.furkanubuntu.helloworld;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
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

    JsonAdapter adapter;
    ArrayList<JsonItemOnSale> arrayOfGoods;
    ListView listView;
    View view;
    char quoteMark = '"';
    String linkString = quoteMark + "link" + quoteMark;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    public static final String choiceString = "ARG_PAGE";
    //String imageSize = "Lets see i think i will need this later"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String apiKey = "AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A";
    String cx = "000741119430587044101:2fdfbkejafg";
    String fileType = "jpg";
    String searchType = "image";
    String searchCriteria;
    String combinedUrl;
    String startNumber;
    int scrollCount = 0;

    public static itemlistFragment newInstance(int choice){
        Bundle args = new Bundle();
        args.putInt(choiceString,choice);
        itemlistFragment fragment = new itemlistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayOfGoods = new ArrayList<JsonItemOnSale>();
        fragmentManager = getActivity().getFragmentManager();

        switch (getArguments().getInt(choiceString)){
            case 2:
                arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg" ));
                arrayOfGoods.add(new JsonItemOnSale("%45", "250$", "Samsung Note 3 Only Bluetooth is ", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg" ));
                arrayOfGoods.add(new JsonItemOnSale("%20", "200$", "Xperia Z4 Second Hand","https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg" ));
                arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Conditionss", "https://s-media-cache-ak0.pinimg.com/736x/4a/70/b4/4a70b4c3b5d26902c8737c81809f2a02.jpg"));
                break;
            case 3:
                MainActivity activity = new MainActivity();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            apiKey = "AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A";
                            cx = "000741119430587044101:2fdfbkejafg";
                            fileType = "jpg";
                            searchType = "image";
                            //String imageSize = "Lets see i think i will need this later"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                                    + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";

                            String jsonString = stringBuilder(combinedUrl);

                            for (int index = 0; index < jsonString.length() - 6; index++) {
                                if (jsonString.substring(index, index + 6).equals(linkString)) {
                                    int a = index + 9;
                                    String link = "";
                                    while (jsonString.charAt(a) != '"') {
                                        link = link + jsonString.charAt(a);
                                        a++;
                                    }
                                    arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", link));
                                }
                            }
                        } catch (Exception exception) {
                            System.out.println("Error" + exception.getMessage());
                        }
                    }
                });
                break;
            case 4:
                searchCriteria = "flower";
                combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                        + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";
                //new myASyncTask(combinedUrl).execute();
                break;
            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_listview,container, false);

        adapter = new JsonAdapter(view.getContext(), arrayOfGoods);
        listView = (ListView) view.findViewById(R.id.item_listview);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if(lastInScreen == totalItemCount) //+++++++++++++++++ WE NEED TO SEE İF THE ASYNC TASK İS STİLL RUNNİNG
                {
                    scrollCount++;
                    combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                            + "&searchType=" + searchType + "&fileType=" + fileType + "&start=" + scrollCount * 10 +"&alt=json";
                    //new myASyncTask(combinedUrl).execute();
                }
            }
        });
        listView.setAdapter(adapter);
        return view;
    }



    public void searchMethod(final String searchCriteria){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String apiKey = "AIzaSyCj4Ok-oVrrVJassta4kX1dugbtGZTxD9A";
                    String cx = "000741119430587044101:2fdfbkejafg";
                    String fileType = "jpg";
                    String searchType = "image";
                    //String imageSize = "Lets see i think i will need this later"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    String combinedUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiKey + "&cx=" + cx + "&q=" + searchCriteria
                            + "&searchType=" + searchType + "&fileType=" + fileType + "&alt=json";

                    String jsonString = stringBuilder(combinedUrl);
                    for(int index = 0; index < jsonString.length() - 6; index++){
                        if(jsonString.substring(index,index+6).equals(linkString)){
                            int a = index+9;
                            String link = "";
                            while(jsonString.charAt(a) != '"'){
                                link = link + jsonString.charAt(a);
                                a++;
                            }
                            arrayOfGoods.add(new JsonItemOnSale("%35", "450$", "Iphone 7 Real Good Condition", link));
                        }
                    }
                }
                catch (Exception exception)
                {
                    System.out.println("Error" + exception.getMessage());
                }
            }

            public String stringBuilder(String urlString) throws IOException{
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if(connection.getResponseCode() != 200) {
                    throw new IOException(connection.getResponseMessage());
                }

                String line;
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }

                connection.disconnect();
                return builder.toString();
            }
        });

       thread.start();
    }

    public String stringBuilder(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        if(connection.getResponseCode() != 200) {
           throw new IOException(connection.getResponseMessage());
        }

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();

        while((line = reader.readLine()) != null){
           builder.append(line);
        }

        Log.d("MYTAG", urlString);

        InputStream in = null;
        try{
            in = new BufferedInputStream(connection.getInputStream());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        //readStream(in);
        Log.d("MYTAG", "on after input stream of stringbuilder");
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(getActivity(), text, duration);
        toast.show();

        Log.d("MYTAG", "on after toast of stringbuilder");

        //StringBuilder builder = new StringBuilder();
        int i = 0;
        while(i < in.toString().length()){
            builder.append(in.toString().charAt(i));
        }

        connection.disconnect();


        Log.d("MYTAG", "on end of stringbuilder");
        return builder.toString();
    }

    public class myASyncTask extends AsyncTask{
        String urlString;
        int exceptionNo;

        public myASyncTask(String urlString){
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
            }catch (IOException e){
                exceptionNo = 1;
            }

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(builder.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jsonObject != null){
                try {
                    jsonArray = jsonObject.getJSONArray("items");
                    for(int i = 0; i < 10; i++){
                        Log.d("MYTAG",jsonArray.getJSONObject(i).getString("link"));
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
            if(o == null){
                CharSequence text = "Returned null";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();
            }
            adapter.notifyDataSetChanged();
        }
    }

}