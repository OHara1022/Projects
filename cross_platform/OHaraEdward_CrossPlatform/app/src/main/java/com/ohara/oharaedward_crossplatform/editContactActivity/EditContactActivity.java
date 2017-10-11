package com.ohara.oharaedward_crossplatform.editContactActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;
import com.ohara.oharaedward_crossplatform.detailsActivity.DetailsActivity;

//Edward O'Hara
//EditContactActivity
public class EditContactActivity extends AppCompatActivity {


    //TAG
    private static final String TAG = "EditContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        //get intent
        Intent intent = getIntent();

        //get intent extras
        String firstName = intent.getStringExtra(DetailsActivity.EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(DetailsActivity.EXTRA_LAST_NAME);
        Long phoneNumber = intent.getLongExtra(DetailsActivity.EXTRA_PHONE_NUMBER, 0);

        //dev
        Log.i(TAG, "onCreate: FIRST " + firstName);
        Log.i(TAG, "onCreate: LAST " + lastName);
        Log.i(TAG, "onCreate: PHONE " + phoneNumber);

        //populate class w/ intent extras
        ContactData contactData = new ContactData(firstName, lastName, phoneNumber);

        //get instance of edit fragment
        EditContactFragment editFrag = EditContactFragment.newInstance(contactData);
        getFragmentManager().beginTransaction().replace(R.id.editContactContainer, editFrag,
                EditContactFragment.EDIT_CONTACT_TAG).commit();

    }
}
