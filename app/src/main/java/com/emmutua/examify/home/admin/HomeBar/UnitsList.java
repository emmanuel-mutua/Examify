package com.emmutua.examify.home.admin.HomeBar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UnitsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UnitsList extends Fragment {
    CardView viewAllStudentsRegisteredUNits,notificationsCardView;
    RecyclerView recyclerView;
    Button allUnitsButton;
    UnitsAdapter unitsAdapter;
    List<UnitModel> unitList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UnitsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UnitsList.
     */
    // TODO: Rename and change types and number of parameters
    public static UnitsList newInstance(String param1, String param2) {
        UnitsList fragment = new UnitsList();
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
        View view = inflater.inflate(R.layout.fragment_units_list, container, false);
        viewAllStudentsRegisteredUNits = view.findViewById(R.id.studentUnitsInfoCardView);
        allUnitsButton = view.findViewById(R.id.all_units_button);
        notificationsCardView = view.findViewById(R.id.notificationsCardView);
        recyclerView = view.findViewById(R.id.All_units_list); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        unitList = new ArrayList<>();
        unitsAdapter = new UnitsAdapter(unitList);
        recyclerView.setAdapter(unitsAdapter);
        fetchUnitsFromFirebase();
        return view;
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
                        Toast.makeText(getContext(), "Failed to fetch data: " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}