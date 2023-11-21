package com.emmutua.examify.home.admin.homeBar.units;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminViewUnits extends AppCompatActivity {
    RecyclerView recyclerView;
    Button allUnitsButton;
    UnitsAdapter unitsAdapter;
    List<UnitModel> unitList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_units);
        allUnitsButton =findViewById(R.id.all_units_button);
        recyclerView =findViewById(R.id.All_units_list); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(adminViewUnits.this));
        unitList = new ArrayList<>();
        unitsAdapter = new UnitsAdapter(unitList);
        recyclerView.setAdapter(unitsAdapter);
        fetchUnitsFromFirebase();
    }
    List<String> semesterStages = Arrays.asList("Y1S1", "Y1S2", "Y2S1", "Y2S2", "Y3S1", "Y3S2", "Y4S1", "Y4S2");
   Map<String, List<UnitModel>> unitListMap = new HashMap<>();
    void fetchUnitsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (String semesterStage : semesterStages) {
            db.collection("units")
                    .document("AllUnits")
                    .collection(semesterStage)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<UnitModel> semesterUnits = new ArrayList<>();
                            for (QueryDocumentSnapshot unitSnapshot : task.getResult()) {
                                String unitName = unitSnapshot.getString("unitName");
                                String unitCode = unitSnapshot.getString("unitCode");
                                String unitLecturer = unitSnapshot.getString("unitLecturer");
                                String unitSemesterStage = unitSnapshot.getString("role");

                                UnitModel unitModel = new UnitModel(unitName, unitCode, unitLecturer, unitSemesterStage);
                                semesterUnits.add(unitModel);
                            }
                            unitListMap.put(semesterStage, semesterUnits);
                            // Notify the adapter of the data change after all data is fetched.
                            unitsAdapter.notifyDataSetChanged();
                            if (unitListMap.size() == semesterStages.size()) {
                                // All data has been fetched, now you can classify and display the data.
                                classifyAndDisplayData();
                            }
                        } else {
                            Log.e("FirebaseFetchError", "Error: " + task.getException());
                            Toast.makeText(adminViewUnits.this, "Failed to fetch data: " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
    private void classifyAndDisplayData() {
        // Iterate through the semester stages and add units to the RecyclerView adapter
        for (String semesterStage : semesterStages) {
            List<UnitModel> units = unitListMap.get(semesterStage);
            if (units != null) {
                unitList.addAll(units);
            }
        }

        // Notify the adapter of the data change
        unitsAdapter.notifyDataSetChanged();
    }
}

