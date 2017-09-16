package com.example.gnana.weathermonitor;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.Math.log;

public class MainActivity extends AppCompatActivity {

    final String url = "https://api.thingspeak.com/channels/242391/feeds/last.json" ;
    private TextView temp ;
    private TextView desc ;
    private TextView humidity;
    private TextView lightIntensity ;
    private TextView rain ;
    private ImageView image ;
    private TextView dewPoint ;
    private TextView humid;
    private TextView rain1;
    private TextView light1;
    private TextView dewp1;

    private ArrayList<weatherReport> weatherReport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (TextView) findViewById(R.id.temp);
        desc = (TextView) findViewById(R.id.desc);
        humidity = (TextView) findViewById(R.id.humidity);
        lightIntensity = (TextView) findViewById(R.id.light);
        rain = (TextView) findViewById(R.id.rain);
        dewPoint = (TextView) findViewById(R.id.dewpoint);
        image = (ImageView) findViewById(R.id.image);
        humid = (TextView) findViewById(R.id.hum1);
        rain1 = (TextView) findViewById(R.id.rain1);
        light1 =(TextView) findViewById(R.id.light1);
        dewp1 = (TextView) findViewById(R.id.dew1);
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(),  "fonts/avenir.ttf");
        humid.setTypeface(custom_font1);
        rain1.setTypeface(custom_font1);
        light1.setTypeface(custom_font1);
        dewp1.setTypeface(custom_font1);


        //setContentView(R.layout.activity_main);
        downloadData();
    }

    public void downloadData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String date = response.getString("created_at");
                    String temp = response.getString("field1");
                    String humidity = response.getString("field2");
                    String lightIntensity = response.getString("field3");
                    String rain = response.getString("field4");
                    date = date.substring(0,10);
                    double h = Double.parseDouble(humidity);
                    double t = Double.parseDouble(temp);
                    double gamma = log(h/100) + ((17.62*t) / (243.5+t));
                    double dp = 243.5*gamma / (17.62-gamma);
                    int d = (int) dp;
                    String dewPoint = d + "";
                    Log.v("json",temp);
                    weatherReport report = new weatherReport(temp,date,humidity,lightIntensity,rain,dewPoint);
                    Log.v("white",report.getLightIntensity());
                    weatherReport.add(report);
                    Log.v("six",""+weatherReport.size());

                }catch (JSONException e){
                    Log.v("exception","exc:"+e);
                    e.printStackTrace();
                }
                updateUI();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error",error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public void updateUI(){
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(),  "fonts/avenir.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(),  "fonts/futura.ttf");
        Typeface custom_font3 = Typeface.createFromAsset(getAssets(),  "fonts/helvetica.otf");
        if(weatherReport.size() > 0) {
            weatherReport report = weatherReport.get(0);
            String temperature = report.getTemp().substring(0,2)+"°C";
            String humid = report.getHumidity().substring(0,2);
            String light = report.getLightIntensity();
            String rainy = report.getRain();
            String dewp = report.getDewPoint();
            temp.setText(temperature);
            temp.setTypeface(custom_font2);
            if(Integer.parseInt(report.getRain()) > 0){
                desc.setText("rainy");
                desc.setTypeface(custom_font3);
                image.setBackground(getResources().getDrawable(R.drawable.rain));
            }
            humidity.setText(humid + "%");
            humidity.setTypeface(custom_font1);
            Log.v("dew",report.getDewPoint() + " " + report.getTemp() + " " + report.getHumidity());
            lightIntensity.setText(light);
            lightIntensity.setTypeface(custom_font1);
            rain.setText(rainy);
            rain.setTypeface(custom_font1);
            dewPoint.setText(dewp);
            dewPoint.setTypeface(custom_font1);
        }
    }
}
