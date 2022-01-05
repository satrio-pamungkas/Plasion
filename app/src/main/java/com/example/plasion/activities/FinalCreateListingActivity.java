package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.plasion.R;
import com.example.plasion.models.Listing;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FinalCreateListingActivity extends AppCompatActivity {

    private CheckBox syaratPertama, syaratKedua;
    private AppCompatButton createButton;
    private String createdDate, userId, userFullName, userEmail, userPhone, userLocation, userBlood;
    private String DATABASE_URL;

    DatabaseReference databaseListing;
    DatabaseReference userAttribute;
    FirebaseUser user;

    private final View.OnClickListener createOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createListing();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_create_listing);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        syaratPertama = (CheckBox) findViewById(R.id.syarat_pertama);
        syaratKedua = (CheckBox) findViewById(R.id.syarat_kedua);
        createButton = (AppCompatButton) findViewById(R.id.create_listing);

        readUserAttribute();

        createButton.setOnClickListener(createOnClickListener);

    }

    private void createListing() {
        if (!syaratPertama.isChecked()) {
            Toast.makeText(getApplicationContext(), "Ketentuan perlu dicentang", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!syaratKedua.isChecked()) {
            Toast.makeText(getApplicationContext(), "Ketentuan perlu dicentang", Toast.LENGTH_SHORT).show();
            return;
        }

        insertListingToDatabase();
    }

    private void insertListingToDatabase() {
        createdDate = getDateTime();
        databaseListing = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("listings")
                .child(userId);

        String listingId = databaseListing.push().getKey();
        Listing listingDonor = new Listing(createdDate, listingId, userId, userFullName, userEmail,
                userPhone, userLocation, userBlood);

        databaseListing.child(listingId).setValue(listingDonor)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), userFullName, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Gagal membuat listing", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void readUserAttribute() {
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
                userEmail = attribute.child("userEmail").getValue(String.class);
                userPhone = attribute.child("userPhone").getValue(String.class);
                userLocation = attribute.child("userAttribute").getValue(String.class);
                userBlood = attribute.child("userBlood").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error"+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private static String getDateTime() {
        try {
            Date dateTime = new Date();
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            return sfd.format(dateTime);
        } catch (Exception error) {
            return "dateTime";
        }
    }

}