package com.emmutua.examify.home.admin.HomeBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emmutua.examify.R;
import com.emmutua.examify.authentication.Login;
import com.emmutua.examify.home.admin.HomeBar.EnrolledStudents.adminViewEnrolledStudents;
import com.emmutua.examify.home.admin.HomeBar.Units.adminViewUnits;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link adminHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class adminHome extends Fragment {
    Button allUnitsButton,enrolledStudentsButton;
    Button logOutButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public adminHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment adminHome.
     */
    // TODO: Rename and change types and number of parameters
    public static adminHome newInstance(String param1, String param2) {
        adminHome fragment = new adminHome();
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
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        allUnitsButton = view.findViewById(R.id.all_units_button);
        logOutButton = view.findViewById(R.id.log_out_button);
        enrolledStudentsButton = view.findViewById(R.id.all_enrolled_students_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); // Log out the user
                // Start the login activity
                Intent intent = new Intent(getContext(), Login.class); // Replace YourCurrentActivity with your current activity class and LoginActivity with your login activity class
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // This clears the current activity stack and creates a new one.
                startActivity(intent);
                getActivity().finish();
                // logout user

            }
        });
        allUnitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), adminViewUnits.class));
            }
        });
        enrolledStudentsButton.setOnClickListener((v)->{
            startActivity(new Intent(getContext(), adminViewEnrolledStudents.class));
        });
        return view;
    }
    //method for fetching allUnits In fireBase

}
