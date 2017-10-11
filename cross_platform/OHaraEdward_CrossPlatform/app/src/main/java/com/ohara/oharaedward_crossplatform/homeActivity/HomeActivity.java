package com.ohara.oharaedward_crossplatform.homeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;
import com.ohara.oharaedward_crossplatform.detailsActivity.DetailsActivity;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;

//Edward O'Hara
//HomeActivity
public class HomeActivity extends AppCompatActivity implements OnContactSelected {

    //TAG
    private static final String TAG = "HomeActivity";
    //result code
    private static final int REQUEST_DETAILS = 0x01001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //no data TV, inform user to add data
        TextView noData = (TextView) findViewById(R.id.noInfoTV);
        FieldsCheck.noData(false, noData);

        //enable db to read offline
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //display list frag with added contact data
        HomeListFragment listFrag = HomeListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.listContainer, listFrag,
                HomeListFragment.LIST_TAG).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //dev
        Log.i(TAG, "onActivityResult: BACK FROM DETAILS");

            //display list frag with updated result from details
            HomeListFragment listFrag = HomeListFragment.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.listContainer, listFrag,
                    HomeListFragment.LIST_TAG).commit();

    }
    @Override
    public void onContactSelected(ContactData contactData) {

        //dev
        Log.i(TAG, "onContactSelected: FIRST " + contactData.firstName);
        Log.i(TAG, "onContactSelected: LAST " + contactData.lastName);
        Log.i(TAG, "onContactSelected: PHONE " + contactData.phoneNumber);

            //pass data w/ intent
            Intent detailsIntent = new Intent(HomeActivity.this, DetailsActivity.class);
            detailsIntent.putExtra(DetailsActivity.EXTRA_FIRST_NAME, contactData.firstName);
            detailsIntent.putExtra(DetailsActivity.EXTRA_LAST_NAME, contactData.lastName);
            detailsIntent.putExtra(DetailsActivity.EXTRA_PHONE_NUMBER, contactData.phoneNumber);
            startActivityForResult(detailsIntent, REQUEST_DETAILS);

    }



}
