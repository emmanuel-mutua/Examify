package com.emmutua.examify.home.student.StudentGrades;

// GradesAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmutua.examify.R;

import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradesViewHolder> {

    private List<Grades> gradesList;

    public GradesAdapter(List<Grades> gradesList) {
        this.gradesList = gradesList;
    }

    @NonNull
    @Override
    public GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_grades, parent, false);
        return new GradesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradesViewHolder holder, int position) {
        // Bind data to the ViewHolder
        Grades grades = gradesList.get(position);
        holder.bind(grades);
    }

    @Override
    public int getItemCount() {
        return gradesList.size();
    }

    // ViewHolder class
    public class GradesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView SemesterStageTextView,RecommendationTextView,MeanGradeTextView;
         TextView totalMarksTextView;
        ListView SemesterCoursesListView;
        // Add more TextViews for other details
        public GradesViewHolder(@NonNull View itemView) {
            super(itemView);
            SemesterCoursesListView = itemView.findViewById(R.id.semesterCoursesListView);
            SemesterStageTextView = itemView.findViewById(R.id.Semester_stage_per_semester);
            RecommendationTextView = itemView.findViewById(R.id.Recommendation_textView);
            totalMarksTextView = itemView.findViewById(R.id.totalMarksForAllCourses);
            MeanGradeTextView = itemView.findViewById(R.id.Mean_grade_for_allTheCourses);
            // Initialize other TextViews

            itemView.setOnClickListener(this);
        }

        public void bind(Grades grades) {
            // Bind data to the TextViews
            SemesterStageTextView.setText(grades.getUnitStage());
            totalMarksTextView.setText("Total Marks: " + grades.getUnitTotalMarks());
            // Bind other details
        }

        @Override
        public void onClick(View view) {
            // Handle card click event
            // Implement logic to show AlertDialog with detailed marks for the selected unit
            // For simplicity, I'll assume a method showUnitDetailsAlertDialog()
            showUnitDetailsAlertDialog(getAdapterPosition());
        }
    }

    private void showUnitDetailsAlertDialog(int position) {
        // Implement AlertDialog logic to show details for the selected unit
        // Use gradesList.get(position) to get the selected Grades object
        // Show assignment marks, cat marks, exam marks, total marks, and grade
    }
}

