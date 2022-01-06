package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MyDonorActivity extends AppCompatActivity {

    private TextView dateCreated;
    private AppCompatButton disableButton;
    private String userId, createdDate, listingKey;
    private String DATABASE_URL;

    private Query keyAttribute;

    DatabaseReference listing, dateAttribute, databaseReference;
    FirebaseUser user;

    private final View.OnClickListener disableClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            deleteListing();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donor);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        disableButton = (AppCompatButton) findViewById(R.id.nonaktif_button);
        dateCreated = (TextView) findViewById(R.id.tanggal);
        disableButton.setOnClickListener(disableClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getListingKey();
    }

    private void deleteListing() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);
        listing = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("listings")
                .child(userId);

        listing.removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Berhasil menghapus", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void getDateInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);
        dateAttribute = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("listings")
                .child(userId)
                .child(listingKey);

        dateAttribute.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot attribute) {
                createdDate = attribute.child("createdDate").getValue(String.class);
                dateCreated.setText(createdDate);
                Log.d("TAG",createdDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error"+ error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getListingKey() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);
        databaseReference = FirebaseDatabase.getInstance(DATABASE_URL).getReference("listings");
        keyAttribute = databaseReference
                .child(userId)
                .orderByChild("userId")
                .equalTo(userId);

        keyAttribute.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            listingKey = childSnapshot.getKey();
                            getDateInformation();
                            Log.d("TAG", listingKey);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}