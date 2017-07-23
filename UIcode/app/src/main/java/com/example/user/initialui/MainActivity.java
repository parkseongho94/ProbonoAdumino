package com.example.user.initialui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void ClickJoin(View v) {
        Intent intent_join = new Intent(getApplicationContext(), Join.class);
        startActivity(intent_join);
    }
    public void ClickLogin(View v){
        Intent intent_login = new Intent(getApplicationContext(), Myinfo.class);
        startActivity(intent_login);
    }
    /*public void ClickChange(View v){
        Intent intent_change = new Intent(getApplicationContext(), info_popup.class);
        startActivity(intent_change);
    }*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button8:
                startActivity(new Intent(this, info_popup.class));
                break;
        }
    }
}
/*public void onClick(View v){
        switch(v.getId()){
            case R.id.button8:
                startActivity(new Intent(this, info_popup.class));
        }
    }*/