package com.example.darts.gpstest;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

// http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/

public class MainActivity extends AppCompatActivity {
    Button btnShowLocation;
    EditText editText;

    StringBuilder strBuilder = new StringBuilder();

    String auth_code = "wLHFtkO4VG7tXEDK1gv4%2FkwrwBqjttUo1QxxLSTdhbJ65A27UJqlBhU0izxIsWF%2FPH8tAp3FX4yEWpxBfhna9w%3D%3D";

    TextView tv;
    Handler handler = new Handler();

    // GPSTracker class
    GPSTracker gps = null;

    public Handler mHandler;

    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;

    public double latitude;
    public double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }

        editText = (EditText) findViewById(R.id.editText);
        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==RENEW_GPS){
                    makeNewGpsService();
                }
                if(msg.what==SEND_PRINT){
                    logPrint((String)msg.obj);
                }
            }
        };

        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // create class object
                if(gps == null) {
                    gps = new GPSTracker(MainActivity.this,mHandler);
                }else{
                    gps.Update();
                }

                // check if GPS enabled
                if(gps.canGetLocation()){
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    connect(latitude,longitude);
                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
            }
        });
    }
    public void makeNewGpsService(){
        if(gps == null) {
            gps = new GPSTracker(MainActivity.this,mHandler);
        }else{
            gps.Update();
        }

    }
    public void logPrint(String str){
        editText.append(getTimeStr()+" "+str+"\n");
    }
    public String getTimeStr(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("MM/dd HH:mm:ss");
        return sdfNow.format(date);
    }

    private void connect(double latitude, double longitude) {
        try{
            String strUrl = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire?ServiceKey="+auth_code+"&"+"WGS84_LON=" + longitude + "&" + "WGS84_LAT=" + latitude;
            URL url = new URL(strUrl); HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if(conn!=null) {
                conn.setConnectTimeout(10000); conn.setRequestMethod("GET");
                int resCode = conn.getResponseCode(); if(resCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream(); InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr); String line = null;
                    while((line=br.readLine())!=null) {
                        strBuilder.append(line+"\n"); } br.close();
                    conn.disconnect();
                }
            }
            handler.post(new Runnable() {
                public void run() {
                    tv.setText(strBuilder.toString());
                }
            });
        }
        catch(Exception ex) {
            ex.printStackTrace(); Log.e("접속오류", ex.toString()); }
    }
}
