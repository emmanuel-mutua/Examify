package com.emmutua.examify.home.admin.StudentsPerformanceBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.emmutua.examify.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FailListPerSemester extends AppCompatActivity {
    private ListView FailListView;
    private List<String> FailList;
    private ArrayAdapter<String> FailListAdapter;
    Set<String> FailstudentUidS;
    RadioGroup FailsemesterRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_list_per_semester);
        FailListView = findViewById(R.id.FailListViewPerSemester);
        FailList = new ArrayList<>();
        FailstudentUidS = new HashSet<>();
        FailListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        FailListView.setAdapter(FailListAdapter);
        FailsemesterRadioGroup = findViewById(R.id.FailListSemesterRadioGroup);
        FailsemesterRadioGroup.setOnCheckedChangeListener((group,checkId)->{
            FailList.clear();
            FailListAdapter.clear();
            ReusableClass reusableClass = new ReusableClass(FailsemesterRadioGroup, this);
            FailListAdapter.notifyDataSetChanged();
            String selectedSemester = reusableClass.getSelectedSemester();
            if (!selectedSemester.isEmpty()) {
                fetchStudentsMarks(selectedSemester);
            }
        });
    }
    void fetchStudentsMarks(String selectedSemester) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");
        collectionReference.whereEqualTo("unitStage", selectedSemester).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String studentUid = documentSnapshot.getString("studentUid");
                FailstudentUidS.add(studentUid);
            }
            for (String studentId : FailstudentUidS) {
                fetchStudentDetails(studentId,selectedSemester);
            }
        }).addOnFailureListener(e -> {
            Log.e("TAG", "Error getting documents: ", e);
        });
    }
    void fetchStudentDetails(String studentId,String selectedSemester) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");
        collectionReference
                .whereEqualTo("unitStage", selectedSemester)
                .whereEqualTo("studentUid", studentId)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    List<StudentMark> studentMarks = new ArrayList<>();
                    String studentName = "";
                    String studentRegNo = "";
                    String unitName = null;
                    String unitCode = null;
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        studentName = documentSnapshot.getString("studentName");
                        studentRegNo = documentSnapshot.getString("registrationNumber");
                        unitCode = documentSnapshot.getString("unitCode");
                        unitName = documentSnapshot.getString("unitName");
                        Integer assignment1Marks = documentSnapshot.getLong("unitAssign1Marks").intValue();
                        Integer assignment2Marks = documentSnapshot.getLong("unitAssign2Marks").intValue();
                        Integer cat1Marks = documentSnapshot.getLong("unitCat1Marks").intValue();
                        Integer cat2Marks = documentSnapshot.getLong("unitCat2Marks").intValue();
                        Integer examMarks = documentSnapshot.getLong("unitExamMarks").intValue();
                        StudentMark studentMark = new StudentMark(studentName, unitName, unitCode, studentRegNo, assignment1Marks, assignment2Marks, cat1Marks, cat2Marks, examMarks);
                        studentMarks.add(studentMark);
                    }
                    checkPassList(studentMarks, studentName, studentRegNo, unitName, unitCode);
                }).addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }
    void checkPassList(List<StudentMark> studentMarks, String studentName,
                       String studentRegNo,String unitName,String unitCode) {
        for (StudentMark studentMark : studentMarks) {
            String grade = calculateTotalMarksAndGrade(studentMark);
            if (grade.equals("E") && !FailList.contains(studentName)) {
                FailList.add(studentName + " - " + studentRegNo + "\n" + "   " + unitCode + "- " + unitName );
                updatePassListView();
                break;  // Break the loop if the student has an "E" grade in any course
            }
        }
    }
    String calculateTotalMarksAndGrade(StudentMark studentMarks) {
        int assignment1Marks = studentMarks.getAssignment1Marks();
        int assignment2Marks = studentMarks.getAssignment2Marks();
        int cat1Marks = studentMarks.getCat1Marks();
        int cat2Marks = studentMarks.getCat2Marks();
        int examMarks = studentMarks.getExamMarks();
        int totalMarks = assignment1Marks + assignment2Marks + cat1Marks + cat2Marks + examMarks;
        return calculateGrade(totalMarks);
    }

    String calculateGrade(int totalMarks) {
        if (totalMarks >= 70) {
            return "A";
        } else if (totalMarks >= 60) {
            return "B";
        } else if (totalMarks >= 50) {
            return "C";
        } else if (totalMarks >= 40) {
            return "D";
        } else {
            return "E";
        }
    }

    private void updatePassListView() {
        FailListAdapter.clear();
        FailListAdapter.addAll(FailList);
        FailListAdapter.notifyDataSetChanged();
    }
}