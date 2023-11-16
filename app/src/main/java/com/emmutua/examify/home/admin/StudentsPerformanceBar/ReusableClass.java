package com.emmutua.examify.home.admin.StudentsPerformanceBar;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ReusableClass {
    private RadioGroup semesterRadioGroup;
    private Context context;

    // Constructor to receive the RadioGroup and Context
    public ReusableClass(RadioGroup radioGroup, Context context) {
        this.semesterRadioGroup = radioGroup;
        this.context = context;
    }

    // Method to get the selected semester
    public String getSelectedSemester() {
        int selectedRadioButtonId = semesterRadioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = semesterRadioGroup.findViewById(selectedRadioButtonId);
            if (selectedRadioButton != null) {
                return selectedRadioButton.getText().toString();
            }
        } else {
            // No radio button is selected
            Toast.makeText(context, "Please select a semester", Toast.LENGTH_SHORT).show();
        }

        return "";
    }
}
