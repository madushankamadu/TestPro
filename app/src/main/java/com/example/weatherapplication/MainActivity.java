package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> arr = new ArrayList<>();

    private static final String TAG = "textCheck";
    private static final String url ="http://api.openweathermap.org/data/2.5/group";


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



        for (String i:arr)
        {
            String city_code = i;
            getData(i);

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







    public void getData(String cityCode){
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {

                Log.d(TAG, "weather results: "+ response);

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.d(TAG, "error happend: "+ error);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("id", cityCode);
                map.put("units", "metric");
                map.put("appid", "a7d0cd107dfa36990f8416cdff4819cb");
                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

}