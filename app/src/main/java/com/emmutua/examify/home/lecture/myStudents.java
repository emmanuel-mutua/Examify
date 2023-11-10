package com.emmutua.examify.home.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.emmutua.examify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class myStudents extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAdapter studentsAdapter;
    List<StudentModel> studentsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);
        recyclerView = findViewById(R.id.lecturer_recyclerView);
        studentsList = new ArrayList<>();
        studentsAdapter = new StudentAdapter(studentsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentsAdapter);

        fetchAllMyStudentsFromFirebase();

    }
    void fetchAllMyStudentsFromFirebase(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference allStudents = firebaseFirestore.collection("students").document().collection("registered_units");

            allStudents.whereEqualTo("unitCode", "ccs 3553")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        studentsList.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            // Retrieve data from the document
                            String registrationNumber = document.getString("registrationNumber");
                            String studentName = document.getString("studentName");
                            String studentUid = document.getString("studentUid");
                            int unitAssign1Marks = document.getLong("unitAssign1Marks").intValue();
                            int unitAssign2Marks = document.getLong("unitAssign2Marks").intValue();
                            int unitCat1Marks = document.getLong("unitCat1Marks").intValue();
                            int unitCat2Marks = document.getLong("unitCat2Marks").intValue();
                            String unitCodeFromFirestore = document.getString("unitCode");
                            String unitDepartment = document.getString("unitDepartment");
                            int unitExamMarks = document.getLong("unitExamMarks").intValue();
                            String unitLecturer = document.getString("unitLecturer");
                            String unitName = document.getString("unitName");
                            String unitStage = document.getString("unitStage");
                            int unitTotalMarks = document.getLong("unitTotalMarks").intValue();

                            // Now, you can use the retrieved data as needed
                            // For example, you can create a StudentModel object and add it to a list
                            StudentModel student = new StudentModel(studentName, registrationNumber);
                            studentsList.add(student);

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
}