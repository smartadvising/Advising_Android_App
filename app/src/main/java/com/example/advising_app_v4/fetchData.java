package com.example.advising_app_v4;

import android.os.AsyncTask;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class fetchData extends AsyncTask<Void,Void,ArrayList<String>> {
    String Data = "";
    String SingleParsed = "";
    String DataParsed = "";

    public static ArrayList<String> CollegeList = new ArrayList<>();

    public interface AsyncResponse {
        void processFinish(ArrayList<String> output);
    }

    public AsyncResponse delegate = null;

    public fetchData(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... voids) {

        try {
            URL url = new URL("https://bvet7wmxma.execute-api.us-east-1.amazonaws.com/prod/colleges?app_token=6vDahPFC9waiEwI3UMHbz5paBkTPRFZshJeDL7ZYnFXvbcoYRGtFPD6Ogh8iy6nI");

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null)
            {
                line = bufferedReader.readLine();
                Data = Data + line;
            }

            //Figure out how to GET OBJECT TO SHOW
            JSONObject JO = new JSONObject(Data);
            JSONArray JA = JO.getJSONArray("colleges");

            for(int i = 0; i < JA.length(); i++)
            {
                JSONObject JasonObject = JA.getJSONObject(i);
                SingleParsed = "ID: " + JasonObject.get("id") +"\n"+
                        "Name: " + JasonObject.get("name") +"\n"+
                        "Email: " + JasonObject.get("email_tld") +"\n";

                CollegeList.add(JasonObject.getString("name"));

                DataParsed = DataParsed + SingleParsed;
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return CollegeList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        //super.onPostExecute(result);
        delegate.processFinish(result);
    }
}
