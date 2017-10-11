package com.ohara.oharaedward_1703.loginActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
import com.ohara.oharaedward_1703.formHelpers.FieldsCheck;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.formActivity.FormActivity;
import com.ohara.oharaedward_1703.homeActivity.HomeActivity;

//Edward O'Hara
//MDV469 - 1703
//LoginActivity
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //TAG
    private static final String TAG = "LoginActivity";

    //stored properties
    EditText mEmailField;
    EditText mPasswordField;
    //firebase auth
    private FirebaseAuth mAuth;
    //firebase listener
    private FirebaseAuth.AuthStateListener mAuthListener;
    //alert dialog variables
    String mEmailAddress;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set context to this activity
        mContext = this;

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
                    mEmailField.setText("");
                    mPasswordField.setText("");
                }
            }
        };

        //init email & password fields
        mEmailField = (EditText) findViewById(R.id.emailEditText);
        mPasswordField = (EditText) findViewById(R.id.passwordEditText);

        //init buttons
        Button signUpButton = (Button) findViewById(R.id.signUpButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button forgetPasswordButton = (Button) findViewById(R.id.forgetPasswordButton);

        //set onClickListener to buttons
        signUpButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgetPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.loginButton:
                //dev
                Log.i(TAG, "onClick: " + "LOGIN");

                //check no empty fields
                if (FieldsCheck.isEmpty(mEmailField) || FieldsCheck.isEmpty(mPasswordField)) {
                    //alert user no empty fields
                    Toast.makeText(this, "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check if email and password are valid
                if (FieldsCheck.validEmail(mEmailField.getText().toString().trim(), this)
                        && FieldsCheck.validPassword(mPasswordField.getText().toString().trim(), this)) {

                    //sign in user w/ email and password
                    mAuth.signInWithEmailAndPassword(mEmailField.getText().toString().toLowerCase().trim(),
                            mPasswordField.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                return;

            case R.id.signUpButton:
                //dev
                Log.i(TAG, "onClick: " + "SIGN UP");

                //transition to form activity to sign up user
                Intent formActivityIntent = new Intent(LoginActivity.this, FormActivity.class);
                startActivity(formActivityIntent);
                return;

            case R.id.forgetPasswordButton:
                //dev
                Log.i(TAG, "onClick: " + "FORGET PASSWORD");
                //present dialog
                passwordAlertDialog();
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

    //method to reset user password if forgotten
    public void passwordAlertDialog() {

        //get new alertbuilder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //get edittext from custom layout
        final EditText recoverPassword = new EditText(this);

        //set input type to email address
        recoverPassword.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        //set title of alert dialog
        builder.setTitle("Please Enter Email Address");

        //set the view to be displayed
        builder.setView(recoverPassword);

        //add buttons
        builder.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dev
                Log.i(TAG, "onClick ALERT: OK");
                Log.i(TAG, "onClick: " + recoverPassword.getText().toString());

                //if field not empty proceed
                if (!FieldsCheck.isEmpty(recoverPassword)) {
                    mEmailAddress = recoverPassword.getText().toString();
                    //dev
                    Log.i(TAG, "onClick: RECOVER EMAIL" + mEmailAddress);

                    //create forget password email thru firebase
                    mAuth.sendPasswordResetEmail(mEmailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            //if task successful inform user
                            if (task.isSuccessful()) {
                                //dev
                                Log.i(TAG, "onComplete: EMAIL SENT");

                                //inform user email was sent
                                Toast.makeText(mContext, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dev
                Log.i(TAG, "onClick ALERT: CANCEL");
            }
        });

        //create alert dialog
        AlertDialog dialog = builder.create();
        //show alert dialog
        dialog.show();
    }
}