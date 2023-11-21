package com.emmutua.examify.home.student;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.emmutua.examify.R;
import com.emmutua.examify.databinding.ActivityStudentHomescreenBinding;
import com.emmutua.examify.home.admin.StudentsPerformanceBar.studentsPerfomanceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_homescreen extends AppCompatActivity {
    ActivityStudentHomescreenBinding binding;

    BottomNavigationView bottomNavigationView;
    Fragment home_fragment = new home();
    Fragment admin_students_performance_fragment = new studentsPerfomanceFragment();
    Fragment units_fragment = new Units();
    Fragment academics_fragment = new academics();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homescreen);
        binding = ActivityStudentHomescreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(home_fragment);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home_nav){
                replaceFragment(home_fragment);
            }else if (id == R.id.units){
                replaceFragment(units_fragment);
            }else if (id == R.id.academics){
                replaceFragment(admin_students_performance_fragment);
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }


}
