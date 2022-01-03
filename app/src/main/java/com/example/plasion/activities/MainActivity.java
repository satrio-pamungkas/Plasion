package com.example.plasion.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.plasion.R;

public class MainActivity extends AppCompatActivity {
    AppCompatButton buttonLogin;
    AppCompatButton buttonRegister;

    private final View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (AppCompatButton) findViewById(R.id.login_button);
        buttonRegister = (AppCompatButton) findViewById(R.id.register_button);

        buttonLogin.setOnClickListener(loginClickListener);
        buttonRegister.setOnClickListener(registerClickListener);
    }
}