package com.emmutua.examify.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.emmutua.examify.R;

public class welcomepage extends AppCompatActivity {
    Button studentbutton, lecbutton, adminbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(welcomepage.this, Login.class));
                finish(); // Optional: Finish the current activity
            }
        }, 2000);
    }
}