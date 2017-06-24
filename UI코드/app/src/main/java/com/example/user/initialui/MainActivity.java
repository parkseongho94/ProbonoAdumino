package com.example.user.initialui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void ClickJoin(View v) {
        Intent intent_join = new Intent(getApplicationContext(), Join.class);
        startActivity(intent_join);
    }
}
