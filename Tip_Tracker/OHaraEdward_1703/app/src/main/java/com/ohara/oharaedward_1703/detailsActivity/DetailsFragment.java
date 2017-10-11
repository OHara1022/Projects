package com.ohara.oharaedward_1703.detailsActivity;

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
import com.ohara.oharaedward_1703.editTipActivity.EditTipActivity;


//Edward O'Hara
//MDV469 - 1703
//DetailsFragment
public class DetailsFragment extends Fragment {

    //TAG
    private static final String TAG = "DetailsFragment";
    public static final String DETAILS_TAG = "DETAILS_TAG";

    //stroed properties
    private static final String ARG_TIPS = "DetailsFragment.ARG_TIPS";
    private TipData mTipData;
    DatabaseReference mRefernece;
    String userID;
    String keyHolder;

    //new instance constructor
    public static DetailsFragment newInstance(TipData _tipData) {
        DetailsFragment detailsFrag = new DetailsFragment();
        Bundle args = new Bundle();
        //put serializable object
        args.putSerializable(ARG_TIPS, _tipData);
        detailsFrag.setArguments(args);
        return detailsFrag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set options menu to true
        setHasOptionsMenu(true);
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

        //getArgs
        Bundle args = getArguments();
        //get views
        View view = getView();

        //check args and view have values
        if (args != null && args.containsKey(ARG_TIPS) && view != null) {

            //get object data
            mTipData = (TipData) args.getSerializable(ARG_TIPS);

            //populate views with object from args
            TextView tv = (TextView) view.findViewById(R.id.dateTV);
            tv.setText(mTipData.selectedDate);

            tv = (TextView) view.findViewById(R.id.hoursWorkedTV);
            tv.setText(String.valueOf(mTipData.hoursWorked));

            tv = (TextView) view.findViewById(R.id.totalSalesTV);
            tv.setText(String.valueOf(mTipData.totalSales));

            tv = (TextView) view.findViewById(R.id.tipPercentTV);
            tv.setText(String.valueOf(mTipData.tipOutPercent));

            tv = (TextView) view.findViewById(R.id.totalCashTipTV);
            tv.setText(String.valueOf(mTipData.cashTips));

            tv = (TextView) view.findViewById(R.id.totalCreditTipTV);
            tv.setText(String.valueOf(mTipData.creditCardTips));

            tv = (TextView) view.findViewById(R.id.totalTipsTV);
            tv.setText(String.valueOf(Math.round(mTipData.totalTips)));

            tv = (TextView) view.findViewById(R.id.totalTipsPerHrTV);
            tv.setText(String.valueOf(Math.round(mTipData.totalPerHr)));

            tv = (TextView) view.findViewById(R.id.noteTv);
            tv.setText(mTipData.note);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //set add menu item
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return frag layout
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.editTips:
                //dev
                Log.i(TAG, "onOptionsItemSelected: DATE" + mTipData);

                //get intent and pass data
                Intent sendData = new Intent(getActivity(), EditTipActivity.class);
                sendData.putExtra(DetailsActivity.EXTRA_DATE, mTipData.selectedDate);
                sendData.putExtra(DetailsActivity.EXTRA_HOURS_WORKED, mTipData.hoursWorked);
                sendData.putExtra(DetailsActivity.EXTRA_TOTAL_SALES, mTipData.totalSales);
                sendData.putExtra(DetailsActivity.EXRTA_TIP_PERCENT, mTipData.tipOutPercent);
                sendData.putExtra(DetailsActivity.EXTRA_CASH_TIPS, mTipData.cashTips);
                sendData.putExtra(DetailsActivity.EXTRA_CREDIT_TIPS, mTipData.creditCardTips);
                sendData.putExtra(DetailsActivity.EXTRA_NOTE, mTipData.note);
                startActivity(sendData);
                return true;

            case R.id.deleteTips:

                //delete current tip data
                deleteTipData();
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
                return true;
        }
        return false;
    }

    //delete data from firebase DB
    public void deleteTipData() {

        mRefernece.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (mTipData.selectedDate.equals(snapshot.child("selectedDate").getValue())){

                        //dev
                        Log.i(TAG, "onDataChange: " + snapshot.child("selectedDate").getValue());
                        Log.i(TAG, "onDataChange: SNAPSHOT!!!!!!!" + snapshot.getKey());

                        //remove the reference from firebase
                        mRefernece.child(snapshot.getKey()).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
