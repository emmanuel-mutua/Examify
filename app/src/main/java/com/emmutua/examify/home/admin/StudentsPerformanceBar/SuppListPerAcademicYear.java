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

public class SuppListPerAcademicYear extends AppCompatActivity {
    private ListView SuppListViewPerAcademicYear;
    private List<String> SuppListPerAcademicYear;
    private ArrayAdapter<String> SuppListAdapterPerAcademicYear;
    Set<String> studentUidSPerAcademicYear;
    RadioGroup SuppListAcademicYearRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supp_list_per_academic_year);
        SuppListViewPerAcademicYear = findViewById(R.id.SuppListViewPerAcademicYear);
        SuppListPerAcademicYear = new ArrayList<>();
        studentUidSPerAcademicYear = new HashSet<>();
        SuppListAdapterPerAcademicYear = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        SuppListViewPerAcademicYear.setAdapter(SuppListAdapterPerAcademicYear);
        SuppListAcademicYearRadioGroup = findViewById(R.id.SuppListRadioGroup);
        ReusableClass reusableClass = new ReusableClass(SuppListAcademicYearRadioGroup, this);
        SuppListAcademicYearRadioGroup.setOnCheckedChangeListener((group, checkId) -> {
            SuppListPerAcademicYear.clear();
            SuppListAdapterPerAcademicYear.clear();
            SuppListAdapterPerAcademicYear.notifyDataSetChanged();
            String selectedAcademicYear = reusableClass.getSelectedAcademicYear();
            if (!selectedAcademicYear.isEmpty()) {
                fetchStudentsMarks(selectedAcademicYear);
            }
        });
    }
    void fetchStudentsMarks(String selectedAcademicYear) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");

        // Fetch all semester stages within the selected academic year
        collectionReference
                .whereGreaterThanOrEqualTo("unitStage", selectedAcademicYear + "S1")
                .whereLessThanOrEqualTo("unitStage", selectedAcademicYear + "S2")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String studentUid = documentSnapshot.getString("studentUid");
                        studentUidSPerAcademicYear.add(studentUid);
                    }
                    // Fetch units for semester 2 (S2)
                    fetchUnitsForSemester(collectionReference, selectedAcademicYear, "S2");
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents for semester 1: ", e);
                });
    }

    void fetchStudentDetails(String studentId, String academicYearAndSemester) {
        boolean AppliedSpecial = false;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("students_registered_units");
        collectionReference
                .whereEqualTo("unitStage", academicYearAndSemester)
                .whereEqualTo("studentUid", studentId)
                .whereEqualTo("appliedSpecial", AppliedSpecial)
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
                        StudentMark studentMark = new StudentMark(studentName, unitName, unitCode, studentRegNo, assignment1Marks, assignment2Marks, cat1Marks, cat2Marks, examMarks);
                        studentMarks.add(studentMark);
                    }
                    checkSuppList(studentMarks, studentName, studentRegNo);
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents: ", e);
                });
    }

    void fetchUnitsForSemester(CollectionReference collectionReference, String academicYear, String semester) {
        collectionReference
                .whereGreaterThanOrEqualTo("unitStage", academicYear + semester)
                .whereLessThanOrEqualTo("unitStage", academicYear + semester)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String studentUid = documentSnapshot.getString("studentUid");
                        studentUidSPerAcademicYear.add(studentUid);
                    }
                    for (String studentId : studentUidSPerAcademicYear) {
                        fetchStudentDetails(studentId, academicYear + semester);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("TAG", "Error getting documents for semester " + semester + ": ", e);
                });
    }

    void checkSuppList(List<StudentMark> studentMarks, String studentName, String studentRegNo) {
        boolean canAddToFailList = false;
        StringBuilder suppUnits = new StringBuilder();

        for (StudentMark studentMark : studentMarks) {
            String grade = calculateTotalMarksAndGrade(studentMark);
            if ("E".equals(grade)){
                canAddToFailList = true;
                suppUnits.append(studentMark.getUnitCode()).append(" - ").append(studentMark.getUnitName()).append("\n");
            }
        }

        if (canAddToFailList) {
            Log.d("TAG", "Adding to SuppList: " + studentName + " - " + studentRegNo);
            SuppListPerAcademicYear.add("\n" + studentName + " - " + studentRegNo +"\n"
                    + "                      SuppleMentaries.                 " + "\n" + suppUnits );
        }
        updateSuppListView();
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
        } else if(totalMarks >= 1 && totalMarks <= 39) {
            return "E";
        }else {
            return "M";
        }
    }

    private void updateSuppListView() {
        SuppListAdapterPerAcademicYear.clear();
        SuppListAdapterPerAcademicYear.addAll(SuppListPerAcademicYear);
        SuppListAdapterPerAcademicYear.notifyDataSetChanged();
    }
}