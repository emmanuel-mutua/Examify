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

public class PassListPerSemester extends AppCompatActivity {
    private ListView passListView;
    private List<String> passList;
    private ArrayAdapter<String> passListAdapter;
    private Set<String> studentUidSet;
    private RadioGroup semesterRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_list_per_semester);

        // Initialize UI components
        passListView = findViewById(R.id.passListView);
        semesterRadioGroup = findViewById(R.id.semesterRadioGroup);

        // Initialize data structures
        passList = new ArrayList<>();
        studentUidSet = new HashSet<>();
        passListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        passListView.setAdapter(passListAdapter);

        // Set up RadioGroup listener
        ReusableClass reusableClass = new ReusableClass(semesterRadioGroup, this);
        semesterRadioGroup.setOnCheckedChangeListener((group, checkId) -> {
            passList.clear();
            passListAdapter.clear();
            passListAdapter.notifyDataSetChanged();
            String selectedSemester = reusableClass.getSelectedSemester();
            if (!selectedSemester.isEmpty()) {
                fetchStudentsMarks(selectedSemester);
            }
        });
    }

    private void fetchStudentsMarks(String selectedSemester) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");

        collectionReference.whereEqualTo("unitStage", selectedSemester)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String studentUid = documentSnapshot.getString("studentUid");
                        studentUidSet.add(studentUid);
                    }
                    for (String studentId : studentUidSet) {
                        fetchStudentDetails(studentId, selectedSemester);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }

    private void fetchStudentDetails(String studentId, String selectedSemester) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");

        collectionReference.whereEqualTo("unitStage", selectedSemester)
                .whereEqualTo("studentUid", studentId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<StudentMark> studentMarks = new ArrayList<>();
                    String studentName = "";
                    String studentRegNo = "";
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        studentName = documentSnapshot.getString("studentName");
                        studentRegNo = documentSnapshot.getString("registrationNumber");
                        String unitCode = documentSnapshot.getString("unitCode");
                        String unitName = documentSnapshot.getString("unitName");
                        Integer assignment1Marks = documentSnapshot.getLong("unitAssign1Marks").intValue();
                        Integer assignment2Marks = documentSnapshot.getLong("unitAssign2Marks").intValue();
                        Integer cat1Marks = documentSnapshot.getLong("unitCat1Marks").intValue();
                        Integer cat2Marks = documentSnapshot.getLong("unitCat2Marks").intValue();
                        Integer examMarks = documentSnapshot.getLong("unitExamMarks").intValue();

                        StudentMark studentMark = new StudentMark(studentName, unitName, unitCode, studentRegNo,
                                assignment1Marks, assignment2Marks, cat1Marks, cat2Marks, examMarks);
                        studentMarks.add(studentMark);
                    }
                    checkPassList(studentMarks, studentName, studentRegNo);
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }

    private void checkPassList(List<StudentMark> studentMarks, String studentName, String studentRegNo) {
        boolean canAddToPassList = true;
        for (StudentMark studentMark : studentMarks) {
            String grade = calculateTotalMarksAndGrade(studentMark);
            // Check if the grade is "E"
            if ("E".equals(grade)) {
                canAddToPassList = false;  // Set the flag to false if any "E" grade is encountered
                break;
            }
        }
        if (canAddToPassList) {
            passList.add(studentName + " - " + studentRegNo);
        }
        updatePassListView();
    }

    private String calculateTotalMarksAndGrade(StudentMark studentMarks) {
        int assignment1Marks = studentMarks.getAssignment1Marks();
        int assignment2Marks = studentMarks.getAssignment2Marks();
        int cat1Marks = studentMarks.getCat1Marks();
        int cat2Marks = studentMarks.getCat2Marks();
        int examMarks = studentMarks.getExamMarks();
        int totalMarks = assignment1Marks + assignment2Marks + cat1Marks + cat2Marks + examMarks;
        return calculateGrade(totalMarks);
    }

    private String calculateGrade(int totalMarks) {
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
        // Remove all occurrences of "-" from the entries in passList
        passList.removeIf(entry -> entry.contains("-") || entry.contains(""));

        passListAdapter.clear();
        passListAdapter.addAll(passList);
        passListAdapter.notifyDataSetChanged();
    }
}
