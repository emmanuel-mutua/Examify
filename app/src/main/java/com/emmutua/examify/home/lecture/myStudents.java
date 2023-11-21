package com.emmutua.examify.home.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class myStudents extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAdapter studentsAdapter;
    List<StudentModel> studentsList;
    LecturerHomeViewModel lecturerHomeViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);
        lecturerHomeViewModel = new ViewModelProvider(this).get(LecturerHomeViewModel.class);
        recyclerView = findViewById(R.id.lecturer_recyclerView);
        studentsList = new ArrayList<>();
        studentsAdapter = new StudentAdapter(studentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentsAdapter);
        lecturerHomeViewModel.getUnitCode().observe(this, unitCode -> {
            fetchAllMyStudentsFromFirebase(unitCode);
        });
        studentsAdapter.setOnItemClickListener(student -> {
            showEditMarksDialog(student);
        });
    }
    private void showEditMarksDialog(StudentModel student) {
        // Create and show a dialog with the student details
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Marks");
        View dialogView = getLayoutInflater().inflate(R.layout.edit_marks_dialog, null);
        builder.setView(dialogView);

        // Initialize EditTexts in the dialog
        EditText assign1MarksEditText = dialogView.findViewById(R.id.assign1MarksEditText);
        EditText assign2MarksEditText = dialogView.findViewById(R.id.assign2MarksEditText);
        EditText cat1MarksEditText = dialogView.findViewById(R.id.cat1MarksEditText);
        EditText cat2MarksEditText = dialogView.findViewById(R.id.cat2MarksEditText);
        EditText examMarksEditText = dialogView.findViewById(R.id.examMarksEditText);

        // Set the existing marks in the EditTexts
        assign1MarksEditText.setText(String.valueOf(student.getUnitAssign1Marks()));
        assign2MarksEditText.setText(String.valueOf(student.getUnitAssign2Marks()));
        cat1MarksEditText.setText(String.valueOf(student.getUnitCat1Marks()));
        cat2MarksEditText.setText(String.valueOf(student.getUnitCat2Marks()));
        examMarksEditText.setText(String.valueOf(student.getUnitExamMarks()));

        // Check if each field has 0 marks, and hide them if true
        checkAndHideZeroMarks(assign1MarksEditText, student.getUnitAssign1Marks());
        checkAndHideZeroMarks(assign2MarksEditText, student.getUnitAssign2Marks());
        checkAndHideZeroMarks(cat1MarksEditText, student.getUnitCat1Marks());
        checkAndHideZeroMarks(cat2MarksEditText, student.getUnitCat2Marks());
        checkAndHideZeroMarks(examMarksEditText, student.getUnitExamMarks());

        // Set positive and negative buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update the student model with the new marks
            student.setUnitAssign1Marks(Integer.parseInt(assign1MarksEditText.getText().toString()));
            student.setUnitAssign2Marks(Integer.parseInt(assign2MarksEditText.getText().toString()));
            student.setUnitCat1Marks(Integer.parseInt(cat1MarksEditText.getText().toString()));
            student.setUnitCat2Marks(Integer.parseInt(cat2MarksEditText.getText().toString()));
            student.setUnitExamMarks(Integer.parseInt(examMarksEditText.getText().toString()));

            // Update the Firebase Firestore document
            updateMarksInFirestore(student);

            // Notify the adapter that the data set has changed
            studentsAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
        // Check if all EditText fields are gone, and show the message
        boolean allMarksAdded = student.getUnitAssign1Marks() > 0
                && student.getUnitAssign1Marks() >0
                && student.getUnitAssign2Marks() >0
                && student.getUnitCat1Marks() >0
                && student.getUnitCat2Marks() >0
                && student.getUnitExamMarks() >0;
        TextView allMarksAddedTextView = dialogView.findViewById(R.id.allMarksAddedTextView);
        allMarksAddedTextView.setVisibility(allMarksAdded ? View.VISIBLE : View.GONE);
    }

    private void updateMarksInFirestore(StudentModel student) {
        Log.d("UpdateMarks", "updateMarksInFirestore: ");
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        // Specify your Firestore collection
        String collectionName = "students_registered_units";

        // Create a reference to the Firestore collection
        CollectionReference collectionReference = firebaseFirestore.collection(collectionName);

        // Build a query to find the document with the matching studentId and unitName
        collectionReference
                .whereEqualTo("studentUid", student.getStudentUid())
                .whereEqualTo("unitCode", student.getUnitCode())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Update the document with the new marks
                            collectionReference.document(document.getId())
                                    .update(
                                            "unitAssign1Marks", student.getUnitAssign1Marks(),
                                            "unitAssign2Marks", student.getUnitAssign2Marks(),
                                            "unitCat1Marks", student.getUnitCat1Marks(),
                                            "unitCat2Marks", student.getUnitCat2Marks(),
                                            "unitExamMarks", student.getUnitExamMarks()
                                    )
                                    .addOnSuccessListener(aVoid -> {
                                        // Document updated successfully
                                        Log.d("Firestore", "DocumentSnapshot successfully updated!");
                                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle errors
                                        Log.e("Firestore", "Error updating document", e);
                                    });
                        }
                    } else {
                        // Handle errors
                        Log.e("Firestore", "Error getting documents: ", task.getException());
                    }
                });
    }


    void fetchAllMyStudentsFromFirebase(String unitCode) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference allStudents = firebaseFirestore.collection("students_registered_units");

        allStudents.whereEqualTo("unitCode", unitCode)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    studentsList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Retrieve data from the document
                        String registrationNumber = document.getString("registrationNumber");
                        String studentName = document.getString("studentName");
                        String studentUid = document.getString("studentUid");
                        Integer unitAssign1Marks = document.getLong("unitAssign1Marks").intValue();
                        Integer unitAssign2Marks = document.getLong("unitAssign2Marks").intValue();
                        Integer unitCat1Marks = document.getLong("unitCat1Marks").intValue();
                        Integer unitCat2Marks = document.getLong("unitCat2Marks").intValue();
                        String unitCodeFromFirestore = document.getString("unitCode");
                        String unitDepartment = document.getString("unitDepartment");
                        Integer unitExamMarks = document.getLong("unitExamMarks").intValue();
                        String unitLecturer = document.getString("unitLecturer");
                        String unitName = document.getString("unitName");
                        String unitStage = document.getString("unitStage");
                        Integer unitTotalMarks = document.getLong("unitTotalMarks").intValue();

                        // Now, you can use the retrieved data as needed
                        // For example, you can create a StudentModel object and add it to a list
                        if (!isStudentInList(registrationNumber, studentsList)) {
                            StudentModel student = new StudentModel( studentUid,unitCodeFromFirestore,studentName, registrationNumber, unitAssign1Marks, unitAssign2Marks, unitCat1Marks, unitCat2Marks, unitExamMarks, false);
                            studentsList.add(student);
                        }


                        // Add more fields to the StudentModel as needed
                        // Do something with the student data, such as adding it to a list or updating UI
                    }
                    studentsAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle any errors that occur
                    Log.e("TAG", "Error getting documents: ", e);
                });


    }

    private boolean isStudentInList(String studentRegNo, List<StudentModel> studentsList) {
        for (StudentModel i : studentsList) {
            if (i.getRegistrationNumber().equals(studentRegNo)) {
                return true;
            }
        }
        return false;
    }
    private void checkAndHideZeroMarks(EditText editText, int marks) {
        if (marks != 0) {
            editText.setVisibility(View.GONE);
        }
    }
}


