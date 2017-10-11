package com.ohara.oharaedward_1703.homeActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;
import com.ohara.oharaedward_1703.detailsActivity.DetailsActivity;
import com.ohara.oharaedward_1703.detailsActivity.OnDateSelected;
import com.ohara.oharaedward_1703.formHelpers.FieldsCheck;

//Edward O'Hara
//MDV469 - 1703
//HomeActivity
public class HomeActivity extends AppCompatActivity implements OnDateSelected {

    //TAG
    private static final String TAG = "HomeActivity";
    private static final int REQUEST_DETAILS = 0x01002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //no data TV, inform user to add tip
        TextView noData = (TextView) findViewById(R.id.noInfoTV);
        FieldsCheck.noData(false, noData);

        //display list frag with added tip date
        HomeListFragment listFrag = HomeListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.listContainer, listFrag,
                HomeListFragment.LIST_TAG).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //dev
        Log.i(TAG, "onActivityResult: BACK!!! ");

        //display list frag with added tip date
        HomeListFragment listFrag = HomeListFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.listContainer, listFrag,
                HomeListFragment.LIST_TAG).commit();

    }

    @Override
    public void onDateSelected(TipData tipData) {
        //dev
        Log.i(TAG, "onDateSelected: SELECTED" + tipData);
        Log.i(TAG, "onDateSelected: DATE " + tipData.selectedDate);
        Log.i(TAG, "onDateSelected: SALES " + tipData.totalSales);
        Log.i(TAG, "onDateSelected: PERCENT" + tipData.tipOutPercent);
        Log.i(TAG, "onDateSelected: NOTE " + tipData.note);

        //pass data on list selection w/ intent to details
        Intent detailActivityIntent = new Intent(HomeActivity.this, DetailsActivity.class);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_DATE, tipData.selectedDate);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_HOURS_WORKED, tipData.hoursWorked);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_TOTAL_SALES, tipData.totalSales);
        detailActivityIntent.putExtra(DetailsActivity.EXRTA_TIP_PERCENT, tipData.tipOutPercent);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_CASH_TIPS, tipData.cashTips);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_CREDIT_TIPS, tipData.creditCardTips);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_NOTE, tipData.note);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_AFTER_TIP_OUT, tipData.totalTips);
        detailActivityIntent.putExtra(DetailsActivity.EXTRA_TIPS_PER_HR, tipData.totalPerHr);
        startActivityForResult(detailActivityIntent, REQUEST_DETAILS);
    }


}

