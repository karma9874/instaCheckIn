package com.example.myapplication;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class sendNoti {

    public String send_postRequest(String token) throws IOException, JSONException {

        String AUTH_KEY_FCM = "AAAACjnJ7v4:APA91bHZLCrnEGUrbgKQ3CGNGgWRLoTHSWf4zMR2dzsZGz81JK_pAxYGYdPOVL9cnyFRNZrglhiXDGlg-rR2-q7-ARRc8i0aPMN2p63C0NG86xp7lGP1-4UGgBFMalYxrCCkTpifF2aM";
        String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        JSONObject postdata = new JSONObject();
        JSONObject data = new JSONObject();

        postdata.put("to", token);
        postdata.put("priority", "high");
        data.put("title", "Your verification is done");
        data.put("body", "Please collect room key from the reception");
        postdata.put("data", data);
        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        Request request = new Request.Builder()
                .url(API_URL_FCM)
                .post(body)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "key=" + AUTH_KEY_FCM)
                .build();

        Response response = client.newCall(request).execute();
        if(response.code()==200){
            return response.body().string();
        }else{
            return String.valueOf(response.code());
        }
    }
}