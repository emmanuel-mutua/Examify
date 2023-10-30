package com.emmutua.examify.home.student.addUnit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emmutua.examify.R;

import java.util.List;

public class SingleSelectionAdapter extends ArrayAdapter<String> {

    private int selectedItemPosition = -1;

    public SingleSelectionAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        RadioButton radioButton = view.findViewById(R.id.radioButton);

        // Set the checked state based on the selected item
        radioButton.setChecked(position == selectedItemPosition);

        radioButton.setOnClickListener(v -> {
            // Update the selected item and notify the adapter that the data has changed
            selectedItemPosition = position;
            notifyDataSetChanged();
        });

        return view;
    }
}
