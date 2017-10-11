package com.ohara.oharaedward_1703.addTipActivity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;


//Edward O'Hara
//MDV469 - 1703
//AddTipActivity
public class AddTipActivity extends AppCompatActivity implements GetTipData {

    //TAG
    private static final String TAG = "AddTipActivity";

    //stored properties
    DatabaseReference mDatabaseRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tip);

        //get instance of AddTipFragment
        AddTipFragment addTipFragment = AddTipFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.addTipContainer, addTipFragment,
                AddTipFragment.ADD_TIP_TAG).commit();

        //get instance of firebaseAuth
        mAuth = FirebaseAuth.getInstance();
        //get instance of firebase database
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public void newTipData(TipData tipData) {

        //dev
        Log.i(TAG, "newTipData: " + " " + tipData.selectedDate);
        Log.i(TAG, "newTipData: " + " " + tipData.totalSales);
        Log.i(TAG, "newTipData: " + " " + tipData.tipOutPercent);
        Log.i(TAG, "newTipData: " + " " + tipData.cashTips);
        Log.i(TAG, "newTipData: " + " " + tipData.creditCardTips);
        Log.i(TAG, "newTipData: " + " " + tipData.hoursWorked);
        Log.i(TAG, "newTipData: " + " " + tipData.note);

        //get current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //check user is not null
        if (user != null) {
            //dev
            Log.i(TAG, "onAuthStateChanged: TIPDATA" + " " + user);

            //get userID
            String userID = user.getUid();

            //set new DB ref to push data
            DatabaseReference newRef = mDatabaseRef.child("tips").child(userID).push();
            //set values of object passed
            newRef.setValue(tipData);
        }
        //close activity
        finish();
    }
}