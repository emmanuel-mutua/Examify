package com.emmutua.examify.home.admin.StudentsPerformanceBar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PassListPerSemester extends AppCompatActivity {
    private ListView passListView;
    private List<StudentMark> passList;
    private ArrayAdapter<String> passListAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list_per_semester);
        passListView = findViewById(R.id.passListView);
        // Call a method for fetching students' marks from Firebase and then calculate their pass list
        passList = new ArrayList<>();
        passListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        passListView.setAdapter(passListAdapter);
        fetchStudentsMarks();


    }
     void fetchStudentsMarks() {
        //fetch students marks from firebase
         FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
         DocumentReference marksDocument = firebaseFirestore.
                 collection("students").document().
                 collection("registered_units").document();
         marksDocument.get().addOnCompleteListener(task -> {
             if(task.isSuccessful()) {
                 DocumentSnapshot documentSnapshot = task.getResult();
                 if(documentSnapshot.exists()){
                     String studentName = documentSnapshot.getString("studentName");
                     String studentRegNo = documentSnapshot.getString("registrationNumber");
                     String assignment1Marks = documentSnapshot.getString("unitAssign1Marks");
                     String assignment2Marks = documentSnapshot.getString("unitAssign2Marks");
                     String cat1Marks = documentSnapshot.getString("unitCat1Marks");
                     String cat2Marks = documentSnapshot.getString("unitCat2Marks");
                     String ExamMarks = documentSnapshot.getString("unitExamMarks");
                     //StudentMarks Object
                     StudentMark studentMark = new StudentMark(
                             studentName,studentRegNo,assignment1Marks,
                             assignment2Marks,cat1Marks,cat2Marks,ExamMarks
                     );
                        calculateTotalMarksAndGrade(studentMark);
                 }else{
//                     Toast.makeText(this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                 }
             }else{
                 Toast.makeText(this, "Details not fetched", Toast.LENGTH_SHORT).show();
             }
         });
    }
     void  calculateTotalMarksAndGrade(StudentMark studentMarks) {
        //parse students marks as integers
         int assignment1Marks = Integer.parseInt(studentMarks.getAssignment1Marks());
         int assignment2Marks = Integer.parseInt(studentMarks.getAssignment2Marks());
         int cat1Marks = Integer.parseInt(studentMarks.getCat1Marks());
         int cat2Marks = Integer.parseInt(studentMarks.getCat2Marks());
         int examMarks = Integer.parseInt(studentMarks.getExamMarks());
         int totalMarks = assignment1Marks + assignment2Marks + cat1Marks + cat2Marks + examMarks;
            String grade = calculateGrade(totalMarks);
         if (!grade.equals("E")) {
             // Add student to the pass list
             passList.add(studentMarks);

             // Update the ListView
             updatePassListView();
         }
    }
    String calculateGrade(int totalMarks){
        if(totalMarks>=70){
            return "A";
    }else if(totalMarks>=60){
        return "B";
        }else if(totalMarks>=50){
            return "C";
    } 
        else if(totalMarks>=40){
            return "D";
    }else 
    {
            return "E";
        }
    }
    private void updatePassListView() {
        // Clear existing items in the adapter
        passListAdapter.clear();

        // Add names and registration numbers of students in the pass list to the adapter
        for (StudentMark studentMark : passList) {
            String listItem = studentMark.getStudentName() + " - " + studentMark.getStudentRegNo();
            passListAdapter.add(listItem);
        }

        // Notify the adapter that the data set has changed
        passListAdapter.notifyDataSetChanged();
    }
}
