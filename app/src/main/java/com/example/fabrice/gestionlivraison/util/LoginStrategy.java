package com.example.fabrice.gestionlivraison.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class LoginStrategy extends AsyncTask<String, Void, String> {
        public static String AUTHORIZED = "AUTHORIZED";
        private static String NON_AUTHORIZED = "NON_AUTHORIZED";
        private String loginEndpointUrl = "http://10.0.2.2:8080/loginFromMobile";


    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    private AsyncResponse delegate = null;

    public LoginStrategy(AsyncResponse delegate){
        this.delegate = delegate;
    }

        protected void onPreExecute(){

        }
 
        protected String doInBackground(String... args) {
            try
            {
                //10.0.2.2 is the localhost of my computer
                URL url = new URL(loginEndpointUrl);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("login", args[0]);
                postDataParams.put("password", args[1]);
                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {
//                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                    StringBuilder sb = new StringBuilder("");
//                    String line;
//
//                    while ((line = in.readLine()) != null)
//                    {
//                        sb.append(line);
//                        break;
//                    }
//
//                    in.close();
//
//                    return sb.toString();
                    return AUTHORIZED;

                }
                else
                {
                    return NON_AUTHORIZED;
                }
            }
            catch(Exception e)
            {
                Log.println(Log.ERROR, "", e.getMessage());
                return "Exception: " + e.getMessage();
            }
        }
 
        @Override
        protected void onPostExecute(String result) {
            delegate.processFinish(result);
        }

    private String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext())
        {
            String key= itr.next();
            Object value = params.get(key);

            if (first)
            {
                first = false;
            }
            else
            {
                result.append("&");
            }

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}