package com.example.user.initialui;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class Loading extends Activity{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(3000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
