package com.emmutua.examify.home.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent; // Add this import statement
import com.emmutua.examify.R;
import com.emmutua.examify.addUnitsClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_homescreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homescreen);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // You are already in the HomeActivity, no need to start a new intent.
                // Do nothing or handle any specific logic here.
                return true;
            } else if (itemId == R.id.nav_addUnit) {
                // Navigate to the Add Units Activity
                startActivity(new Intent(this, addUnitsClass.class));
                return true;
            }
            return false;
        });
    }
}
