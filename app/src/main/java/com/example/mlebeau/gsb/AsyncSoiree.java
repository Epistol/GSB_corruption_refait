package com.example.mlebeau.gsb;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by mlebeau on 04/04/2017.
 */

public class AsyncSoiree extends AsyncTask<String, Integer, String> {
    private static OkHttpClient client = null;

    public AsyncSoiree() {
        if (client == null) {
            client = new OkHttpClient();
        }
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        try {
            response = run(params[0]);
        } catch (IOException o) {
            o.printStackTrace();
        }
        return response;
    }


    private String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}

