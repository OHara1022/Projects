package com.ohara.oharaedward_1703.homeActivity;

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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.addTipActivity.AddTipActivity;
import com.ohara.oharaedward_1703.breakdownActivity.BreakdownActivity;
import com.ohara.oharaedward_1703.dataModel.TipData;
import com.ohara.oharaedward_1703.detailsActivity.OnDateSelected;
import com.ohara.oharaedward_1703.formHelpers.FieldsCheck;
import com.ohara.oharaedward_1703.loginActivity.LoginActivity;

import java.util.ArrayList;

//Edward O'Hara
//MDV469 - 1703
//HomeListFragment
public class HomeListFragment extends ListFragment {

    //TAG
    private static final String TAG = "HomeListFragment";
    public static final String LIST_TAG = "LIST_TAG";

    //stored properties
    TextView noInfo;
    //firebase reference
    FirebaseAuth mAuth;
    DatabaseReference mRefernece;
    FirebaseUser mUser;
    //holder for current userID
    String mUserID;
    String mDate;
    Long mTotalSales;
    Long mPercent;
    Long mCash;
    Long mCredit;
    Long mHoursWorked;
    String mNote;
    double mTipOutPercent;
    //tipData class to populate
    TipData queriedTipData;
    //listener
    private OnDateSelected mListener;
    //arraylist of object
    public ArrayList<TipData> mTipsArrayList;
    //adapter
    public ArrayAdapter<TipData> adapter;

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
        if (context instanceof OnDateSelected) {
            mListener = (OnDateSelected) context;
        } else {
            throw new IllegalArgumentException("Please add HomeListFrag");
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init tipData arraylist
        mTipsArrayList = new ArrayList<>();

        //init noInfo TV
        noInfo = (TextView) getActivity().findViewById(R.id.noInfoTV);

        //get current user
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser != null) {
            mUserID = mUser.getUid();

            //get instance of firebase database
            mRefernece = FirebaseDatabase.getInstance().getReference().child("tips").child(mUserID);

            //get tip data
            getData();
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        //dev
        Log.i(TAG, "onListItemClick: " + " " + l.getAdapter().getItem(position));
        //check for listener
        if (mListener != null) {
            //send position on date selected
            mListener.onDateSelected((TipData) l.getAdapter().getItem(position));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addTips:

                //transition to addTip screen
                Intent addTipActivity = new Intent(getActivity(), AddTipActivity.class);
                startActivity(addTipActivity);

                return true;

            case R.id.breakdown:
                //TODO: send averages in intent
                Intent breakdownActivity = new Intent(getActivity(), BreakdownActivity.class);
                startActivity(breakdownActivity);
                return true;

            case R.id.signOutUser:
                //dev
                Log.i(TAG, "onOptionsItemSelected: SIGNED OUT");

                //sign out current user
                signOut();
                return true;
        }
        return false;
    }

    public void getData() {

        mRefernece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear list for reload of data
                mTipsArrayList.clear();

                //check snapshot has value
                if (dataSnapshot != null) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //dev
                        Log.i(TAG, "onDataChange: SNAPSHOT " + snapshot.getValue());

                        //check for current user
                        if (mUser != null) {
                            //dev
                            Log.i(TAG, "onDataChange: " + mUserID);
                            Log.i(TAG, "onDataChange: USER!!!! " + snapshot.getValue());
                            Log.i(TAG, "onDataChange: GETING DATE " + snapshot.child("selectedDate").getValue());

                            //get data from firebase
                            mDate = (String) snapshot.child("selectedDate").getValue();
                            mHoursWorked = (Long) snapshot.child("hoursWorked").getValue();
                            mTotalSales = (Long) snapshot.child("totalSales").getValue();
                            mPercent = (Long) snapshot.child("tipOutPercent").getValue();
                            mCash = (Long) snapshot.child("cashTips").getValue();
                            mCredit = (Long) snapshot.child("creditCardTips").getValue();
                            mNote = (String) snapshot.child("note").getValue();

                            //check percent value
                            if (mPercent == 1) {
                                //get percent
                                mTipOutPercent = mTotalSales * 0.01;
                            } else if (mPercent == 2) {
                                mTipOutPercent = mTotalSales * 0.02;
                            } else if (mPercent == 3) {
                                mTipOutPercent = mTotalSales * 0.03;
                            } else if (mPercent == 4) {
                                mTipOutPercent = mTotalSales * 0.04;
                                Log.i(TAG, "onDataChange: PERCENT OUT" + mTipOutPercent);
                            }else if (mPercent == 5){
                                mTipOutPercent = mTotalSales * 0.05;
                            }else if (mPercent == 6){
                                mTipOutPercent = mTotalSales * 0.06;
                            }else if (mPercent == 7){
                                mTipOutPercent = mTotalSales * 0.07;
                            }else if (mPercent == 8){
                                mTipOutPercent = mTotalSales * 0.08;
                            }else if (mTipOutPercent == 9){
                                mTipOutPercent = mTotalSales * 0.09;
                            }

                            //get total tip value
                            Long totalTips = mCash + mCredit;
                            //get tips after tipOut percent
                            double afterTipOut = totalTips - mTipOutPercent;
                            //get tips made per hour
                            double tipsPerHr = afterTipOut / mHoursWorked;

                            //dev
                            Log.i(TAG, "onDataChange: TOTAL TIP " + totalTips);//math check
                            Log.i(TAG, "onDataChange: AFTER TIP OUT " + Math.round(afterTipOut));//math check
                            Log.i(TAG, "onDataChange: PER HR " + Math.round(tipsPerHr));//math check

                            //load data to object
                            queriedTipData = new TipData(mDate, mTotalSales, mPercent, mCash, mCredit, mHoursWorked);
                            if (mNote != null) {
                                queriedTipData.note = mNote;
                            }
                            queriedTipData.totalTips = Math.round(afterTipOut);
                            queriedTipData.totalPerHr = Math.round(tipsPerHr);

                            //dev
                            Log.i(TAG, "onDataChange: DATE!!!! " + mDate);
                            Log.i(TAG, "onDataChange: SALES!!! " + mTotalSales);
                            Log.i(TAG, "onDataChange: PERCENT!!! " + mPercent);
                            Log.i(TAG, "onDataChange: CASH!!! " + mCash);
                            Log.i(TAG, "onDataChange: CREDIT!!! " + mCredit);
                            Log.i(TAG, "onDataChange: HOURS!!! " + mHoursWorked);
                            Log.i(TAG, "onDataChange: NOTE!!!! " + mNote);

                            if (mDate != null) {
                                //populate array
                                mTipsArrayList.add(queriedTipData);
                                FieldsCheck.noData(true, noInfo);
                            }
                        }
                    }
                    //set array adapter
                    adapter = new ArrayAdapter<>(
                            getActivity(),
                            android.R.layout.simple_list_item_1,
                            mTipsArrayList);

                    //set adapter to list
                    setListAdapter(adapter);
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
        Intent homeActivtity = new Intent(getActivity(), LoginActivity.class);
        startActivity(homeActivtity);
    }
}