package com.ohara.oharaedward_crossplatform.formActivity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.UserData;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;

//Edward O'Hara
//FormFragment
public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";
    public static final String FORM_TAG = "FORM_TAG";

    //edit text fields
    EditText mEmailEditText;
    EditText mPasswordEditText;
    EditText mFirstNameEditText;
    EditText mLastnameEditText;

    //interface listener
    GetUserData mListener;

    //new instance of form frag
    public static FormFragment newInstance() {
        //instance of form frag
        FormFragment formFragment = new FormFragment();
        //bundle info
        Bundle args = new Bundle();
        //set arguments
        formFragment.setArguments(args);
        //return frag w/ info
        return formFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GetUserData) {
            //attach listener
            mListener = (GetUserData) context;
        } else {
            throw new IllegalArgumentException("Please add get user data interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set options menu to true, show add button
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflate options menu
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //init fragment view
        View formView = inflater.inflate(R.layout.form_fragment, container, false);

        //init ui
        mEmailEditText = (EditText) formView.findViewById(R.id.addEmailEditText);
        mPasswordEditText = (EditText) formView.findViewById(R.id.addPasswordEditText);
        mFirstNameEditText = (EditText) formView.findViewById(R.id.firstNameEditText);
        mLastnameEditText = (EditText) formView.findViewById(R.id.lastNameEditText);

        //return created view
        return formView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addUser:

                //check for empty edit text
                if (FieldsCheck.isEmpty(mEmailEditText) || FieldsCheck.isEmpty(mPasswordEditText)
                        || FieldsCheck.isEmpty(mFirstNameEditText) || FieldsCheck.isEmpty(mLastnameEditText)) {

                    //alert user no empty field
                    Toast.makeText(getActivity(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //get value of edit text
                String email = mEmailEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String firstName = mFirstNameEditText.getText().toString().trim();
                String lastName = mLastnameEditText.getText().toString().trim();

                //check for valid email and password
                if (FieldsCheck.validEmail(email, getActivity()) &&
                        FieldsCheck.validPassword(password, getActivity())) {

                    //populate user
                    UserData newUser = new UserData(firstName, lastName, email, password);

                    mListener.getUser(newUser);

                    //dev
                    Log.i(TAG, "onOptionsItemSelected: NEW USER " + firstName);
                    Log.i(TAG, "onOptionsItemSelected: NEW USER " + lastName);
                    Log.i(TAG, "onOptionsItemSelected: NEW USER " + email);
                    Log.i(TAG, "onOptionsItemSelected: NEW USER " + password);

                    return true;
                }
        }

        return false;
    }
}