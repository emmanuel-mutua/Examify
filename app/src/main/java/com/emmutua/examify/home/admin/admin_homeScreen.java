package com.emmutua.examify.home.admin;

import android.os.Bundle;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.emmutua.examify.R;
import com.emmutua.examify.databinding.ActivityAdminHomescreenBinding;
import com.emmutua.examify.home.admin.AddUnitsBar.adminAddUnits;
import com.emmutua.examify.home.admin.homeBar.adminHome;
import com.emmutua.examify.home.admin.StudentsPerformanceBar.studentsPerfomanceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class admin_homeScreen extends AppCompatActivity {
    Toolbar toolbar;
    @NonNull
    ActivityAdminHomescreenBinding binding;

    BottomNavigationView bottomNavigationView;
    Fragment admin_home_fragment = new adminHome();
    Fragment admin_students_performance_fragment = new studentsPerfomanceFragment();
    Fragment admin_add_units_fragment = new adminAddUnits();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homescreen);
        binding = ActivityAdminHomescreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(admin_home_fragment);
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.admin_nav_home){
                replaceFragment(admin_home_fragment);
            }else if (id == R.id.admin_nav_addUnit){
                replaceFragment(admin_add_units_fragment);
            } else if (id == R.id.admin_nav_studentsPerformance){
                replaceFragment(admin_students_performance_fragment);
            }
            return true;
        });
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContainer, fragment)
                .commit();
    }
}
