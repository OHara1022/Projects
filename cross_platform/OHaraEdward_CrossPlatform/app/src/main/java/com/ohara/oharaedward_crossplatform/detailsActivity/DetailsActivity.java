package com.ohara.oharaedward_crossplatform.detailsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;

//Edward O'Hara
//DetailsActivity
public class DetailsActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "DetailsActivity";

    //EXTRAS
    public static final String EXTRA_FIRST_NAME = "com.ohara.android.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.ohara.android.EXTRA_LAST_NAME";
    public static final String EXTRA_PHONE_NUMBER = "com.ohara.android.EXTRA_PHONE_NUMER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //get intent
        Intent intent = getIntent();

        //get intent extras
        String first = intent.getStringExtra(EXTRA_FIRST_NAME);
        String last = intent.getStringExtra(EXTRA_LAST_NAME);
        Long phone = intent.getLongExtra(EXTRA_PHONE_NUMBER, 0);

        //dev
        Log.i(TAG, "onCreate: first " + first);
        Log.i(TAG, "onCreate: last " + last);
        Log.i(TAG, "onCreate: phone " + phone);

        //populate object w/ intent data
        ContactData contactData = new ContactData(first, last, phone);

        //pass object to frag
        DetailsFragment detailsFragment = DetailsFragment.newInstance(contactData);
        getFragmentManager().beginTransaction().replace(R.id.detailsContainer, detailsFragment
                , DetailsFragment.DETAILS_TAG).commit();

    }


}
