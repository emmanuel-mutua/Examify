package com.emmutua.examify.home.lecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Adapter;

import com.emmutua.examify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class myStudents extends AppCompatActivity {
    RecyclerView recyclerView;
    //StudentsAdapter studentsAdapter;
//    List<Student> studentsList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_students);
        recyclerView = findViewById(R.id.lecturer_recyclerView);
//        studentsList = new ArrayList<>();
//        studentsAdapter = new StudentsAdapter(studentsList);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(studentsAdapter);

        fetchAllMyStudentsFromFirebase();

    }
    void fetchAllMyStudentsFromFirebase(){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Fetch documents from student collection
        firebaseFirestore.collection("students")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
//                        studentsList.clear();
                        for(QueryDocumentSnapshot studentDocument: task.getResult()){
                            String studentID = studentDocument.getId();
                            //Fetch the user document for the studentId from the users collection
                            firebaseFirestore.collection("users").document(studentID)
                                    .get().addOnCompleteListener(userTask ->{
                                        if (userTask.isSuccessful()) {
                                            DocumentSnapshot documentSnapshot= userTask.getResult();
                                            if(documentSnapshot.exists() &&
                                                    documentSnapshot.getString("uid").equals(studentID)){
                                                String studentName = documentSnapshot.getString("fullName");
                                                String studentRegNo = documentSnapshot.getString("regNo");
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}