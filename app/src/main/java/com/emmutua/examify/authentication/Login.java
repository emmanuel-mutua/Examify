package com.emmutua.examify.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.emmutua.examify.R;

public class Login extends AppCompatActivity {

    TextView signUpText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUpText = findViewById(R.id.sign_in_button);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Login.this, SignUp.class));
            }
        });
    }
}