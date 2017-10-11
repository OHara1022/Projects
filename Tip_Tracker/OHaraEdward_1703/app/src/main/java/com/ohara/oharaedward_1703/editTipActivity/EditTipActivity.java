package com.ohara.oharaedward_1703.editTipActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;
import com.ohara.oharaedward_1703.detailsActivity.DetailsActivity;

//Edward O'Hara
//MDV469 - 1703
//EditTipActivity
public class EditTipActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "EditTipActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tip);

        //get intent
        Intent intent = getIntent();

        //get intent extras
        String date = intent.getStringExtra(DetailsActivity.EXTRA_DATE);
        Long hoursWorked = intent.getLongExtra(DetailsActivity.EXTRA_HOURS_WORKED, 0);
        Long sales = intent.getLongExtra(DetailsActivity.EXTRA_TOTAL_SALES, 0);
        Long percent = intent.getLongExtra(DetailsActivity.EXRTA_TIP_PERCENT, 0);
        Long cash = intent.getLongExtra(DetailsActivity.EXTRA_CASH_TIPS, 0);
        Long credit = intent.getLongExtra(DetailsActivity.EXTRA_CREDIT_TIPS, 0);
        String note = intent.getStringExtra(DetailsActivity.EXTRA_NOTE);
        //TODO: get key as extra
        String key = intent.getStringExtra(DetailsActivity.EXTRA_KEY);

        Log.i(TAG, "onCreate: KEY!!!! " + key);

        //dev
        Log.i(TAG, "onCreate: " + date);
        Log.i(TAG, "onCreate: " + hoursWorked);
        Log.i(TAG, "onCreate: " + sales);
        Log.i(TAG, "onCreate: " + percent);
        Log.i(TAG, "onCreate: " + cash);
        Log.i(TAG, "onCreate: " + credit);
        Log.i(TAG, "onCreate: " + note);

        //populate class w/ extras
        TipData tipData = new TipData(date,sales, percent, cash, credit, hoursWorked);

        if (note != null){
            tipData.note = note;
        }

        //pass object to edit frag
        EditTipFragment frag = EditTipFragment.newInstance(tipData);
        getFragmentManager().beginTransaction().replace(R.id.editTipContainer, frag,
                EditTipFragment.EDIT_TIP_TAG).commit();
    }
}
