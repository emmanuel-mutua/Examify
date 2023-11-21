package com.emmutua.examify.home.admin.homeBar.editmarks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmutua.examify.R;
import com.emmutua.examify.home.lecture.StudentModel;

import java.util.List;

public class EditMarksAdapter extends RecyclerView.Adapter<EditMarksAdapter.EditMarksViewHolder> {
    List<StudentModel> studentList;

    public EditMarksAdapter(List<StudentModel> studentList) {
        this.studentList = studentList;
    }

    public interface OnItemClickListener {
        void onItemClick(StudentModel student);
    }

    public void setOnItemClickListener(com.emmutua.examify.home.lecture.StudentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    private com.emmutua.examify.home.lecture.StudentAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public EditMarksAdapter.EditMarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_edit_marks_cardview, parent, false);
        return new EditMarksAdapter.EditMarksViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull EditMarksAdapter.EditMarksViewHolder holder, int position) {
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

    public static class EditMarksViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, regNoTextView;
        TextView assign1MarksTextView, assign2MarksTextView, cat1MarksTextView, cat2MarksTextView, examMarksTextView;

        public EditMarksViewHolder(View itemView) {
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

