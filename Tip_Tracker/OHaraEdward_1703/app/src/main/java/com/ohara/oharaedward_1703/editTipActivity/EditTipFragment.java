package com.ohara.oharaedward_1703.editTipActivity;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;

import java.util.HashMap;
import java.util.Map;

//Edward O'Hara
//MDV469 - 1703
//EditTipFragment
public class EditTipFragment extends Fragment {

    //TAG
    private static final String TAG = "EditTipFragment";
    public static final String EDIT_TIP_TAG = "EDIT_TIP_TAG";

    //arguments key
    private static final String ARG_TIP_DATA = "EDITTIPFRAGEMNT.ARG_TIP_DATA";

    //stored properties
    private TextView mDate;
    private EditText mHoursWorked;
    private EditText mTotalSales;
    private EditText mTipPercent;
    private EditText mCashTip;
    private EditText mCreditTip;
    private EditText mNote;
    DatabaseReference mRefernece;
    String userID;
    String keyHolder;

    //new instance constructor
    public static EditTipFragment newInstance(TipData _tipData) {

        EditTipFragment editTipFrag = new EditTipFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIP_DATA, _tipData);
        editTipFrag.setArguments(args);
        return editTipFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set options menu to true
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View editTipView = inflater.inflate(R.layout.edit_tip_fragment, container, false);

        //init views
        mDate = (TextView) editTipView.findViewById(R.id.dateTextView);
        mHoursWorked = (EditText) editTipView.findViewById(R.id.hoursWorkedEditText);
        mTotalSales = (EditText) editTipView.findViewById(R.id.totalSalesEditText);
        mTipPercent = (EditText) editTipView.findViewById(R.id.tipOutPercentEditText);
        mCashTip = (EditText) editTipView.findViewById(R.id.cashTipsEditText);
        mCreditTip = (EditText) editTipView.findViewById(R.id.creditTipsEditText);
        mNote = (EditText) editTipView.findViewById(R.id.noteEditText);

        return editTipView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //check for current user
        if (user != null) {
            //get userID
            userID = user.getUid();
            //get instance of firebase database
            mRefernece = FirebaseDatabase.getInstance().getReference().child("tips").child(userID);
        }

        //get args
        Bundle args = getArguments();
        //populate object with arg data
        final TipData mTipData = (TipData) args.getSerializable(ARG_TIP_DATA);

        //check args and object has a value
        if (mTipData != null && args.containsKey(ARG_TIP_DATA)) {

            //dev
            Log.i(TAG, "onCreateView: DATE" + mTipData.selectedDate);

            //set edit text with user data
            mDate.setText(mTipData.selectedDate);
            mHoursWorked.setText(String.valueOf(mTipData.hoursWorked));
            mTotalSales.setText(String.valueOf(mTipData.totalSales));
            mTipPercent.setText(String.valueOf(mTipData.tipOutPercent));
            mCashTip.setText(String.valueOf(mTipData.cashTips));
            mCreditTip.setText(String.valueOf(mTipData.creditCardTips));
            mNote.setText(String.valueOf(mTipData.note));
        }

        mRefernece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (mTipData != null) {

                        if (mTipData.selectedDate.equals(snapshot.child("selectedDate").getValue())) {
                            //dev
                            Log.i(TAG, "onDataChange: " + snapshot.child("selectedDate").getValue());
                            Log.i(TAG, "onDataChange: SNAPSHOT!!!!!!!" + snapshot.getKey());

                            //get key of date
                            keyHolder = snapshot.getKey();
                            Log.i(TAG, "onDataChange: DATEHOLDER " + keyHolder);
                        }
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

            case R.id.saveTips:

                //get edit text values
                Long sales = Long.parseLong(mTotalSales.getText().toString().trim());
                Long percent = Long.parseLong(mTipPercent.getText().toString().trim());
                Long cash = Long.parseLong(mCashTip.getText().toString().trim());
                Long credit = Long.parseLong(mCreditTip.getText().toString().trim());
                Long hours = Long.parseLong(mHoursWorked.getText().toString().trim());

                //populate new object to map
                TipData newData = new TipData(mDate.getText().toString().trim(),
                        sales,
                        percent,
                        cash,
                        credit,
                        hours);


                //data toMap
                Map<String, Object> postData = newData.toMap();
                //child to update
                Map<String, Object> childUpdates = new HashMap<>();
                //put children
                childUpdates.put(keyHolder, postData);
                //update reference
                mRefernece.updateChildren(childUpdates);
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();

                return true;
        }

        return false;
    }


}
