package com.emmutua.examify.home.student.addUnit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * our add unit model can have the following properties:
 * 1. stage name
 * 2. unit name
 * 3. unit code - will be the document code
 * 4. unit assign1 1 marks
 * 4. unit cat 1 marks
 * 5. unit cat 2 marks
 * 6. unit exam marks
 * 7. unit total marks
 * 5. unit lecturer
 * 6. unit year
 * 7. unit semester
 * 8. unit department
 * //default marks will be null
 *
 * //then i want to set the collection in firebase to be units
 * will be students collection
 * documentid will be the student id
 * then have collection registed units
 *
 */




public class AddUnitViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getCurrentUser().getUid();
    CollectionReference registeredUnits = db.collection("students").document(uid).collection("registered_units");
    private MutableLiveData<List<String>> unitDetails = new MutableLiveData<>();
    private MutableLiveData<String> _stage = new MutableLiveData<>();
    private MutableLiveData<String> _unitName = new MutableLiveData<>();
    private MutableLiveData<String> _unitCode = new MutableLiveData<>();
    private MutableLiveData<String> _unitLecturer = new MutableLiveData<>();
    private MutableLiveData<String> _unitDepartment = new MutableLiveData<>();
    private String _unitStage;

    public LiveData<List<String>> getUnitDetails(){
        return unitDetails;
    }
    public void setUnitDetails(List<String> unitDetails){
        this.unitDetails.setValue(unitDetails);
    }
    void getAllUnitsAccordingToStage(String stageId) {
        CollectionReference units = db.collection("units").document("AllUnits").collection(stageId);

        units.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> unitDetailsList = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    String unitStage = document.getString("role");
                    String semester = document.getString("semester");
                    String unitCode = document.getString("unitCode");
                    String unitLecturer = document.getString("unitLecturer");
                    String unitName = document.getString("unitName");
                    _stage.setValue(unitStage);
                    _unitCode.setValue(unitCode);
                    _unitLecturer.setValue(unitLecturer);
//                    new UnitDetails(unitName, unitCode, unitLecturer, "Computer Science", unitStage,0,0,0,0, 0 , 0);
                    unitDetailsList.add(unitName);
                }
                setUnitDetails(unitDetailsList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors that occur.
                Log.e(TAG, "Error getting documents: ", e);
            }
        });
    }

    public Boolean registerUnits(List<String> selectedUnits, String studentName, String studentRegNo) {
        if (selectedUnits.size() < 5) {
            return false;
        }
         String unitCode =  _unitCode.getValue().toString();
         String unitLecturer = _unitLecturer.getValue().toString();
         String unitDepartment = "Computer Science";
         String unitStage = _stage.getValue().toString();

        for (String unitName : selectedUnits) {
            UnitDetails unitDetails =  new UnitDetails(uid,studentName, studentRegNo, unitName, unitCode, unitLecturer, unitDepartment, unitStage,0,0,0,0, 0 , 0);
            registeredUnits.add(unitDetails)
                    .addOnSuccessListener(documentReference -> {
                        // Unit added successfully
                        String documentId = documentReference.getId();
                        Log.d("Firestore", "Unit added with ID: " + documentId);
                    })
                    .addOnFailureListener(e -> {
                        // Error occurred while adding the unit
                        Log.e("Firestore", "Error adding unit", e);
                    });
        }
        return true;
    }
}
