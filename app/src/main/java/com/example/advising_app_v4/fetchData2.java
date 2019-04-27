package com.example.advising_app_v4;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

public class fetchData2 extends AsyncTask<Integer,Void,ArrayList<String>> {
    String Data = "";
    String SingleParsed = "";
    String DataParsed = "";
    public static ArrayList<String> MajorList = new ArrayList<>();

    public interface AsyncResponse {
        void processFinish(ArrayList<String> output);
    }

    public AsyncResponse delegate = null;

    public fetchData2(AsyncResponse delegate){
        this.delegate = delegate;
    }
//Deals with getting the list of majors from whatever college the user choose
    @Override
    protected ArrayList<String> doInBackground(Integer... CollegeNum) {
            int position = CollegeNum[0].intValue();
        try {
            URL url = new URL("https://bvet7wmxma.execute-api.us-east-1.amazonaws.com/prod/colleges/" + position + "/majors?app_token=6vDahPFC9waiEwI3UMHbz5paBkTPRFZshJeDL7ZYnFXvbcoYRGtFPD6Ogh8iy6nI");

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpUrlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null)
            {
                line = bufferedReader.readLine();
                Data = Data + line;
            }

            JSONObject JO = new JSONObject(Data);
            JSONArray JA = JO.getJSONArray("majors");

            for(int i = 0; i < JA.length(); i++)
            {
                JSONObject JasonObject = JA.getJSONObject(i);

                MajorList.add(JasonObject.getString("name"));

                DataParsed = DataParsed + SingleParsed;
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return MajorList;
    }

    @Override
    protected void onPostExecute(ArrayList<String> result) {
        //super.onPostExecute(result);

        delegate.processFinish(result);
    }
}

