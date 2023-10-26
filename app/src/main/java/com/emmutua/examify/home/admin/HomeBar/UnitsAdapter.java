package com.emmutua.examify.home.admin.HomeBar;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// create units adapter class
public class UnitsAdapter extends RecyclerView.Adapter {
    List<UnitModel> unitList;

    public List<UnitModel> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<UnitModel> unitList) {
        this.unitList = unitList;
    }

    public UnitsAdapter(List<UnitModel> unitList) {
        this.unitList = unitList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
