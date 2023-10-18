package com.emmutua.examify.authentication;

import static com.emmutua.examify.authentication.utility.showToast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.emmutua.examify.home.admin.admin_homescreen;
import com.emmutua.examify.home.lecture.lecturer_homescreen;
import com.emmutua.examify.home.student.student_homescreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    TextView signUpText;
    EditText emailedittext, passwordedittext;
    Button login_button;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        passwordedittext = findViewById(R.id.login_psw);
        login_button = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progress_circular);
        emailedittext = findViewById(R.id.login_email);
        signUpText = findViewById(R.id.sign_in_button);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    void signIn() {
        String email = emailedittext.getText().toString();
        String password = passwordedittext.getText().toString();

        boolean isValid = validateInput(email, password);
        if (!isValid) {
            return;
        } else {
                signInToFirebase(email, password);
        }
    }

    void signInToFirebase(String email, String password) {
        showProgressBar(true);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                showProgressBar(false);
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser != null && currentUser.isEmailVerified()) {
                        // Fetch the user's role
                        utility.fetchUserRole(new UserRoleCallback() {
                            @Override
                            public void onUserRoleFetched(String role) {
                                if (role != null) {
                                    if (role.equals("Student")) {
                                        // Navigate to the Student homepage
                                        utility.showToast(Login.this,"Student loggedIn");
                                        startActivity(new Intent(Login.this, student_homescreen.class));
                                        finish();
                                    } else if (role.equals("Lecturer")) {
                                        // Navigate to the lecturer homepage
                                        utility.showToast(Login.this, "Lecturer loggedIn");
                                        startActivity(new Intent(Login.this, lecturer_homescreen.class));
                                        finish();
                                    }
                                        else if (role.equals("admin")) {
                                            // Navigate to the lecturer homepage
                                            utility.showToast(Login.this,"Lecturer loggedIn");
                                            startActivity(new Intent(Login.this, admin_homescreen.class));
                                            finish();
                                    } else {
                                        utility.showToast(Login.this,"Unknown role: " + role);
                                    }
                                } else {
                                    utility.showToast(Login.this,"User role not found.");
                                }
                            }
                        });
                    } else {
                        utility.showToast(Login.this,"Please verify your email.");
                    }
                } else {
                    utility.showToast(Login.this,task.getException().getLocalizedMessage());
                }
            }
        });
    }



    boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailedittext.setError("Please enter email");
            return false;
        }
        if (password.isEmpty()) {
            passwordedittext.setError("Please enter password");
            return false;
        }
        return true;
    }


    void showProgressBar(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            login_button.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            login_button.setVisibility(View.VISIBLE);
        }
    }
}
