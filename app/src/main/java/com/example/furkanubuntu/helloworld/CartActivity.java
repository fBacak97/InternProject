package com.example.furkanubuntu.helloworld;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 7/18/17.
 */

public class CartActivity extends AppCompatActivity {

    ArrayList<JsonItemOnSale> cartList;
    int userID;
    CartAdapter cartAdapter;
    int priceSum = 0;
    TextView priceSumView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        priceSumView = (TextView) findViewById(R.id.itemPriceSum);
        ListView cartListView = (ListView) findViewById(R.id.cartList);

        Intent intent = getIntent();
        userID = intent.getIntExtra(Intent.EXTRA_TEXT,0);
        Bundle bundle = intent.getBundleExtra(Intent.EXTRA_INTENT);
        cartList = (ArrayList) bundle.getSerializable("Arraylist");

        for (int i = 0; i < cartList.size(); i++){
            int j = 0;
            while(cartList.get(i).price.charAt(j) != '$' )
                j++;
            priceSum = priceSum + Integer.parseInt(cartList.get(i).price.substring(0,j));
        }
        priceSumView.setText(String.valueOf(priceSum) + "$");

        cartAdapter = new CartAdapter(this,cartList);
        cartListView.setAdapter(cartAdapter);
        cartListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectCartItem(position);
            }
        });
    }

    public void selectCartItem(int position){
        Intent intent = new Intent(CartActivity.this,ProductActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,cartList.get(position).jsonLink);
        intent.putExtra(Intent.EXTRA_TITLE,cartList.get(position).department);
        intent.putExtra(Intent.EXTRA_SUBJECT,cartList.get(position).description);
        intent.putExtra(Intent.EXTRA_TEMPLATE,userID);
        startActivity(intent);
    }

    private class CartAdapter extends ArrayAdapter<JsonItemOnSale> {
        CartAdapter(@NonNull Context context, ArrayList<JsonItemOnSale> cart) {
            super(context, 0, cart);
        }

        @NonNull
        public View getView(final int position, View convertView, @NonNull ViewGroup parent)
        {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item,parent,false);
            }

            TextView description = (TextView) convertView.findViewById(R.id.cartItemName);
            TextView priceTag = (TextView) convertView.findViewById(R.id.cartPrice);
            ImageView productPic = (ImageView) convertView.findViewById(R.id.cartPic);
            Button button = (Button) convertView.findViewById(R.id.removeCartButton);
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
                                    DbHelper helperInstance = new DbHelper(getContext());
                                    helperInstance.removeCart(cartList.get(position).jsonLink, userID);
                                    int k = 0;
                                    while (cartList.get(position).price.charAt(k) != '$') {
                                        k++;
                                    }
                                    priceSum = priceSum - Integer.parseInt(cartList.get(position).price.substring(0,k));
                                    priceSumView.setText(String.valueOf(priceSum) + "$");
                                    cartList.remove(position);
                                    cartAdapter.notifyDataSetChanged();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to remove this item?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });


            Picasso.with(convertView.getContext()).load(cartList.get(position).jsonLink).fit().into(productPic);
            description.setText(cartList.get(position).description);
            priceTag.setText(cartList.get(position).price);

            return convertView;
        }
    }
}
