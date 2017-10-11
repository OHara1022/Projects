package com.ohara.oharaedward_1703.formActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.User;


//Edward O'Hara
//MDV469 - 1703
//FormActivity
public class FormActivity extends AppCompatActivity implements GetUserData {

    //TAG
    private static final String TAG = "FormActivity";

    //stored properties
    //firebase auth
    FirebaseAuth mAuth;
    //firebase database reference
    DatabaseReference mRefernece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //get instance of firebaseAuth
        mAuth = FirebaseAuth.getInstance();
        //get instance of firebase database
        mRefernece = FirebaseDatabase.getInstance().getReference();

        //get instance of form fragment
        FormFragment formFragment = FormFragment.newInstance();
        //display fragment
        getFragmentManager().beginTransaction().replace(R.id.formContainer, formFragment,
                FormFragment.FORM_TAG).commit();
    }

    //method to create firebase user
    private void createFirebaseUser(final User user) {

        mAuth.createUserWithEmailAndPassword
                (user.email, user.password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //dev
                            Log.i(TAG, "onComplete: AUTHENTICATION FAILED");

                            //auth failed
                            Toast.makeText(FormActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //dev
                        Log.i(TAG, "onComplete: AUTHENTICATION COMPLETED");

                        //auth successful
                        Toast.makeText(FormActivity.this, "Authentication complete", Toast.LENGTH_SHORT).show();

                        //close activity
                        finish();
                    }
                });
    }

    @Override
    public void newUser(User user) {
        //dev
        Log.i(TAG, "newUser: " + user.firstName);
        Log.i(TAG, "newUser: " + user.lastName);
        Log.i(TAG, "newUser: " + user.email);
        Log.i(TAG, "newUser: " + user.password);//never store password in firebase
        Log.i(TAG, "newUser: " + user.uid);

        //create user with passed data
        createFirebaseUser(user);

        //holder string for saving user data
        final String firstName = user.firstName;
        final String lastName = user.lastName;
        final String emailAddress = user.email;

        //listener to save user data, gets hit after create user method finishes
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //get current user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check user is not null
                if (user != null) {
                    //dev
                    Log.i(TAG, "onAuthStateChanged: FORM" + user.getUid());

                    //holder for users uid
                    String uid = user.getUid();

                    //save data to user firebase database
                    mRefernece.child("users").child(uid).child("firstName").setValue(firstName);
                    mRefernece.child("users").child(uid).child("lastName").setValue(lastName);
                    mRefernece.child("users").child(uid).child("email").setValue(emailAddress);
                    mRefernece.child("users").child(uid).child("uid").setValue(uid);
                }
            }
        });
    }
}
