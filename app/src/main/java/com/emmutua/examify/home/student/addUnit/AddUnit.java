package com.emmutua.examify.home.student.addUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.emmutua.examify.home.student.StudentHomeViewModel;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AddUnit extends AppCompatActivity {
    AddUnitViewModel addUnitViewModel;
    MaterialButton add_units_button;
    private RadioGroup radioGroup;
    private ProgressBar progress_bar;
    private TextView unitsTextView;

    TextView selected;
    SingleSelectionAdapter adapter;
    private String[] options = {"Y1S1", "Y1S2", "Y2S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2", "Y5S1", "Y5S2"};
    ImageButton back_button;
    private List<String> selectedUnits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unit);
        addUnitViewModel = new ViewModelProvider(this).get(AddUnitViewModel.class);
        add_units_button = findViewById(R.id.add_units_button);
        back_button = findViewById(R.id.back_button);
        radioGroup = findViewById(R.id.stage_radio_group);
        unitsTextView = findViewById(R.id.units_text_view);
        progress_bar = findViewById(R.id.progress_bar);
        int selectedStageId = radioGroup.getCheckedRadioButtonId();
        final String[] selectedStage = {""};
        if (selectedStageId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedStageId);
            selectedStage[0] = selectedRadioButton.getText().toString();
        } else {
            // Handle the case where no radio button is selected
            // You can provide a default value or show an error message.
            selectedStage[0] = "No selection"; // For example
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedStage[0] = selectedRadioButton.getText().toString();
                unitsTextView.setVisibility(TextView.VISIBLE);
                progress_bar.setVisibility(ProgressBar.VISIBLE);
                try {
                    addUnitViewModel.getAllUnitsAccordingToStage(selectedStage[0]);
                }catch (Exception e){
                    Toast.makeText(AddUnit.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                unitsTextView.setText("Select AtLeast 5 units");
                addUnitViewModel.getUnitDetails().observe(AddUnit.this, units -> {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < units.size(); i++) {
                        unitNames.add(units.get(i).toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AddUnit.this, android.R.layout.simple_list_item_multiple_choice, unitNames);
                    ListView listView = findViewById(R.id.units_list_view);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        // Handle item click, use the 'position' to identify the selected unit.
                        boolean isChecked = listView.isItemChecked(position);
                        String selectedUnitName = unitNames.get(position);
                        if (isChecked) {
                            selectedUnits.add(selectedUnitName);
                            // Add the selected unit to your list
                        } else {
                            selectedUnits.remove(selectedUnitName);
                        }
                    });
                    progress_bar.setVisibility(ProgressBar.INVISIBLE);
                });

                Toast.makeText(AddUnit.this, "Selected: " + selectedStage[0], Toast.LENGTH_SHORT).show();
            }
        });
        add_units_button.setOnClickListener(onClick -> {
            Boolean isSuccess = addUnitViewModel.registerUnits(selectedUnits);
            if (isSuccess == true){
                Toast.makeText(this, "Units added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please select atleast 5 units", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, selectedStage[0], Toast.LENGTH_SHORT).show();
        adapter = new SingleSelectionAdapter(this, R.layout.list_radio_button, options);
        back_button.setOnClickListener(onClick -> {
            finish();
        });

    }
}