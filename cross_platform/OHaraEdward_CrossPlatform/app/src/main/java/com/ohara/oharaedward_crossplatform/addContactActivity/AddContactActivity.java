package com.ohara.oharaedward_crossplatform.addContactActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;

//Edward O'Hara
//AddContactActivity
public class AddContactActivity extends AppCompatActivity implements GetContactData {

    //TAG
    private static final String TAG = "AddContactActivity";

    //stored properties
    DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        //get instance of contact frag
        AddContactFragment addContactFrag = AddContactFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.addContactContainer, addContactFrag,
                AddContactFragment.ADD_CONTACT_TAG).commit();

        //get instance of firebase database
        mReference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void getContactData(ContactData contactData) {

        //dev
        Log.i(TAG, "getContactData: FIRST NAME " + contactData.firstName);
        Log.i(TAG, "getContactData: LAST NAME " + contactData.lastName);
        Log.i(TAG, "getContactData: PHONE " + contactData.phoneNumber);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            //dev
            Log.i(TAG, "getContactData: USER " + user);

            //get uid
            String userID = user.getUid();

            //set new db ref to push to firebase
            DatabaseReference newRef = mReference.child("contacts").child(userID).push();

            //keep contacts sync when offline
            newRef.keepSynced(true);

            //set value to push
            newRef.setValue(contactData);
        }
        //close activity
        this.setResult(Activity.RESULT_OK);
        finish();
    }
}