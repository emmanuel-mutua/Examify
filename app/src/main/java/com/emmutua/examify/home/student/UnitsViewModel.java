package com.emmutua.examify.home.student;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emmutua.examify.home.student.addUnit.UnitDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UnitsViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();
    private MutableLiveData<List<String>> allUnitDetails = new MutableLiveData<>();
    public LiveData<List<String>> getUnitDetails(){
        getAllStudentUnits();
        return allUnitDetails;
    }
    public void setUnitDetails(List<String> unitDetails){
        this.allUnitDetails.setValue(unitDetails);
    }
    private void getAllStudentUnits() {
        CollectionReference registeredUnits = db.collection("students_registered_units");

        registeredUnits.whereEqualTo("studentUid", uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> allUnitDetailsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String unitName = document.getString("unitName");
                    allUnitDetailsList.add(unitName);
                }
                setUnitDetails(allUnitDetailsList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur.
                Log.e(TAG, "Error getting documents: ", e);
            }
        });
    }

}
