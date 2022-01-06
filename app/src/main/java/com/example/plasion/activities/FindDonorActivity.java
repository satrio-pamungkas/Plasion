package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plasion.R;
import com.example.plasion.adapter.ListingAdapter;
import com.example.plasion.models.Listing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Objects;

public class FindDonorActivity extends AppCompatActivity {

    private String userId;
    private String DATABASE_URL;

    DatabaseReference listing;
    FirebaseUser user;

    private ArrayList<Listing> listingArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_donor);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);
        listing = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("listings")
                .child(userId);

        listingArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    @Override
    protected void onStart() {
        super.onStart();

        listing.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listingArrayList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Listing list = postSnapshot.getValue(Listing.class);
                    listingArrayList.add(list);
                }
                ListingAdapter listingAdapter = new ListingAdapter(FindDonorActivity.this, listingArrayList);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FindDonorActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(listingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}