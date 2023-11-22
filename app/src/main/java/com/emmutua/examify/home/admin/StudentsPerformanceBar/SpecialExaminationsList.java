package com.emmutua.examify.home.admin.StudentsPerformanceBar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class SpecialExaminationsList extends AppCompatActivity {
    private ListView SpecialEXamsListView;
    private List<String> SpecialEXamsList;
    private ArrayAdapter<String> SpecialEXamsListAdapter;
    private Set<String> studentUidSet;
    private RadioGroup SpecialEXamssemesterRadioGroup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_examinations_list);
        SpecialEXamsListView = findViewById(R.id.specialExaminationsListView);
        SpecialEXamssemesterRadioGroup = findViewById(R.id.SpecialExamRadioGroup);

        // Initialize data structures
        SpecialEXamsList = new ArrayList<>();
        studentUidSet = new HashSet<>();
        SpecialEXamsListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        SpecialEXamsListView.setAdapter(SpecialEXamsListAdapter);

        // Set up RadioGroup listener
        ReusableClass reusableClass = new ReusableClass(SpecialEXamssemesterRadioGroup, this);
        SpecialEXamssemesterRadioGroup.setOnCheckedChangeListener((group, checkId) -> {
            SpecialEXamsList.clear();
            SpecialEXamsListAdapter.clear();
            SpecialEXamsListAdapter.notifyDataSetChanged();
            String selectedSemester = reusableClass.getSelectedSemester();
            if (!selectedSemester.isEmpty()) {
                fetchStudentsAppliedForSpecial(selectedSemester);
            }
        });
    }

    private void fetchStudentsAppliedForSpecial(String selectedSemester) {
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
        boolean appliedSpecial = true;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");

        collectionReference.whereEqualTo("unitStage", selectedSemester)
                .whereEqualTo("studentUid", studentId)
                .whereEqualTo("appliedSpecial",appliedSpecial)
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
                    checkSpecialListList(studentMarks, studentName, studentRegNo);
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }
    private void checkSpecialListList(List<StudentMark> studentMarks, String studentName, String studentRegNo) {
        boolean appliedForSpecial = false;
        StringBuilder specialUnits = new StringBuilder();

        for (StudentMark studentMark : studentMarks) {
                appliedForSpecial = true;
                specialUnits.append(studentMark.getUnitCode()).append(" - ").append(studentMark.getUnitName()).append("\n");
        }

        if (appliedForSpecial) {
            SpecialEXamsList.add("\n" + studentName + " - " + studentRegNo + "\n"
                    + "               Units Applied for Special                 " + "\n" + specialUnits);
        }

        updateSpecialExamsListView();
    }

    private void updateSpecialExamsListView() {
        // Remove all occurrences of "-" from the entries in SpecialEXamsList
        SpecialEXamsListAdapter.clear();
        SpecialEXamsListAdapter.addAll(SpecialEXamsList);
        SpecialEXamsListAdapter.notifyDataSetChanged();
    }
}
