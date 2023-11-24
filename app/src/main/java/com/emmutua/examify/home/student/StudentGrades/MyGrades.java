package com.emmutua.examify.home.student.StudentGrades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.emmutua.examify.home.admin.StudentsPerformanceBar.ReusableClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyGrades extends AppCompatActivity {
    private GradesAdapter allGradesAdapter;
    private List<Grades> gradesList;
    TextView overallMarksTextView, recommendationTextView, meanMarksTextView;
    TextView overallGrade;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grades);
        recommendationTextView = findViewById(R.id.Recommendation_textView);
        meanMarksTextView = findViewById(R.id.mean_marks_text_view);
        radioGroup = findViewById(R.id.myGradesRadioGroup);
        RecyclerView allGradesRecyclerView = findViewById(R.id.units_recyclerView);
        overallMarksTextView = findViewById(R.id.overall_mark);
        overallGrade = findViewById(R.id.total_grade);
        allGradesAdapter = new GradesAdapter(gradesList);
        allGradesRecyclerView.setAdapter(allGradesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        allGradesRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(allGradesRecyclerView.getContext(), layoutManager.getOrientation());
        allGradesRecyclerView.addItemDecoration(dividerItemDecoration);

        radioGroup.setOnCheckedChangeListener((group,checkId)->{
            ReusableClass reusableClass = new ReusableClass(radioGroup, this);
            allGradesAdapter.notifyDataSetChanged();
            String selectedSemester = reusableClass.getSelectedSemester();
            if (!selectedSemester.isEmpty()) {
                fetchData(selectedSemester);
            }
        });

    }
    // Update UI with total marks
    private void updateUIWithTotalMarks() {
        Integer size = allGradesAdapter.gradesList.size();
        if (size > 0) {
            Integer totalMarks = allGradesAdapter.calculateTotalMarks();
            Integer meanTotalMarks = totalMarks / size;
            String grade = calculateGrade(meanTotalMarks);
            String recommendation = getRecommendations(grade);
            overallMarksTextView.setText("Overall Marks: " + totalMarks);
            meanMarksTextView.setText("Mean Marks: " + meanTotalMarks);
            recommendationTextView.setText(recommendation);
            overallGrade.setText("Grade: " + grade);
        } else {
            overallMarksTextView.setText("");
            meanMarksTextView.setText("");
            recommendationTextView.setText("");
            overallGrade.setText("");
            Toast.makeText(this, "You did not apply for any course", Toast.LENGTH_SHORT).show();
        }
    }
    String calculateGrade(Integer meanTotalMarks){
        if (meanTotalMarks >= 70) {
            return "A";
        } else if (meanTotalMarks >= 60) {
            return "B";
        } else if (meanTotalMarks >= 50) {
            return "C";
        } else if (meanTotalMarks >= 40) {
            return "D";
        } else if(meanTotalMarks >= 1 && meanTotalMarks <= 39) {
            return "E";
        }else {
            return "Missing mark";
        }
    }
    String getRecommendations(String grade){
       if ("E".equals(grade)){
           return "Fail";
       }else if("Missing mark".equals(grade)) {
           return "Missing mark";
       }else {
           return "Pass";
       }
    }

    private void fetchData(String unitStage) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("students_registered_units")
                .whereEqualTo("studentUid", currentUser.getUid())
                .whereEqualTo("unitStage",unitStage)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Grades> userList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Grades userGrades = document.toObject(Grades.class);
                    userList.add(userGrades);
                }
                updateUsersList(userList);
                updateUIWithTotalMarks();
            } else {
                // Handle the error
                String errorMessage = task.getException().getMessage();
                Log.d("FetchUsersError", errorMessage);
            }
        });
    }

    private void updateUsersList(List<Grades> userList) {
        // Assuming you have a member variable 'allUsersAdapter' in your activity
        if (allGradesAdapter != null) {
            allGradesAdapter.setUserList(userList);
            allGradesAdapter.notifyDataSetChanged();
        }
    }
}