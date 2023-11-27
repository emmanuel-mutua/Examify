package com.emmutua.examify.home.lecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmutua.examify.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    List<StudentModel> studentList;

    public StudentAdapter(List<StudentModel> studentList) {
        this.studentList = studentList;
    }

    public interface OnItemClickListener {
        void onItemClick(StudentModel student);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    private OnItemClickListener listener;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecturer_regstudentscardview, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = studentList.get(position);

        // Bind data to the ViewHolder's views
        holder.nameTextView.setText(student.getStudentName());
        holder.regNoTextView.setText(student.getRegistrationNumber());
        holder.assign1MarksTextView.setText(String.valueOf(student.getUnitAssign1Marks()));
        holder.assign2MarksTextView.setText(String.valueOf(student.getUnitAssign2Marks()));
        holder.cat1MarksTextView.setText(String.valueOf(student.getUnitCat1Marks()));
        holder.cat2MarksTextView.setText(String.valueOf(student.getUnitCat2Marks()));
        holder.examMarksTextView.setText(String.valueOf(student.getUnitExamMarks()));

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(student);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, regNoTextView;
        TextView assign1MarksTextView, assign2MarksTextView, cat1MarksTextView, cat2MarksTextView, examMarksTextView;

        public StudentViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_name_id); // Replace with the appropriate resource ID
            regNoTextView = itemView.findViewById(R.id.student_registration_number); // Replace with the appropriate resource ID
            assign1MarksTextView = itemView.findViewById(R.id.assign1_mark);
            assign2MarksTextView = itemView.findViewById(R.id.assign2_mark);
            cat1MarksTextView = itemView.findViewById(R.id.cat1mark);
            cat2MarksTextView = itemView.findViewById(R.id.cat2mark);
            examMarksTextView = itemView.findViewById(R.id.exam_mark);
        }
    }
}