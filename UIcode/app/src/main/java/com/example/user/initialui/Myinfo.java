package com.example.user.initialui;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Myinfo extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        findViewById(R.id.button8).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button8:
                new AlertDialog.Builder(this)
                        .setTitle("알람 팝업")
                        .setMessage("팝업창 내용")
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dig, int sumthin){}
                        })
                        .show();
                break;

        }
    }
}
/*    public void ClickChange(View v){
        Intent intent_change = new Intent(getApplicationContext(), info_popup.class);
        startActivity(intent_change);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button8:
                startActivity(new Intent(this, info_popup.class));
                break;
        }
    }
 }*/



