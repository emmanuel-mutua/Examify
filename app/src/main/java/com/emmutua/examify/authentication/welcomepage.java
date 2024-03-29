package com.emmutua.examify.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import com.emmutua.examify.R;
import com.emmutua.examify.home.admin.admin_homeScreen;
import com.emmutua.examify.home.lecture.lecturer_homeScreen;
import com.emmutua.examify.home.student.student_homescreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class welcomepage extends AppCompatActivity {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Button studentbutton, lecbutton, adminbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser != null) {
                    utility.fetchUserRole(new UserRoleCallback() {
                        @Override
                        public void onUserRoleFetched(String role) {
                            if (role != null) {
                                if (role.equals("Student")) {
                                    // Navigate to the Student homepage and clear the activity stack
                                    utility.showToast(welcomepage.this, "Student loggedIn");
                                    Intent studentIntent = new Intent(welcomepage.this, student_homescreen.class);
                                    studentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(studentIntent);
                                    finish(); // Finish the current welcome page activity
                                } else if (role.equals("Lecturer")) {
                                    // Navigate to the lecturer homepage and clear the activity stack
                                    utility.showToast(welcomepage.this, "Lecture loggedIn");
                                    Intent lecturerIntent = new Intent(welcomepage.this, lecturer_homeScreen.class);
                                    lecturerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(lecturerIntent);
                                    finish(); // Finish the current welcome page activity
                                }
                                else if (role.equals("admin")) {
                                    utility.showToast(welcomepage.this, "Admin loggedIn");
                                    Intent adminIntent = new Intent(welcomepage.this, admin_homeScreen.class);
                                    adminIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(adminIntent);
                                    finish();
                                }
                                else {
                                    Intent loginIntent = new Intent(welcomepage.this, Login.class);
                                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(loginIntent);                                }
                            } else {
                                Intent loginIntent = new Intent(welcomepage.this, Login.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginIntent);
                            }
                        }
                    });
                } else {
                    startActivity(new Intent(welcomepage.this, Login.class));
                }
            }
        }, 1000);

    }

}