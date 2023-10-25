package com.emmutua.examify.home.student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class StudentHomeViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = auth.getCurrentUser();

    CollectionReference usersCollection = db.collection("users");
    private MutableLiveData<String> greetingText = new MutableLiveData<>();
    private MutableLiveData<String> courseText = new MutableLiveData<>();
    private MutableLiveData<String> regNoText = new MutableLiveData<>();
    private MutableLiveData<String> nameText = new MutableLiveData<>();
    private MutableLiveData<String> semesterText = new MutableLiveData<>();
    private MutableLiveData<String> emailText = new MutableLiveData<>();
    private MutableLiveData<String> phoneText = new MutableLiveData<>();

    public StudentHomeViewModel() {
        // Initialize the ViewModel and load user data immediately
        loadUserDataFromFirebase(currentUser.getUid());
    }
    public LiveData<String> getGreetingText() {
        return greetingText;
    }
    public LiveData<String> getCourseText() {
        return courseText;
    }

    public LiveData<String> getRegNoText() {
        return regNoText;
    }

    public LiveData<String> getNameText() {
        return nameText;
    }

    public LiveData<String> getSemesterText() {
        return semesterText;
    }

    public LiveData<String> getEmailText() {
        return emailText;
    }

    public LiveData<String> getPhoneText() {
        return phoneText;
    }

    public void loadUserDataFromFirebase(String currentUserUid) {

        Query query = usersCollection.whereEqualTo("uid", currentUserUid);

        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                String userFullName = documentSnapshot.getString("fullName");
//                String userCourse = documentSnapshot.getString("course");
                String userRegNo = documentSnapshot.getString("regNo");
                String userName = documentSnapshot.getString("fullName");
//                String userSemester = documentSnapshot.getString("semester");
                String userEmail = documentSnapshot.getString("email");
                String userPhone = documentSnapshot.getString("phoneNumber");

                // Update LiveData with the retrieved data
                greetingText.setValue("Hi there, " + userFullName);
//                courseText.setValue("Course: " + userCourse);
                regNoText.setValue("RegNo: " + userRegNo);
                nameText.setValue("Name: " + userName);
//                semesterText.setValue("Current Semester: " + userSemester);
                emailText.setValue("Email: " + userEmail);
                phoneText.setValue("Phone: " + userPhone);
            } else {
                // Handle the case when the user data is not found
            }
        }).addOnFailureListener(e -> {
            // Handle errors, such as network issues or Firebase exceptions
        });
    }

}
