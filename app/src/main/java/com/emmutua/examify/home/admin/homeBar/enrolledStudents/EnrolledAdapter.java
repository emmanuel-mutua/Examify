package com.emmutua.examify.home.admin.homeBar.enrolledStudents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmutua.examify.R;

import java.util.List;

public class EnrolledAdapter extends RecyclerView.Adapter<EnrolledAdapter.EnrolledViewHolder> {
    List<EnrolledModel> enrolledList;

    public EnrolledAdapter(List<EnrolledModel> enrolledList) {
        this.enrolledList = enrolledList;
    }

    @NonNull
    @Override
    public EnrolledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrolled_students_card, parent, false);
        return new EnrolledViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrolledViewHolder holder, int position) {
        EnrolledModel enrolled = enrolledList.get(position);

        // Bind data to the ViewHolder's views
        holder.nameTextView.setText(enrolled.getStudentName());
        holder.regNoTextView.setText(enrolled.getStudentRegNo());
    }

    @Override
    public int getItemCount() {
        return enrolledList.size();
    }

    public static class EnrolledViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, regNoTextView;

        public EnrolledViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.enrolledStudent_name_id); // Replace with the appropriate resource ID
            regNoTextView = itemView.findViewById(R.id.enrolledStudent_regNo_id); // Replace with the appropriate resource ID
        }
    }
}
