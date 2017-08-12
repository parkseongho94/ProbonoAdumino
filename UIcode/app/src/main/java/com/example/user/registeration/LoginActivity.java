package com.example.user.registeration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() { // Alt+Enter 하면 자동 임플리먼트가 됨
            @Override
            public void onClick(View v) {
                Intent registIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registIntent);
            }
        });

        Button unregisterloginButton = (Button) findViewById(R.id.unregisterloginButton);
        unregisterloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                Toast.makeText(getApplicationContext(),"비회원으로 로그인하셨습니다.",Toast.LENGTH_LONG).show();
                Intent intent =  new Intent(LoginActivity.this, UnregisterActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });

    }
}
