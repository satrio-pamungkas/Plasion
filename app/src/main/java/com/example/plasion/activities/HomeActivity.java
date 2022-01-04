package com.example.plasion.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private TextView displayName;
    private FloatingActionButton logoutButton;
    private MaterialCardView findDonorButton, createDonorButton, checkDonorButton, myDonorButton;
    private String userDisplayName;
    private String userFirstName;

    private final View.OnClickListener logoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            logoutButtonClicked();
        }
    };

    private final View.OnClickListener createClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToCreateActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        findDonorButton = (MaterialCardView) findViewById(R.id.cari_pendonor);
        createDonorButton = (MaterialCardView) findViewById(R.id.berdonor);
        checkDonorButton = (MaterialCardView) findViewById(R.id.cek_stok);
        myDonorButton = (MaterialCardView) findViewById(R.id.my_donor);

        logoutButton =  (FloatingActionButton) findViewById(R.id.logout_button);
        logoutButton.bringToFront();

        logoutButton.setOnClickListener(logoutClickListener);
        createDonorButton.setOnClickListener(createClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        userDisplayName = extras.getString("DISPLAY_NAME");
        userFirstName = userDisplayName.replaceAll(" .*","");

        displayName = (TextView) findViewById(R.id.display_name);
        displayName.setText(userFirstName);

    }

    private void goToCreateActivity() {
        Intent intent = new Intent(HomeActivity.this, CreateListingActivity.class);
        startActivity(intent);
    }

    private void logoutButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Keluar Akun");
        builder.setMessage("Apakah anda yakin ingin keluar dari akun ?");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.setOnShowListener(arg0 -> {
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
        });
        alert.show();
    }
}