package com.tesis.alejofila.centrocomercial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tesis.alejofila.centrocomercial.helper.CircleTransform;
import com.tesis.alejofila.centrocomercial.http.Constants;

import java.util.Iterator;

/**
 * Created by alejofila on 6/09/15.
 */
public class DetalleActivity extends AppCompatActivity {

    private static final String TAG =DetalleActivity.class.getSimpleName();

    ImageView imagen;
    TextView txtProductName;
    TextView txtProductPrice;
    TextView txtProductOldPrice;
    TextView txtProductStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Calling onCreate in DetallecActivity");
        this.setTitle("DETALLE ACTIVITY");
        setContentView(R.layout.activity_product_detail);
        imagen = (ImageView) findViewById(R.id.imagen_product_detail);
        txtProductName =(TextView) findViewById(R.id.txt_product_name);
        txtProductPrice = (TextView) findViewById(R.id.precio_product_detail);
        txtProductOldPrice =(TextView) findViewById(R.id.original_precio_product_detail);
        txtProductStore =(TextView) findViewById(R.id.store_product);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(getIntent() != null){
            Bundle b = getIntent().getExtras();

            String productName = b.getString(Constants.PRODUCT_NAME);
            String productImage = b.getString(Constants.PRODUCT_IMAGE);
            String productPrice = b.getString(Constants.PRODUCT_PRICE);
            String productOldPrice =b.getString(Constants.PRODUCT_OLD_PRICE);
            String productStore = b.getString(Constants.PRODUCT_STORE);


            setProductData(productName, productPrice, productOldPrice, productImage,productStore);
        }

    }

    private void setProductData(String productName, String productPrice,String productOldPrice,String productImage, String productStore) {

        txtProductName.setText(productName);
        txtProductStore.setText("Encuentralo en: "+productStore);
        txtProductPrice.setText("$"+productPrice);
        txtProductOldPrice.setText("$"+productOldPrice);
        Picasso.with(this).load(productImage)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .transform(new CircleTransform())
                .into(imagen);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG,"Calling onNew intent in DetalleActivity");
    }
}
