package com.example.elimuapp.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.elimuapp.R;

public class welcomepage extends AppCompatActivity {
    Button studentbutton, lecbutton, adminbutton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        studentbutton = findViewById(R.id.studentbtn);
        adminbutton = findViewById(R.id.adminbtn);
        studentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(welcomepage.this, SignUp.class));
            }
        });
        adminbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(welcomepage.this, Login.class));
            }
        });
    }
}