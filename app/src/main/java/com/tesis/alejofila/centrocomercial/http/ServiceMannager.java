package com.tesis.alejofila.centrocomercial.http;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.tesis.alejofila.centrocomercial.utils.HttpParser;

/**
 * Created by tales on 4/09/15.
 */
public class ServiceMannager {

    public static void loginFunction(String email, String password, Callback callback){
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.EMAIL,email);
        params.put(Constants.PASSWORD, password);
        HttpRequest request = HttpRequest.post(Constants.URL_BASE + Constants.URL_LOGIN).form(params);
        int statusCode = request.code();
        if (statusCode == 200) {
            ResultCallback rc =  new ResultCallback.Builder(true).
                    message(Constants.NO_MESSAGE)
                    .build();
            callback.receiveResult(rc);
        }
        else{
            JSONObject obj= HttpParser.parseStringToJSON(HttpParser.interpretarInputStream(request.stream()));
            String message = HttpParser.getJSONValue(obj,Constants.MESSAGE);
            ResultCallback rc = new ResultCallback.Builder(false)
                    .message(message)
                    .build();
            callback.receiveResult(rc);

        }
    }

    public static void registerFunction(String nombre, String email, String password, String token, Callback callback ){
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.EMAIL,email);
        params.put(Constants.PASSWORD, password);
        params.put(Constants.NAME,nombre);
        params.put(Constants.TOKEN,token);
        HttpRequest request = HttpRequest.post(Constants.URL_BASE + Constants.URL_REGISTER).form(params);

        //int statusCode = request
    }

}
