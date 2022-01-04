package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.plasion.R;
import com.example.plasion.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FinalRegisterActivity extends AppCompatActivity {

    private String userId, email, password, fullName, phoneNumber, location, blood;
    private AppCompatButton registerButton;
    private EditText inputFullName, inputPhone, inputLocation;
    private Spinner inputBlood;
    private FirebaseAuth mAuth;

    private static final String DATABASE_URL = "https://plasion.com";

    DatabaseReference databaseUser;

    private final View.OnClickListener registerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            registerButtonClicked();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_register);

        mAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance(DATABASE_URL).getReference("users");

        inputFullName = (EditText) findViewById(R.id.nama_lengkap);
        inputPhone = (EditText) findViewById(R.id.nomor_telepon);
        inputLocation = (EditText) findViewById(R.id.domisili);
        inputBlood = (Spinner) findViewById(R.id.golongan_darah);
        registerButton = (AppCompatButton) findViewById(R.id.submit_register_button);

        registerButton.setOnClickListener(registerOnClickListener);
    }

    private void registerButtonClicked() {
        Bundle extras = getIntent().getExtras();

        email = extras.getString("USER_EMAIL");
        password = extras.getString("USER_PASSWORD");
        fullName = inputFullName.getText().toString().trim();
        phoneNumber = inputPhone.getText().toString().trim();
        location = inputLocation.getText().toString().trim();
        blood = inputBlood.getSelectedItem().toString();

        if (TextUtils.isEmpty(fullName)) {
            inputFullName.setError("Alamat surel wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            inputPhone.setError("Kata sandi wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(location)) {
            inputLocation.setError("Kata sandi wajib diisi");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(FinalRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Berhasil membuat akun basic", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(FinalRegisterActivity.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Gagal membuat akun", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        setAdditionalInformation();
    }

    private void setAdditionalInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                userId = profile.getUid();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Akun null", Toast.LENGTH_SHORT).show();
        }

        User pengguna = new User(userId, fullName, email, phoneNumber, location, blood);
        databaseUser.child(userId).setValue(pengguna);

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Berhasil membuat akun", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FinalRegisterActivity.this, "Error"+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}