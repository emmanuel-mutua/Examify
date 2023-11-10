package com.emmutua.examify.home.lecture;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


import com.emmutua.examify.R;
import com.emmutua.examify.authentication.Login;
import com.google.firebase.auth.FirebaseAuth;

public class lecturer_homeScreen extends AppCompatActivity {
    TextView goodMorningLecturerTextview;
    Button myStudentsButton,addMarksButton,lecturerLogOutButton;
    Toolbar LecturerToolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_homescreen);
        goodMorningLecturerTextview = findViewById(R.id.Lecturer_greetingTextView);
        myStudentsButton = findViewById(R.id.Lecturer_myStudents_button);
        LecturerToolbar = findViewById(R.id.Lecturer_toolbar);
        setSupportActionBar(LecturerToolbar);
        getSupportActionBar().setTitle("Lecturer Dashboard");
        addMarksButton = findViewById(R.id.Lecturer_AddMarks_button);
        lecturerLogOutButton = findViewById(R.id.Lecturer_log_out_button);
        lecturerLogOutButton.setOnClickListener(v ->{
            AlertDialog.Builder builder = new AlertDialog
                    .Builder(lecturer_homeScreen.this);
            builder.setTitle("Log Out");
            builder.setMessage("Are you sure you want to log out?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(lecturer_homeScreen.this, Login.class);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("No",(dialog, which) ->{
                dialog.dismiss();
                    });
         AlertDialog dialog = builder.create();
         dialog.show();
        });
        myStudentsButton.setOnClickListener(v ->{
            Intent intent = new Intent(lecturer_homeScreen.this, myStudents.class);
            startActivity(intent);
        });
    }
}