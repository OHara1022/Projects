package com.ohara.oharaedward_crossplatform.addContactActivity;

import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneNumberUtils;
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
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;

//Edward O'Hara
//AddContactFragment
public class AddContactFragment extends Fragment{

    //TAG
    private static final String TAG = "AddContactFragment";
    public static final String ADD_CONTACT_TAG = "ADD_CONTACT_TAG";

    //stored properties
    private EditText mFirstNameET;
    private EditText mLastNameET;
    private EditText mPhoneNumberET;
    GetContactData mListener;

    //new instance constructor
    public static AddContactFragment newInstance() {
        //instance of frag
        AddContactFragment addTipFrag = new AddContactFragment();
        //bundle info
        Bundle args = new Bundle();
        //set arguments
        addTipFrag.setArguments(args);
        //return frag w/ info
        return addTipFrag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GetContactData) {
            //attach listener
            mListener = (GetContactData) context;
        } else {
            throw new IllegalArgumentException("Please add GetContactData interface to frag");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View addContactView = inflater.inflate(R.layout.add_contact_fragment, container, false);

        //init ui
        mFirstNameET = (EditText) addContactView.findViewById(R.id.firstNameET);
        mLastNameET = (EditText) addContactView.findViewById(R.id.lastNameET);
        mPhoneNumberET = (EditText) addContactView.findViewById(R.id.numberET);

        return addContactView;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.addContactData:

                //check empty fields
                if (FieldsCheck.isEmpty(mFirstNameET) || FieldsCheck.isEmpty(mLastNameET) || FieldsCheck.isEmpty(mPhoneNumberET)){
                    //alert user no empty field
                    Toast.makeText(getActivity(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //get values
                String firstName = mFirstNameET.getText().toString().trim();
                String lastName = mLastNameET.getText().toString().trim();
                Long phoneNumber = Long.parseLong(mPhoneNumberET.getText().toString().trim());

                //check phone number length
                if (mPhoneNumberET.length() < 10){
                    //alert user phone number must be ten digits
                    Toast.makeText(getActivity(), "Phone number must be 10 digits", Toast.LENGTH_LONG).show();
                    return false;
                }

                //populate object
                ContactData newData = new ContactData(firstName, lastName, phoneNumber);

                //dev
                Log.i(TAG, "onOptionsItemSelected: FIRST " + newData.firstName);
                Log.i(TAG, "onOptionsItemSelected: LAST " + newData.lastName);
                Log.i(TAG, "onOptionsItemSelected: PHONE " + newData.phoneNumber);

                //pass data to activity
                mListener.getContactData(newData);
                return true;
        }
        return false;
    }
}
