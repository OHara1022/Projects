package com.ohara.oharaedward_crossplatform.editContactActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;
import java.util.HashMap;
import java.util.Map;

//Edward O'Hara
//EditContactFragment
public class EditContactFragment extends Fragment {

    //TAG
    private static final String TAG = "EditContactActivity";
    public static final String EDIT_CONTACT_TAG = "EDIT_CONTACT_TAG";

    //argument key
    private static final String ARG_CONTACT_DATA = "EditContactFragment.ARG_CONTACT_DATA";

    //stored properties
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mPhoneNumber;
    DatabaseReference mRef;
    String userID;
    String keyHolder;
     ContactData mContactData;

    //new instance constructor
    public static EditContactFragment newInstance(ContactData _contactData){

        EditContactFragment editContactFragment = new EditContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_DATA, _contactData);
        editContactFragment.setArguments(args);
        return editContactFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set options menu to true
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //inflate options menu
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //inflate layout
        View editContactsView = inflater.inflate(R.layout.edit_contact_fragment, container, false);

        //init view
        mFirstName = (EditText) editContactsView.findViewById(R.id.firstNameEditText);
        mLastName = (EditText) editContactsView.findViewById(R.id.lastNameEditText);
        mPhoneNumber = (EditText) editContactsView.findViewById(R.id.numberEditText);
        //return inflated view
        return editContactsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //check for user
        if (user != null){

            //get userID
            userID = user.getUid();
            //get instance of db
            mRef = FirebaseDatabase.getInstance().getReference().child("contacts").child(userID);
        }

        //get arguments
        Bundle args = getArguments();
        //populate object w/ argument data
        mContactData = (ContactData) args.getSerializable(ARG_CONTACT_DATA);

        //check arguments & object have a value
        if (mContactData != null && args.containsKey(ARG_CONTACT_DATA)){

            //dev
            Log.i(TAG, "onActivityCreated: NAME " + mContactData.toString());

            //set edit text w/ contact data
            mFirstName.setText(mContactData.firstName);
            mLastName.setText(mContactData.lastName);
            mPhoneNumber.setText(String.valueOf(mContactData.phoneNumber));
        }

        //get key value for changes
        getKeyForChanges();
    }


    //method to get key value to change in db
    public void getKeyForChanges(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if (mContactData.firstName.equals(snapshot.child("firstName").getValue())){

                        //dev
                        Log.i(TAG, "onDataChange: SNAPSHOT " + snapshot.child("firstName").getValue());
                        Log.i(TAG, "onDataChange: KEY TO DELETE "  + snapshot.getKey());

                        //get key
                        keyHolder = snapshot.getKey();
                        Log.i(TAG, "onDataChange: NAME " + keyHolder);

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.saveChanges:

                String first = mFirstName.getText().toString().trim();
                String last = mLastName.getText().toString().trim();
                Long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString().trim());

                //populate new object to map
                ContactData newContact = new ContactData(first, last, phoneNumber);

                if (mPhoneNumber.length() < 10) {
                    //alert user phone number must be ten digits
                    Toast.makeText(getActivity(), "Phone number must be 10 digits", Toast.LENGTH_LONG).show();
                    return false;
                }

                //data toMap
                Map<String, Object> postContactData = newContact.toMap();
                //child to update
                Map<String, Object> childUpdates = new HashMap<>();
                //put children
                childUpdates.put(keyHolder, postContactData);
                //update reference
                mRef.updateChildren(childUpdates);
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                return true;
        }
        return false;
    }

}