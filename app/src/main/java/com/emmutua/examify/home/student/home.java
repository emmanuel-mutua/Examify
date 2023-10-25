package com.emmutua.examify.home.student;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emmutua.examify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    StudentHomeViewModel studentHomeViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
         View view = inflater.inflate(R.layout.fragment_home, container, false);
        studentHomeViewModel = new ViewModelProvider(this).get(StudentHomeViewModel.class);

        TextView greetingTextView = view.findViewById(R.id.greetingTextView);
        TextView courseTextView = view.findViewById(R.id.courseTextView);
        TextView regNoTextView = view.findViewById(R.id.regNoTextView);
        TextView nameTextView = view.findViewById(R.id.nameTextView);
        TextView semesterTextView = view.findViewById(R.id.currentSemesterTextView);
        TextView emailTextView = view.findViewById(R.id.emailTextView);
        TextView phoneTextView = view.findViewById(R.id.phoneTextView);

        TextView logoutTextView = view.findViewById(R.id.logoutTextView);
        logoutTextView.setOnClickListener(v -> {
            studentHomeViewModel.logout();
        });

        // Observe LiveData from the ViewModel and update UI elements
        studentHomeViewModel.getGreetingText().observe(getViewLifecycleOwner(), greeting -> {
            greetingTextView.setText(greeting);
        });
        studentHomeViewModel.getCourseText().observe(getViewLifecycleOwner(), course -> {
            courseTextView.setText(course);
        });
        studentHomeViewModel.getRegNoText().observe(getViewLifecycleOwner(), regNo -> {
            regNoTextView.setText(regNo);
        });
        studentHomeViewModel.getNameText().observe(getViewLifecycleOwner(), name -> {
            nameTextView.setText(name);
        });
        studentHomeViewModel.getSemesterText().observe(getViewLifecycleOwner(), semester -> {
            semesterTextView.setText(semester);
        });
        studentHomeViewModel.getEmailText().observe(getViewLifecycleOwner(), email -> {
            emailTextView.setText(email);
        });
        studentHomeViewModel.getPhoneText().observe(getViewLifecycleOwner(), phone -> {
            phoneTextView.setText(phone);
        });
        return view;
    }
}