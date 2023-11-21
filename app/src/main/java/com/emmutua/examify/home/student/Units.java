package com.emmutua.examify.home.student;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emmutua.examify.R;
import com.emmutua.examify.authentication.Login;
import com.emmutua.examify.authentication.utility;
import com.emmutua.examify.home.lecture.lecturer_homeScreen;
import com.emmutua.examify.home.student.addUnit.AddUnit;
import com.emmutua.examify.home.student.addUnit.AddUnitViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Units#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Units extends Fragment {
    UnitsViewModel unitsViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Units() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment courses.
     */
    // TODO: Rename and change types and number of parameters
    public static Units newInstance(String param1, String param2) {
        Units fragment = new Units();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);
        unitsViewModel = new ViewModelProvider(this).get(UnitsViewModel.class);
        ListView listView = view.findViewById(R.id.all_units_list_view);
        TextView noRegisteredUnitsText = view.findViewById(R.id.no_registered_units_text);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        unitsViewModel.getUnitDetails().observe(getViewLifecycleOwner(), allUnits -> {
            if (allUnits != null) {
                adapter.clear();
                adapter.addAll(allUnits);
                adapter.notifyDataSetChanged();
            }else {
                noRegisteredUnitsText.setVisibility(View.VISIBLE);
            }
        });
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedItem = adapter.getItem(position);
            ShowAlertDialog(selectedItem);
        });
        FloatingActionButton addUnitFab = view.findViewById(R.id.add_unit_fab);
        addUnitFab.setOnClickListener(onClick -> {
            startActivity(new Intent(getActivity(), AddUnit.class));
        });
        return view;
    }
    void ShowAlertDialog(String selectedItem){
        // show alert dialog to allow for registration of special exams
            AlertDialog.Builder builder = new AlertDialog
                   .Builder(getContext());
           builder.setTitle("Apply For Special EXam");
           builder.setMessage("Are you sure you want to apply for special for  " + selectedItem + "?");
           builder.setPositiveButton("Apply", (dialog, which) -> {
               sendAppliedSpecialsToFirebase(selectedItem);
           });
            builder.setNegativeButton("Cancel",(dialog, which) ->{
               dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
          dialog.show();
    }
    void sendAppliedSpecialsToFirebase(String selectedUnit){
        utility utility = new utility();
        boolean appliedForSpecial = true;
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("students_registered_units").
                document().update("appliedForSpecial", appliedForSpecial).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        utility.showToast(getContext(), "Successfully Applied for special exam for " + selectedUnit );
                    }else{
                        utility.showToast(getContext(), "Failed to apply for special exam");
                    }
                        // show success message
                }).addOnFailureListener(
                            e -> utility.showToast(getContext(), "Failed to apply for special exam")
                );
    }
}