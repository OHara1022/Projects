package com.ohara.oharaedward_1703.detailsActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;
import com.ohara.oharaedward_1703.homeActivity.HomeListFragment;

//Edward O'Hara
//MDV469 - 1703
//DetailsActivity
public class DetailsActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "DetailsActivity";

    //EXTRAS
    public static final String EXTRA_DATE = "com.ohara.android.EXTRA_DATE";
    public static final String EXTRA_HOURS_WORKED = "com.ohara.android.EXTRA_HOURS_WORKED";
    public static final String EXTRA_TOTAL_SALES = "com.ohara.android.EXTRA_TOTAL_SALES";
    public static final String EXRTA_TIP_PERCENT = "com.ohara.android.EXTRA_TIP_PERCENT";
    public static final String EXTRA_CASH_TIPS = "com.ohara.android.EXTRA_CASH_TIPS";
    public static final String EXTRA_CREDIT_TIPS = "com.ohara.android.EXTRA_CREDIT_TIPS";
    public static final String EXTRA_AFTER_TIP_OUT = "com.ohara.android.EXTRA_AFTER_TIP_OUT";
    public static final String EXTRA_TIPS_PER_HR = "com.ohara.android.EXTRA_TIPS_PER_HR";
    public static final String EXTRA_NOTE = "com.ohara.android.EXTRA_NOTE";
    //testing
    public static final String EXTRA_KEY = "com.ohara.android.EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //get intent
        Intent intent = getIntent();

        //get intent extras
        String date = intent.getStringExtra(EXTRA_DATE);
        Long hoursWorked = intent.getLongExtra(EXTRA_HOURS_WORKED, 0);
        Long sales = intent.getLongExtra(EXTRA_TOTAL_SALES, 0);
        Long percent = intent.getLongExtra(EXRTA_TIP_PERCENT, 0);
        Long cash = intent.getLongExtra(EXTRA_CASH_TIPS, 0);
        Long credit = intent.getLongExtra(EXTRA_CREDIT_TIPS, 0);
        String note = intent.getStringExtra(EXTRA_NOTE);
        double afterTipOut = intent.getDoubleExtra(EXTRA_AFTER_TIP_OUT, 0);
        double tipsPerHour = intent.getDoubleExtra(EXTRA_TIPS_PER_HR, 0);

        //dev
        Log.i(TAG, "onCreate: DATE!!!" + date);
        Log.i(TAG, "onCreate: HOURS!!! " + hoursWorked);
        Log.i(TAG, "onCreate: SALES!! " + sales);
        Log.i(TAG, "onCreate: PERCENT!!! " + percent);
        Log.i(TAG, "onCreate: CASH!!! " + cash);
        Log.i(TAG, "onCreate: CREDIT!!! " + credit);
        Log.i(TAG, "onCreate: NOTE!!! " + note);

        //populate object w/ intent data
        TipData tipData = new TipData(date, sales, percent, cash, credit, hoursWorked);

        if (note != null){
            tipData.note = note;
        }

        //get totals
        tipData.totalTips = afterTipOut;
        tipData.totalPerHr = tipsPerHour;

        //pass object to detail frag to display tip data
        DetailsFragment detailsFragment = DetailsFragment.newInstance(tipData);
        getFragmentManager().beginTransaction().replace(R.id.detailsContainer, detailsFragment
                , DetailsFragment.DETAILS_TAG).commit();
    }


}
