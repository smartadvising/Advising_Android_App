package com.example.advising_app_v4;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;

public class postData extends AsyncTask<Void,Void,Void> {

    public interface AsyncResponse {
        void processFinish(ArrayList<String> output);
    }

    public AsyncResponse delegate = null;

    public postData(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            String URL_POST = "https://bvet7wmxma.execute-api.us-east-1.amazonaws.com/prod/students";
            URL url = new URL(URL_POST);
            String type = "application/json";

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-Type",type);
            httpURLConnection.setRequestProperty("Accept",type);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email","jjc15h@my.fsu.edu");
            jsonObject.put("student_identifier","jjc15h");
            jsonObject.put("is_undergraduate",true);
            jsonObject.put("major_id",1);
            jsonObject.put("app_token","6vDahPFC9waiEwI3UMHbz5paBkTPRFZshJeDL7ZYnFXvbcoYRGtFPD6Ogh8iy6nI");

            Log.d("HELP", "Value = " + httpURLConnection.getResponseCode());

            /*
                    DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                   os.writeBytes(jsonObject.toString());
            */

            OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream());
            osw.write(jsonObject.toString());
            osw.flush();



                    } catch (MalformedURLException e){
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                    } catch (JSONException e){
                    e.printStackTrace();
                    }

        return null;
    }

    @Override
    protected void onPostExecute(Void Void) {
        super.onPostExecute(Void);
    }
}
