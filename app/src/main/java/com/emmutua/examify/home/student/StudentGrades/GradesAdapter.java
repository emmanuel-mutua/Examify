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
    List<Grades> gradesList;

    public GradesAdapter(List<Grades> gradesList) {
        this.gradesList = gradesList;
    }
    public void setUserList(List<Grades> userList) {
        this.gradesList = userList;
    }

    public interface OnItemClickListener {
        void onItemClick(Grades grades);
    }

    public void setOnItemClickListener(GradesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    private GradesAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public GradesAdapter.GradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_marks_card, parent, false);
        return new GradesAdapter.GradesViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull GradesAdapter.GradesViewHolder holder, int position) {
        Grades grades = gradesList.get(position);

        // Bind data to the ViewHolder's views
        holder.unitNameTextView.setText(grades.getUnitName());
        holder.unitCodeTextView.setText(grades.getUnitCode());
        holder.totalMarksTextView.setText("Total Mark: " + grades.getUnitTotalMarks() + "  Grade: " + grades.getUnitGrade());
        holder.assign1MarksTextView.setText(String.valueOf(grades.getUnitAssign1Marks()));
        holder.assign2MarksTextView.setText(String.valueOf(grades.getUnitAssign2Marks()));
        holder.cat1MarksTextView.setText(String.valueOf(grades.getUnitCat1Marks()));
        holder.cat2MarksTextView.setText(String.valueOf(grades.getUnitCat2Marks()));
        holder.examMarksTextView.setText(String.valueOf(grades.getUnitExamMarks()));
    }

    @Override
    public int getItemCount() {
         return gradesList != null ? gradesList.size() : 0;
    }
     Integer calculateTotalMarks() {
         if (gradesList!=null){
             int totalMarks = 0;
             for (Grades grades : gradesList) {
                 totalMarks += grades.getUnitTotalMarks();
             }
             return totalMarks;
         }else {
             return 0;
         }
    }


    public static class GradesViewHolder extends RecyclerView.ViewHolder {
        TextView unitNameTextView, unitCodeTextView, totalMarksTextView, gradeTextView;
        TextView assign1MarksTextView, assign2MarksTextView, cat1MarksTextView, cat2MarksTextView, examMarksTextView;

        public GradesViewHolder(View itemView) {
            super(itemView);
            unitNameTextView = itemView.findViewById(R.id.unit_name); // Replace with the appropriate resource ID
            unitCodeTextView = itemView.findViewById(R.id.unit_code); // Replace with the appropriate resource ID
            totalMarksTextView = itemView.findViewById(R.id.total_mark); // Replace with the appropriate resource ID
            gradeTextView = itemView.findViewById(R.id.grade);
            assign1MarksTextView = itemView.findViewById(R.id.assign1_mark);
            assign2MarksTextView = itemView.findViewById(R.id.assign2_mark);
            cat1MarksTextView = itemView.findViewById(R.id.cat1mark);
            cat2MarksTextView = itemView.findViewById(R.id.cat2mark);
            examMarksTextView = itemView.findViewById(R.id.exam_mark);

        }
    }
}

