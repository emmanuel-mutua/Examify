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

public class MissingMarksInAtleastOneCourse extends AppCompatActivity {
    private ListView MissingMarksListView;
    private List<String> MissingMarksList;
    private ArrayAdapter<String> MissingMarksListAdapter;
    private Set<String> studentUidSet;
    private RadioGroup MissingMarksSemesterStageRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_marks_in_atleast_one_course);
        MissingMarksListView = findViewById(R.id.missingMarksInAtLeastOneCourseListView);
        MissingMarksSemesterStageRadioGroup = findViewById(R.id.MissingMarksInAtLeastOneCourseRadioGroup);

        // Initialize data structures
        MissingMarksList = new ArrayList<>();
        studentUidSet = new HashSet<>();
        MissingMarksListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        MissingMarksListView.setAdapter(MissingMarksListAdapter);

        // Set up RadioGroup listener
        ReusableClass reusableClass = new ReusableClass(MissingMarksSemesterStageRadioGroup, this);
        MissingMarksSemesterStageRadioGroup.setOnCheckedChangeListener((group, checkId) -> {
           MissingMarksList.clear();
            MissingMarksListAdapter.clear();
            MissingMarksListAdapter.notifyDataSetChanged();
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
        boolean appliedSpecial = false;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");

        collectionReference.whereEqualTo("unitStage", selectedSemester)
                .whereEqualTo("studentUid", studentId)
                .whereEqualTo("appliedSpecial", appliedSpecial)
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
                    Log.d("TAG", "Selected Semester: " + selectedSemester);
                    Log.d("TAG", "Student UID: " + studentId);
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }

    private void checkPassList(List<StudentMark> studentMarks, String studentName, String studentRegNo) {
        boolean canAddToMissingMarkList = false;
        StringBuilder suppUnits = new StringBuilder();
        for (StudentMark studentMark : studentMarks) {
            String grade = calculateTotalMarksAndGrade(studentMark);
            String missingMarksInfo = getMissingMarksDetails(studentMark);
            Log.d("TAG", "checkPassList: " + grade);
            // Check if the grade is "E"
            if ("M".equals(grade)&& !missingMarksInfo.isEmpty()) {
                canAddToMissingMarkList = true;  // Set the flag to false if any "E" grade is encountered
                suppUnits.append(studentMark.getUnitCode()).append(" - ").append(studentMark.getUnitName()).append("\n").append(missingMarksInfo).append("\n");

            }
        }
        if (canAddToMissingMarkList) {
            Log.d("TAG", "Adding to SuppList: " + studentName + " - " + studentRegNo);
            MissingMarksList.add("\n" + studentName + " - " + studentRegNo +"\n"
                    + "               Units With Missing Marks.                 " + "\n" + suppUnits );
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
        } else if(totalMarks >= 1 && totalMarks <= 39) {
            return "E";
        }else {
            return "M";
        }
    }

    private void updatePassListView() {
        // Remove all occurrences of "-" from the entries in passList


        MissingMarksListAdapter.clear();
        MissingMarksListAdapter.addAll(MissingMarksList);
        MissingMarksListAdapter.notifyDataSetChanged();
    }
    private String getMissingMarksDetails(StudentMark studentMark) {
        StringBuilder details = new StringBuilder();

        if (studentMark.getAssignment1Marks()==null||studentMark.getAssignment1Marks() == 0) {
            details.append("Missing marks in Assignment 1\n");
        }

        if (studentMark.getAssignment2Marks()==null||studentMark.getAssignment2Marks() == 0) {
            details.append("Missing marks in Assignment 2\n");
        }

        if (studentMark.getAssignment1Marks()==null||studentMark.getCat1Marks() == 0) {
            details.append("Missing marks in Cat 1\n");
        }

        if (studentMark.getAssignment2Marks()==null||studentMark.getCat2Marks() == 0) {
            details.append("Missing marks in Cat 2\n");
        }

        if (studentMark.getExamMarks()==null||studentMark.getExamMarks() == 0) {
            details.append("Missing marks in Exam\n");
        }

        return details.toString();
    }

}