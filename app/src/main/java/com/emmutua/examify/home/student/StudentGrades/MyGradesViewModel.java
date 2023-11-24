package com.emmutua.examify.home.student.StudentGrades;

// MyGradesViewModel.java
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MyGradesViewModel extends ViewModel {
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    //Fetch all semesters registered
    private MutableLiveData<List<Grades>> gradesList = new MutableLiveData<>();

    public LiveData<List<Grades>> getGradesList() {
        // Implement logic to fetch data from FireStore
        // Update the MutableLiveData with the fetched data
        // For simplicity, I'll assume a method fetchGradesDataFromFireStore()

        fetchGradesDataFromFirestore(); // Implement this method

        return gradesList;
    }

    private void fetchGradesDataFromFirestore() {
        // Implement Firestore query to fetch data based on the student ID and semester
        // Update gradesList with the fetched data
        // For simplicity, I'll assume a method fetchGradesFromFirestore()

        //fetchGradesFromFirestore(); // Implement this method
    }
}
