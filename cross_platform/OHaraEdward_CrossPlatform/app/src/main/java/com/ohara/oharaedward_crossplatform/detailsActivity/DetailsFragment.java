package com.ohara.oharaedward_crossplatform.detailsActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.ohara.oharaedward_crossplatform.editContactActivity.EditContactActivity;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;

//Edward O'Hara
//DetailsFragment
public class DetailsFragment extends Fragment {

    //TAG
    private static final String TAG = "DetailsFragment";
    public static final String DETAILS_TAG = "DEATILS_TAG";

    //stored properties
    private static final String ARG_CONTACTS = "DETAILSFRAGMENT.ARG_CONTACTS";
    private ContactData mContactData;
    DatabaseReference mReference;
    String userID;
    private static final int REQUEST_EDIT = 0x02002;//result code

    //new instance
    public static DetailsFragment newInstance(ContactData _contactData){
        DetailsFragment detailsFrag = new DetailsFragment();
        Bundle args = new Bundle();
        //put serializable object
        args.putSerializable(ARG_CONTACTS, _contactData);
        detailsFrag.setArguments(args);
        return detailsFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){

            //get user id
            userID = user.getUid();

            //get ref to DB
            mReference = FirebaseDatabase.getInstance().getReference().child("contacts").child(userID);

            mReference.keepSynced(true);

        }

        //get arguments
        Bundle args = getArguments();
        //getView
        View view = getView();

        //check args and view have values
        if (args != null && args.containsKey(ARG_CONTACTS) && view != null){

            //get data
            mContactData = (ContactData) args.getSerializable(ARG_CONTACTS);

            //populate view w/ arg data
            TextView tv = (TextView) view.findViewById(R.id.nameTV);
            tv.setText(mContactData.firstName + " " + mContactData.lastName);

            tv = (TextView) view.findViewById(R.id.phoneTV);
            tv.setText(String.valueOf(mContactData.phoneNumber));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            
            case R.id.editContacts:
                //dev
                Log.i(TAG, "onOptionsItemSelected: EDIT");

                //get intent and pass data
                Intent sendIntentData = new Intent(getActivity(), EditContactActivity.class);
                sendIntentData.putExtra(DetailsActivity.EXTRA_FIRST_NAME, mContactData.firstName);
                sendIntentData.putExtra(DetailsActivity.EXTRA_LAST_NAME, mContactData.lastName);
                sendIntentData.putExtra(DetailsActivity.EXTRA_PHONE_NUMBER, mContactData.phoneNumber);
                startActivityForResult(sendIntentData, REQUEST_EDIT);
                return true;

            case R.id.deleteContacts:
                //dev
                Log.i(TAG, "onOptionsItemSelected: DELETE ");

                    //delete current contact selected
                    deleteSelectedContact();
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                    return true;

        }

        return false;
    }

    //method to delete selected contact from DB
    public void deleteSelectedContact(){

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if (mContactData.firstName.equals(snapshot.child("firstName").getValue())){

                        //dev
                        Log.i(TAG, "onDataChange: SNAPSHOT " + snapshot.child("firstName").getValue());
                        Log.i(TAG, "onDataChange: KEY TO DELETE "  + snapshot.getKey());

                        //remove contact from DB
                        mReference.child(snapshot.getKey()).removeValue();

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
