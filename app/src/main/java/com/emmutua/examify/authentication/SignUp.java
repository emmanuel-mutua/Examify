package com.emmutua.examify.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText emailedittext, passwordedittext, confirmpasswordedittext, fullNameEditText, regNo_pf;
    Button createaccbtn;

    RadioGroup radioGroup;
    TextView logintextview;
    ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        fullNameEditText = findViewById(R.id.fullNameEditText);
        regNo_pf = findViewById(R.id.reg_no);
        emailedittext = findViewById(R.id.email);
        passwordedittext = findViewById(R.id.psw);
        confirmpasswordedittext = findViewById(R.id.confirm_psw);
        createaccbtn = findViewById(R.id.create_account_btn);
        logintextview = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_circular);
        radioGroup = findViewById(R.id.role_radio_group);

        logintextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        createaccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    void createAccount() {
        String email = emailedittext.getText().toString();
        String password = passwordedittext.getText().toString();
        String confirmPassword = confirmpasswordedittext.getText().toString();
        String fullName = fullNameEditText.getText().toString();
        String regNo = regNo_pf.getText().toString();

        int selectedRoleId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRoleId);
        String selectedRole = selectedRadioButton.getText().toString();

        boolean isValid = validateInput(email, password, confirmPassword, fullName, regNo);

        if (isValid) {
            progressIndicator(true);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressIndicator(false);
                            if (task.isSuccessful()) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                if (user != null) {
                                    // Send email verification
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> emailTask) {
                                                    if (emailTask.isSuccessful()) {
                                                        String uid = user.getUid();
                                                        // Email sent successfully
                                                        User newUser = new User(uid,email, fullName, regNo, selectedRole);
                                                        saveUserToFirestore(newUser);
                                                        showToast("Account created successfully. Check your email to verify.");
                                                        finish();
                                                    } else {
                                                        showToast("Failed to send verification email.");
                                                    }
                                                }
                                            });
                                }
                            } else {
                                showToast("Account creation failed: " + task.getException().getMessage());
                            }
                        }
                    });
        }
    }

    void saveUserToFirestore(User user) {
        firestore.collection("users")
                .add(user)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // User data saved to Firestore
                        } else {
                            showToast("Failed to save user data to Firestore.");
                        }
                    }
                });
    }

    boolean validateInput(String email, String password, String confirmPassword, String fullName, String regNo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email address");
            return false;
        }

        if (password.length() < 8) {
            showToast("Password must be at least 8 characters");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return false;
        }

        if (fullName.isEmpty()) {
            showToast("Full name is required");
            return false;
        }

        if (regNo.isEmpty()) {
            showToast("Registration number is required");
            return false;
        }

        return true;
    }

    void progressIndicator(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            createaccbtn.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            createaccbtn.setEnabled(true);
        }
    }

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
