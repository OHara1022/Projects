package com.ohara.oharaedward_crossplatform.loginActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;
import com.ohara.oharaedward_crossplatform.formActivity.FormActivity;
import com.ohara.oharaedward_crossplatform.homeActivity.HomeActivity;

//Edward O'Hara
//LoginActivity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //TAG
    private static final String TAG = "LoginActivity";

    //stored properties
    EditText mEmailEditText;
    EditText mPasswordEditText;
    Button signUpBtn;
    Button loginBtn;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //enable db to read offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //init firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //init auth state listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //get current user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check is user has a value
                if (user != null) {
                    //dev
                    Log.d(TAG, "onAuthStateChanged: SIGNED IN " + user.getUid());

                    //transition to homeScreen if user is logged in
                    Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(homeActivity);

                } else {
                    //dev
                    Log.d(TAG, "onAuthStateChanged: SIGNED OUT");

                    //clean text fields on return to home screen
                    mEmailEditText.setText("");
                    mPasswordEditText.setText("");
                }
            }
        };

        //init email & password fields
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        //init buttons
        signUpBtn = (Button) findViewById(R.id.signUpButton);
        loginBtn = (Button) findViewById(R.id.loginButton);

        //set onClickListener to buttons
        signUpBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        //alert user there is no network
        FieldsCheck.networkConnection(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.loginButton:
                //dev
                Log.i(TAG, "onClick: LOGIN clicked");

                //check if network is connected
                if (!FieldsCheck.networkConnection(this)) {

                    //dev
                    Log.i(TAG, "onClick: ALERT USER");
                } else {

                    //dev
                    Log.i(TAG, "onClick: CONNECT");

                    //check empty fields
                    if (FieldsCheck.isEmpty(mEmailEditText) || FieldsCheck.isEmpty(mPasswordEditText)) {
                        //alert user no empty fields
                        Toast.makeText(this, "No Empty Fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (FieldsCheck.validEmail(mEmailEditText.getText().toString().trim(), this) &&
                            FieldsCheck.validPassword(mPasswordEditText.getText().toString().trim(), this)) {
                        //sign in user w/ email & password
                        mAuth.signInWithEmailAndPassword(mEmailEditText.getText().toString().toLowerCase().trim(),
                                mPasswordEditText.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    //notify user of wrong credentials
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials !", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                                    @Override
                                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                        //get current user
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        //user conditional
                                        if (user != null) {
                                            //dev
                                            Log.i(TAG, "onAuthStateChanged: " + "User: " + user.getEmail() + " / Signed In");
                                            //re-direct user to home activity
                                            Intent homeActivityIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(homeActivityIntent);
                                            //kill login activity
                                            LoginActivity.this.finish();
                                        }
                                    }

                                });
                            }
                        });

                    }

                }

                return;

            case R.id.signUpButton:
                //dev
                Log.i(TAG, "onClick: SIGN UP BTN");

                //check for network
                if (!FieldsCheck.networkConnection(this)) {
                    //dev
                    Log.i(TAG, "onClick: ALERT USER");
                } else {

                    //transition to form activity to create user
                    Intent formIntent = new Intent(LoginActivity.this, FormActivity.class);
                    startActivity(formIntent);
                }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //add listener for firebase auth
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //check listener
        if (mAuthListener != null) {
            //remove listener on stop
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
