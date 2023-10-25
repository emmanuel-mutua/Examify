package com.emmutua.examify.home.admin.AddUnitsBar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adminAddUnits#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adminAddUnits extends Fragment {
    Spinner spinner;
    EditText unitNameEditText, unitCodeEditText,
            unitLecturerEditText,semesterEditText;
    Button submitButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public adminAddUnits() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adminAddUnits.
     */
    // TODO: Rename and change types and number of parameters
    public static adminAddUnits newInstance(String param1, String param2) {
        adminAddUnits fragment = new adminAddUnits();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_add_units, container, false);
        //correct all the errors in the code
        unitNameEditText = view.findViewById(R.id.unit_name);
        unitCodeEditText = view.findViewById(R.id.unit_code);
        unitLecturerEditText = view.findViewById(R.id.unit_lecturer);
        semesterEditText = view.findViewById(R.id.semester_stage);
        submitButton = view.findViewById(R.id.submit_units_btn);
       /*set the spinner to be able to enter diffrent sem stages in an array
        and allow user to select only one option at a time*/
        spinner = view.findViewById(R.id.role_spinner);
        String[] options = {"Y1S1", "Y1S2", "Y2S1", "Y2S2",
                "Y3S1", "Y3S2", "Y4S1", "Y4S2"};
        ArrayAdapter<String> adapter = new
                ArrayAdapter<>(requireContext(), android.R.layout.
                simple_spinner_dropdown_item, options);
        adapter.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // on button click, get the values entered by the user , validate them and send them to firestore database
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    SendUnitDetailsToFirebase();
                }else {
                    return;
                }
            }
        });
        return view;
    }
    // get the values entered by the user and send them to firestore database
    void SendUnitDetailsToFirebase() {
        String unitName = unitNameEditText.getText().toString();
        String unitCode = unitCodeEditText.getText().toString();
        String unitLecturer = unitLecturerEditText.getText().toString();
        String semester = semesterEditText.getText().toString();
        String role = spinner.getSelectedItem().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Units unit = new Units(unitName, unitCode, unitLecturer, semester, role);

        // Reference to the "units" collection in Firestore, with the document ID being unitCode
        DocumentReference documentReference = db.collection("units").document().collection(role).document(unitCode);

        // Set the unit data in Firestore
        documentReference.set(unit)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Unit added successfully", Toast.LENGTH_SHORT).show();
                    // Clear input fields after successful addition
                    unitNameEditText.setText("");
                    unitCodeEditText.setText("");
                    unitLecturerEditText.setText("");
                    semesterEditText.setText("");
                    spinner.setSelection(0);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // method for validating user input and ensuring the fields are not empty
    boolean validateInput() {
        String unitName = unitNameEditText.getText().toString();
        String unitCode = unitCodeEditText.getText().toString();
        String unitLecturer = unitLecturerEditText.getText().toString();
        String semester = semesterEditText.getText().toString();
        String role = spinner.getSelectedItem().toString();

        if (unitName.isEmpty()) {
            unitNameEditText.setError("Unit name is required");
            return false;
        } else if (unitCode.isEmpty()) {
            unitCodeEditText.setError("Unit code is required");
            return false;
        } else if (unitLecturer.isEmpty()) {
            unitLecturerEditText.setError("Unit lecturer is required");
            return false;
        } else if (semester.isEmpty()) {
            semesterEditText.setError("Semester is required");
            return false;
        } else if (role.isEmpty()) {
            Toast.makeText(requireContext(), "Please select a role", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}