package com.example.plasion.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plasion.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private String email, password;
    private EditText inputEmail, inputPassword;
    private AppCompatButton loginButton;
    private FirebaseAuth mAuth;

    private final View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginButtonClicked();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.alamat_surel_login);
        inputPassword = (EditText) findViewById(R.id.kata_sandi_login);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);

        loginButton.setOnClickListener(loginClickListener);
    }

    private void loginButtonClicked() {
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Alamat surel wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Kata sandi wajib diisi");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            Toast.makeText(LoginActivity.this, "Berhasil terotentikasi", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Gagal otentikasi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}