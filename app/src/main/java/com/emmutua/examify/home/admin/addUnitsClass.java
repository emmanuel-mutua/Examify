package com.emmutua.examify.home.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.emmutua.examify.home.admin.Units;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;

public class addUnitsClass extends AppCompatActivity {
    Spinner spinner;
    EditText unitNameEditText, unitCodeEditText,
            unitLecturerEditText,semesterEditText;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        unitNameEditText = findViewById(R.id.unit_name);
        unitCodeEditText = findViewById(R.id.unit_code);
        unitLecturerEditText = findViewById(R.id.unit_lecturer);
        semesterEditText = findViewById(R.id.semester_stage);
        submitButton = findViewById(R.id.submit_units_btn);
       /*set the spinner to be able to enter diffrent sem stages in an array
        and allow user to select only one option at a time*/
     spinner = findViewById(R.id.role_spinner);
        String[] options = {"Y1S1", "Y1S2", "Y2S1", "Y2S2",
                "Y3S1", "Y3S2", "Y4S1", "Y4S2"};
        ArrayAdapter<String> adapter = new
                ArrayAdapter<>(this, android.R.layout.
                simple_spinner_dropdown_item, options);
        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // on button click, get the values entered by the user , validate them and send them to firestore database
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    SendUnitDetailsToFirebase();
                }else {
                    return;
                }
            }
        });

    }
    // get the values entered by the user and send them to firestore database
    void SendUnitDetailsToFirebase() {
        String unitName = unitNameEditText.getText().toString();
        String unitCode = unitCodeEditText.getText().toString();
        String unitLecturer = unitLecturerEditText.getText().toString();
        String semester = semesterEditText.getText().toString();
        String role = spinner.getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Units unit = new Units(unitName, unitCode, unitLecturer, semester, role);

        // Reference to the "units" collection in Firestore, with the document ID being unitCode
        DocumentReference documentReference = db.collection("units").document(unitCode);

        // Set the unit data in Firestore
        documentReference.set(unit)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getApplicationContext(), "Unit added successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields after successful addition
                    unitNameEditText.setText("");
                    unitCodeEditText.setText("");
                    unitLecturerEditText.setText("");
                    semesterEditText.setText("");
                    spinner.setSelection(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // method for validating user input and ensuring the fields are not empty
    boolean validateInput() {
        String unitName = unitNameEditText.getText().toString();
        String unitCode = unitCodeEditText.getText().toString();
        String unitLecturer = unitLecturerEditText.getText().toString();
        String semester = semesterEditText.getText().toString();
        String role = spinner.getSelectedItem().toString();

        if (unitName.isEmpty()) {
            unitNameEditText.setError("Unit name is required");
            return false;
        } else if (unitCode.isEmpty()) {
            unitCodeEditText.setError("Unit code is required");
            return false;
        } else if (unitLecturer.isEmpty()) {
            unitLecturerEditText.setError("Unit lecturer is required");
            return false;
        } else if (semester.isEmpty()) {
            semesterEditText.setError("Semester is required");
            return false;
        } else if (role.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}