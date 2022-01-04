package com.example.plasion.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.plasion.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    AppCompatButton nextButton;
    EditText inputEmail, inputPassword, inputRepeatPassword;

    public static String USER_EMAIL;
    public static String USER_PASSWORD;

    private final View.OnClickListener nextOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            inputValidation();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nextButton = (AppCompatButton) findViewById(R.id.next_button);
        inputEmail = (EditText) findViewById(R.id.alamat_surel_register);
        inputPassword = (EditText) findViewById(R.id.kata_sandi_register);
        inputRepeatPassword = (EditText) findViewById(R.id.kata_sandi_ulangi_register);

        nextButton.setOnClickListener(nextOnClickListener);

    }

    private void inputValidation() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String repeatPassword = inputRepeatPassword.getText().toString().trim();

        inputEmail.setError(null);
        inputPassword.setError(null);
        inputRepeatPassword.setError(null);

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Alamat surel wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Kata sandi wajib diisi");
            return;
        }

        if (TextUtils.isEmpty(repeatPassword)) {
            inputRepeatPassword.setError("Kata sandi wajib diisi");
            return;
        }

        if (!password.equals(repeatPassword)) {
            inputRepeatPassword.setError("Kata sandi tidak sama");
            return;
        }

        nextButtonClicked(email, password);

    }

    private void nextButtonClicked(String email, String password) {
        Intent intent = new Intent(RegisterActivity.this, FinalRegisterActivity.class);

        intent.putExtra("USER_EMAIL", email);
        intent.putExtra("USER_PASSWORD", password);

        startActivity(intent);
    }
}