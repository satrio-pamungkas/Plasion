package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private TextView displayName;
    private FloatingActionButton logoutButton;
    private MaterialCardView findDonorButton, createDonorButton, profilButton, myDonorButton;
    private String userDisplayName;
    private String userFirstName;
    private String userId, userFullName, userPhone, userLocation;
    private String DATABASE_URL;

    DatabaseReference userAttribute;
    FirebaseUser user;

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

    private final View.OnClickListener profileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goToProfileActivity();
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
        profilButton = (MaterialCardView) findViewById(R.id.profil);
        myDonorButton = (MaterialCardView) findViewById(R.id.my_donor);

        logoutButton =  (FloatingActionButton) findViewById(R.id.logout_button);
        logoutButton.bringToFront();

        logoutButton.setOnClickListener(logoutClickListener);
        createDonorButton.setOnClickListener(createClickListener);
        profilButton.setOnClickListener(profileClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userFirstName = getUserInformation().replaceAll(" .*","");
        getAllUserInformation();
        displayName = (TextView) findViewById(R.id.display_name);
        displayName.setText(userFirstName);

    }

    private String getUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getDisplayName();
    }

    private void goToCreateActivity() {
        Intent intent = new Intent(HomeActivity.this, CreateListingActivity.class);
        startActivity(intent);
    }

    private void goToProfileActivity() {
        Intent intent = new Intent(HomeActivity.this, EditProfileActivity.class);
        intent.putExtra("USER_FULL_NAME", userFullName);
        intent.putExtra("USER_PHONE", userPhone);
        intent.putExtra("USER_LOCATION", userLocation);
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

    private void getAllUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);
        userAttribute = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("users")
                .child(userId);

        userAttribute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot attribute) {
                userFullName = attribute.child("userFullName").getValue(String.class);
                userPhone = attribute.child("userPhone").getValue(String.class);
                userLocation = attribute.child("userLocation").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error"+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}