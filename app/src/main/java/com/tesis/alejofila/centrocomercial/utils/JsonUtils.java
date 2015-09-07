package com.tesis.alejofila.centrocomercial.utils;

import android.os.Bundle;
import android.util.Log;

import com.tesis.alejofila.centrocomercial.http.Constants;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by alejofila on 6/09/15.
 */
public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Bundle detailedJsonToBundle(JSONObject jObj) {
        Bundle b = new Bundle();

        try {
            b.putString(Constants.PRODUCT_IMAGE, jObj.getString(Constants.PRODUCT_IMAGE));
            b.putString(Constants.PRODUCT_NAME, jObj.getString(Constants.PRODUCT_NAME));
            b.putString(Constants.PRODUCT_PRICE, jObj.getString(Constants.PRODUCT_PRICE));
            b.putString(Constants.PRODUCT_OLD_PRICE,jObj.getString(Constants.PRODUCT_OLD_PRICE));
            b.putString(Constants.PRODUCT_STORE,jObj.getString(Constants.PRODUCT_STORE));
        }
        catch (JSONException e){
            Log.e(TAG, "ERROR PARSEANDO JSON");
            e.printStackTrace();
        }
        return b;
    }

}