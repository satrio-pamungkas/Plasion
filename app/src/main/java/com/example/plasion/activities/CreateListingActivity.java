package com.example.plasion.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.plasion.R;

import java.util.Objects;

public class CreateListingActivity extends AppCompatActivity {

    private AppCompatButton proceedButton;

    private final View.OnClickListener proceedOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CreateListingActivity.this, FinalCreateListingActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        proceedButton = (AppCompatButton) findViewById(R.id.proceed_button);
        proceedButton.setOnClickListener(proceedOnClickListener);
    }
}