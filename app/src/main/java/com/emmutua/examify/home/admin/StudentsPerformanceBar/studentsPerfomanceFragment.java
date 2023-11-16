package com.emmutua.examify.home.admin.StudentsPerformanceBar;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emmutua.examify.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link studentsPerfomanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class studentsPerfomanceFragment extends Fragment {
  CardView passlistPerSemesterCardview, passListPerAcademicYearCardView, failListPerSemesterCardView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public studentsPerfomanceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment studentsPerfomanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static studentsPerfomanceFragment newInstance(String param1, String param2) {
        studentsPerfomanceFragment fragment = new studentsPerfomanceFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_students_perfomance, container, false);
        passlistPerSemesterCardview = view.findViewById(R.id.passedAllCoursesInASemester);
        passListPerAcademicYearCardView = view.findViewById(R.id.studentPassedUnitsInAcademicYear);
        failListPerSemesterCardView = view.findViewById(R.id.studentsFailedOneOrMoreCourses_andTheCoursesFailed);
        passlistPerSemesterCardview.setOnClickListener(V ->
                startActivity(new Intent(getContext(), PassListPerSemester.class)));
        failListPerSemesterCardView.setOnClickListener(V ->startActivity(new Intent(getContext(), FailListPerSemester.class)) );
        return view;
    }
}