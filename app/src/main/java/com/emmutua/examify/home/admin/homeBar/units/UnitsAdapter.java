package com.emmutua.examify.home.admin.homeBar.units;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emmutua.examify.R;

import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.UnitsViewHolder> {
    List<UnitModel> unitList;

    public UnitsAdapter(List<UnitModel> unitList) {
        this.unitList = unitList;
    }

    @NonNull
    @Override
    public UnitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.units_card, parent, false);
        return new UnitsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitsViewHolder holder, int position) {
        UnitModel unit = unitList.get(position);

        // Bind data to the ViewHolder's views
        holder.unitNameTextView.setText(unit.getUnitName());
        holder.unitCodeTextView.setText(unit.getUnitCode());
        holder.unitLecturerTextView.setText(unit.getUnitLecturer());
        holder.unitSemesterStageTextView.setText(unit.getUnitSemesterStage());
    }

    @Override
    public int getItemCount() {
        return unitList.size();
    }

    public static class UnitsViewHolder extends RecyclerView.ViewHolder {
        TextView unitNameTextView, unitCodeTextView, unitLecturerTextView, unitSemesterStageTextView;

        public UnitsViewHolder(View itemView) {
            super(itemView);
            unitNameTextView = itemView.findViewById(R.id.Unit_name_id);
            unitCodeTextView = itemView.findViewById(R.id.Unit_code_id);
            unitLecturerTextView = itemView.findViewById(R.id.unit_lecturer_id);
            unitSemesterStageTextView = itemView.findViewById(R.id.Unit_semester_stage_id);
        }
    }
}
