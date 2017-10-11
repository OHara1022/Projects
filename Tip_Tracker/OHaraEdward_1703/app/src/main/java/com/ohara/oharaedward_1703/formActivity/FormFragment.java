package com.ohara.oharaedward_1703.formActivity;

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
import com.ohara.oharaedward_1703.formHelpers.FieldsCheck;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.User;

//Edward O'Hara
//MDV469 - 1703
//FormFragment
public class FormFragment extends Fragment {

    //TAG
    private static final String TAG = "FormFragment";
    public static final String FORM_TAG = "FORM_TAG";

    //edit text fields
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;

    //interface listener to pass user input to form activity
    GetUserData mListener;

    //new instance constructor
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

    //attach instance interface
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetUserData) {
            //attach listener
            mListener = (GetUserData) context;

        } else {
            //throw exception to add interface
            throw new IllegalArgumentException("Please add GetUserData interface to form fragment");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set option menu to true
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //custom view for form fragment
        View formView = inflater.inflate(R.layout.form_fragment, container, false);

        //init UI
        mEmailEditText = (EditText) formView.findViewById(R.id.addEmailEditText);
        mPasswordEditText = (EditText) formView.findViewById(R.id.addPasswordEditText);
        mFirstNameEditText = (EditText) formView.findViewById(R.id.firstNameEditText);
        mLastNameEditText = (EditText) formView.findViewById(R.id.lastNameEditText);

        //return create view
        return formView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //set form menu item
        inflater.inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addUser:

                //check for empty text fields
                if (FieldsCheck.isEmpty(mFirstNameEditText) || FieldsCheck.isEmpty(mLastNameEditText)
                        || FieldsCheck.isEmpty(mEmailEditText) || FieldsCheck.isEmpty(mPasswordEditText)){

                    //alert user no empty field
                    Toast.makeText(getActivity(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //check for valid email & password
                if (FieldsCheck.validEmail(mEmailEditText.getText().toString().trim(), getActivity())
                        && FieldsCheck.validPassword(mPasswordEditText.getText().toString().trim(), getActivity())) {
                    //populate user data with input text
                    User newUser = new User(mFirstNameEditText.getText().toString().trim(),
                            mLastNameEditText.getText().toString().trim(), mEmailEditText.getText().toString().trim(),
                            mPasswordEditText.getText().toString().trim());

                    //send data to form activity
                    mListener.newUser(newUser);

                    //dev
                    Log.i(TAG, "onOptionsItemSelected: NEW USER CREATED " + newUser.firstName);

                    return true;
                }
        }
        return false;
    }
}