package com.emmutua.examify.authentication;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class utility {
    static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    static FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public static void showToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReference(){
        return FirebaseFirestore.getInstance().collection("User").document().collection("Personal_Details");
    }
    static void fetchUserRole(UserRoleCallback callback) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();

            CollectionReference usersCollection = firestore.collection("users");

            // Query the Firestore collection for the user with the matching UID
            Query query = usersCollection.whereEqualTo("uid", uid);

            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();

                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    // Get the role from the first document found (assuming there's only one)
                                    DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                                    String role = document.getString("role");

                                    if (role != null) {
                                        callback.onUserRoleFetched(role); // Return the role via callback
                                    } else {
                                        callback.onUserRoleFetched(null); // Handle the case where 'role' is null
                                    }
                                } else {
                                    callback.onUserRoleFetched(null); // Handle the case where no matching document is found
                                }
                            } else {
                                callback.onUserRoleFetched(null); // Handle any errors that may occur
                            }
                        }
                    });
        } else {
            callback.onUserRoleFetched(null); // Handle the case where the user is not authenticated
        }
    }

}

