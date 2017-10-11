package com.ohara.oharaedward_crossplatform.homeActivity;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ohara.oharaedward_crossplatform.R;
import com.ohara.oharaedward_crossplatform.addContactActivity.AddContactActivity;
import com.ohara.oharaedward_crossplatform.dataModel.ContactData;
import com.ohara.oharaedward_crossplatform.fieldsCheck.FieldsCheck;
import com.ohara.oharaedward_crossplatform.loginActivity.LoginActivity;
import java.util.ArrayList;

//Edward O'Hara
//HomeListFragment
public class HomeListFragment extends ListFragment {

    //TAG
    private static final String TAG = "HomeListFragment";
    public static final String LIST_TAG = "LIST_TAG";
    private static final int REQUEST_ADD = 0x02002;

    //stored properties
    TextView noInfo;
    FirebaseAuth mAuth;
    DatabaseReference mReference;
    FirebaseUser mUser;
    String mUserID;
    ContactData mQueriedContactData;
    private OnContactSelected mListener;
    public ArrayList<ContactData> mContactArrayList;
    public ArrayAdapter<ContactData> mAdapter;

    //new instance of homeListFrag
    public static HomeListFragment newInstance() {
        //instance of form frag
        HomeListFragment homeFrag = new HomeListFragment();
        //bundle info
        Bundle args = new Bundle();
        //set arguments
        homeFrag.setArguments(args);
        //return frag w/ info
        return homeFrag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //attach listener
        if (context instanceof OnContactSelected) {
            mListener = (OnContactSelected) context;
        } else {
            throw new IllegalArgumentException("Please add HomeListFrag interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show options menu item
        setHasOptionsMenu(true);
        //get instance of firebase auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init arraylist
        mContactArrayList = new ArrayList<>();

        //init infoTV
        noInfo = (TextView) getActivity().findViewById(R.id.noInfoTV);

        //get current user
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {

            //get user id
            mUserID = mUser.getUid();

            //get instance of DB
            mReference = FirebaseDatabase.getInstance().getReference().child("contacts").child(mUserID);

            //keep contacts sync when offline
            mReference.keepSynced(true);

            //get data
            getContactData();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addContact:
                //dev
                Log.i(TAG, "onOptionsItemSelected: ADD CONTACT");

                //transition to add contact activity
                Intent addContactIntent = new Intent(getActivity(), AddContactActivity.class);
                startActivityForResult(addContactIntent, REQUEST_ADD);
                return true;

            case R.id.signOutUser:
                //dev
                Log.i(TAG, "onOptionsItemSelected: SIGN OUT");

                //sign out current user
                signOut();

        }
        return false;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //dev
        Log.i(TAG, "onListItemClick:  " + l.getAdapter().getItem(position));

        if (mListener != null) {

            //send position of person selected
            mListener.onContactSelected((ContactData) l.getAdapter().getItem(position));
            //dev
            Log.i(TAG, "onListItemClick: CLICKED!!!!!");
        }
    }

    //method to retrieve data
    public void getContactData() {


        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear list
                mContactArrayList.clear();

                //check snapshot has value
                if (dataSnapshot != null) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        //check for current user
                        if (mUser != null) {

                            //dev
                            Log.i(TAG, "onDataChange: NAME " + snapshot.child("firstName").getValue());
                            Log.i(TAG, "onDataChange: NAME " + snapshot.child("lastName").getValue());
                            Log.i(TAG, "onDataChange: NAME " + snapshot.child("phoneNumber").getValue());

                            //get data
                            String first = (String) snapshot.child("firstName").getValue();
                            String last = (String) snapshot.child("lastName").getValue();
                            Long phone = (Long) snapshot.child("phoneNumber").getValue();

                            //populate class w/ new data
                            mQueriedContactData = new ContactData(first, last, phone);

                            //populate array
                            mContactArrayList.add(mQueriedContactData);
                            if (mContactArrayList != null) {
                                FieldsCheck.noData(true, noInfo);
                            }
                        }
                    }


                    //set adapter
                    mAdapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            mContactArrayList);

                    //set adapter to list
                    setListAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //method to sign out current user
    private void signOut() {

        //firebase signout method
        mAuth.signOut();
        //dev
        Log.i(TAG, "onClick: SIGNOUT");

        //transition user back to login screen
        Intent homeActivity = new Intent(getActivity(), LoginActivity.class);
        startActivity(homeActivity);
    }
}