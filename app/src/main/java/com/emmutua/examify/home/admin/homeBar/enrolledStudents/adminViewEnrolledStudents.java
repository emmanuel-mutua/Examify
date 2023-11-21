package com.emmutua.examify.home.admin.homeBar.enrolledStudents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.emmutua.examify.R;
import com.emmutua.examify.home.admin.homeBar.units.UnitModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class adminViewEnrolledStudents extends AppCompatActivity {
    RecyclerView recyclerView;
    EnrolledAdapter enrolledAdapter;
    List<EnrolledModel> enrolledList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_enrolled_students);
        recyclerView =findViewById(R.id.All_EnrolledStudents_list); // Replace with your RecyclerView ID
        recyclerView.setLayoutManager(new LinearLayoutManager(adminViewEnrolledStudents.this));
        enrolledList = new ArrayList<>();
        enrolledAdapter= new EnrolledAdapter(enrolledList);
        recyclerView.setAdapter(enrolledAdapter);
        fetchEnrolledStudentsFromFirebase();
    }
    Map<String, List<UnitModel>> enrolledListMap = new HashMap<>();
    void fetchEnrolledStudentsFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Enrollment")
                .document("1029") // Use the correct document path
                .collection("2023") // Replace with the collection name containing student data
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        enrolledList.clear(); // Clear the list before adding data
                        for (QueryDocumentSnapshot studentSnapshot : task.getResult()) {
                            String studentName = studentSnapshot.getString("Name");
                            String studentRegNo = studentSnapshot.getString("RegNo");

                            EnrolledModel enrolledModel = new EnrolledModel(studentName, studentRegNo);
                            enrolledList.add(enrolledModel);
                        }
                        enrolledAdapter.notifyDataSetChanged(); // Notify the adapter after data is fetched.
                    } else {
                        Log.e("FirebaseFetchError", "Error: " + task.getException());
                        Toast.makeText(this, "Failed to fetch data: " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
//create class enrolledAdapter without the recyclerview