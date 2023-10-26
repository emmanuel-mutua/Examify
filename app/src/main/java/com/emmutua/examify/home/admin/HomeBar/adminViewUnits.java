package com.emmutua.examify.home.admin.HomeBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class adminViewUnits extends AppCompatActivity {
    CardView viewAllStudentsRegisteredUNits,notificationsCardView;
    RecyclerView recyclerView;
    Button allUnitsButton;
    UnitsAdapter unitsAdapter;
    List<UnitModel> unitList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_units);
        viewAllStudentsRegisteredUNits = findViewById(R.id.studentUnitsInfoCardView);
        allUnitsButton =findViewById(R.id.all_units_button);
        notificationsCardView = findViewById(R.id.notificationsCardView);
        recyclerView =findViewById(R.id.All_units_list); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(adminViewUnits.this));
        unitList = new ArrayList<>();
        unitsAdapter = new UnitsAdapter(unitList);
        recyclerView.setAdapter(unitsAdapter);
        fetchUnitsFromFirebase();
    }
    void fetchUnitsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("units").document("AllUnits").collection("Y1S1")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        unitList.clear();
                        for (int i = 0; i < task.getResult().size(); i++) {
                            //get the unit name
                            String unitName = task.getResult().getDocuments().get(i).get("unitName").toString();
                            //get the unit code
                            String unitCode = task.getResult().getDocuments().get(i).get("unitCode").toString();
                            //get the unit lecturer
                            String unitLecturer = task.getResult().getDocuments().get(i).get("unitLecturer").toString();
                            //get the unit semester
                            String unitSemesterStage = task.getResult().getDocuments().get(i).get("role").toString();
                            // Create a UnitModel object and add it to the list
                            UnitModel unitModel = new UnitModel(unitName,unitCode,unitLecturer,unitSemesterStage);
                            unitList.add(unitModel);
                        }
                        //notify the adapter of the data change
                        unitsAdapter.notifyDataSetChanged();
                    } else{
                        Log.e("FirebaseFetchError", "Error: " + task.getException());
                        Toast.makeText(adminViewUnits.this, "Failed to fetch data: " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}