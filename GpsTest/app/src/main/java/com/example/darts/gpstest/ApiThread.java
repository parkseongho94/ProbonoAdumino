package com.example.darts.gpstest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by yesun on 2017-07-23.
 */

public class ApiThread extends Thread {

    StringBuilder output = new StringBuilder();
    String auth_code = "wLHFtkO4VG7tXEDK1gv4%2FkwrwBqjttUo1QxxLSTdhbJ65A27UJqlBhU0izxIsWF%2FPH8tAp3FX4yEWpxBfhna9w%3D%3D";

    double latitude;
    double longitude;

    public ApiThread(double latitude, double longitude) {
        output = new StringBuilder();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void run() {
        try {

            String urlStr = "http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncLcinfoInqire?ServiceKey=" + auth_code + "&" + "WGS84_LON=" + longitude + "&" + "WGS84_LAT=" + latitude;
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            Log.e("conn", conn.toString());

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                conn.se

                int resCode = conn.getResponseCode();
                Log.e("코드",Integer.toString(resCode));
                if (resCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;


                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }

                        output.append(line + "\n");
                    }

                    reader.close();
                    conn.disconnect();

                }
            }

        } catch (ProtocolException e) {
            Log.v("예외",e.toString());
            e.printStackTrace();
        } catch (MalformedURLException e) {
            Log.v("예외",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.v("예외",e.toString());
            e.printStackTrace();
        }
    }

    public String getResult() {
        return output.toString();
    }

}
