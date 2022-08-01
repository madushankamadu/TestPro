package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> arr = new ArrayList<>();

    private static final String TAG = "textCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> cityCodes = new ArrayList<>();

        try {
            JSONObject job = new JSONObject(get_json());
            JSONArray ja = job.getJSONArray("List");
            for (int i = 0; i< ja.length(); i++){
                JSONObject jo = ja.getJSONObject(i);
                Log.d(TAG, "onCreate: "+jo.getString("CityCode"));

                arr.add( jo.getString("CityCode"));
                Log.d(TAG, "onCreate: "+arr);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }
    public String get_json(){
        String json = null;
        try {
            InputStream is = this.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");



        }catch(IOException e)  {
            e.printStackTrace();

        }
        return json;
    }
}