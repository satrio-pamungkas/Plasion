package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private TextView displayName, displayLocation, displayBlood;
    private AppCompatButton emailButton, smsButton, phoneButton;

    private String textDisplayName, textDisplayLocation, textDisplayBlood;
    private String userId, userEmail, userPhone, listingKey;
    private String DATABASE_URL;

    DatabaseReference listingAttribute;
    FirebaseUser user;

    private final View.OnClickListener emailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendEmail();
        }
    };

    private final View.OnClickListener smsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendSms();
        }
    };

    private final View.OnClickListener phoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendPhone();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        Bundle extras = getIntent().getExtras();
        listingKey = extras.getString("LISTING_KEY");

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        displayName = (TextView) findViewById(R.id.nama_lengkap_2);
        displayLocation = (TextView) findViewById(R.id.lokasi);
        displayBlood = (TextView) findViewById(R.id.golongan_darah_2);

        emailButton = (AppCompatButton) findViewById(R.id.hubungi_email);
        smsButton = (AppCompatButton) findViewById(R.id.hubungi_sms);
        phoneButton = (AppCompatButton) findViewById(R.id.hubungi_telepon);

        emailButton.setOnClickListener(emailClickListener);
        smsButton.setOnClickListener(smsClickListener);
        phoneButton.setOnClickListener(phoneClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getListingAttribute();
    }

    private void sendPhone() {
        String addressPhone = "tel:" + userPhone;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(addressPhone));
        startActivity(intent);
    }

    private void sendSms() {
        String addressPhone = "sms:" + userPhone;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(addressPhone));
        intent.putExtra("sms_body", "Saya berminat ");
        startActivity(intent);
    }

    private void sendEmail() {
        String subject = "Menghubungi Donor";
        String message = "Saya berminat untuk mengambil donor anda";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{userEmail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Pilih klien email"));
    }

    private void getListingAttribute() {
        DATABASE_URL = getResources().getString(R.string.database_url);
        listingAttribute = FirebaseDatabase.getInstance(DATABASE_URL).getReference("listings")
                .child(userId)
                .child(listingKey);

        listingAttribute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textDisplayName = snapshot.child("userFullName").getValue(String.class);
                textDisplayLocation = snapshot.child("userLocation").getValue(String.class);
                textDisplayBlood = snapshot.child("userBlood").getValue(String.class);
                userEmail = snapshot.child("userEmail").getValue(String.class);
                userPhone = snapshot.child("userPhone").getValue(String.class);

                setDisplay();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDisplay() {
        String bloodText = "Golongan Darah " + textDisplayBlood;
        displayName.setText(textDisplayName);
        displayLocation.setText(textDisplayLocation);
        displayBlood.setText(bloodText);

    }


}