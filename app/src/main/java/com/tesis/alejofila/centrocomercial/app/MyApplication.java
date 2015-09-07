package com.tesis.alejofila.centrocomercial.app;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.tesis.alejofila.centrocomercial.http.Constants;

/**
 * Created by alejofila on 5/09/15.
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
