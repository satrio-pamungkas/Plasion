package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    private EditText fullName, phoneNumber, location;
    private TextView displayName;
    private AppCompatButton updateButton;
    private String userFirstName, userId;
    private String inputFullName, inputPhone, inputLocation;
    private String DATABASE_URL;

    DatabaseReference userAttribute;
    FirebaseUser user;

    private final View.OnClickListener updateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inputFullName = fullName.getText().toString().trim();
            inputPhone = phoneNumber.getText().toString().trim();
            inputLocation = location.getText().toString().trim();

            updateUserInformation();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        displayName = (TextView) findViewById(R.id.nama_depan);
        fullName = (EditText) findViewById(R.id.nama_lengkap_profil);
        phoneNumber = (EditText) findViewById(R.id.nomor_telepon_profil);
        location = (EditText) findViewById(R.id.domisili_profil);
        updateButton = (AppCompatButton) findViewById(R.id.submit_update_button);

        updateButton.setOnClickListener(updateClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();

        userFirstName = getUserFirstName().replaceAll(" .*","");
        displayName.setText(userFirstName);
        fullName.setText(extras.getString("USER_FULL_NAME"));
        phoneNumber.setText(extras.getString("USER_PHONE"));
        location.setText(extras.getString("USER_LOCATION"));

    }

    private String getUserFirstName() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        return user.getDisplayName();
    }

    private void updateUserInformation() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        DATABASE_URL = getResources().getString(R.string.database_url);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("userFullName", inputFullName);
        data.put("userPhone", inputPhone);
        data.put("userLocation", inputLocation);

        userAttribute = FirebaseDatabase.getInstance(DATABASE_URL)
                .getReference("users")
                .child(userId);

        userAttribute.child("userPhone").setValue(inputPhone);
        UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(inputFullName)
                .build();

        user.updateProfile(updateProfile);

        userAttribute.updateChildren(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Berhasil memperbarui", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Gagal memperbarui", Toast.LENGTH_LONG).show();
                    }
                });
    }

}