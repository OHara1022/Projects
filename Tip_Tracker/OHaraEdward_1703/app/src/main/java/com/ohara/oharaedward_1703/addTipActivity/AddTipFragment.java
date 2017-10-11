package com.ohara.oharaedward_1703.addTipActivity;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.dataModel.TipData;
import com.ohara.oharaedward_1703.formHelpers.FieldsCheck;


//Edward O'Hara
//MDV469 - 1703
//AddTipFragment
public class AddTipFragment extends Fragment {

    //TAG
    private static final String TAG = "AddTipFragment";
    public static final String ADD_TIP_TAG = " ADD_TIP_TAG";

    //text field for date
    private TextView mdate;
    //edit text fields
    private EditText mHoursWorked;
    private EditText mTotalSales;
    private EditText mTipOutPercent;
    private EditText mCashTips;
    private EditText mCreditCardTips;
    private EditText mNote;
    //button to display date picker
    private Button mSelectDate;
    //interface listener to pass tip data to add tip activity
    GetTipData mListener;

    //new instance constructor
    public static AddTipFragment newInstance() {
        //instance of frag
        AddTipFragment addTipFrag = new AddTipFragment();
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
        if (context instanceof GetTipData) {
            //attach listener
            mListener = (GetTipData) context;
        } else {
            throw new IllegalArgumentException("Please add GetTipData interface to add tip frag");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set has options menu to true
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //custom view for add tip fragment
        View addTipView = inflater.inflate(R.layout.add_tip_fragment, container, false);

        //init UI
        mdate = (TextView) addTipView.findViewById(R.id.selectedDateTextView);
        mHoursWorked = (EditText) addTipView.findViewById(R.id.hoursWorkedET);
        mTotalSales = (EditText) addTipView.findViewById(R.id.totalSalesET);
        mTipOutPercent = (EditText) addTipView.findViewById(R.id.tipOutPercentET);
        mCashTips = (EditText) addTipView.findViewById(R.id.cashTipsET);
        mCreditCardTips = (EditText) addTipView.findViewById(R.id.creditTipsET);
        mNote = (EditText) addTipView.findViewById(R.id.noteET);
        mSelectDate = (Button) addTipView.findViewById(R.id.selectDateButton);

        //set onclick to display date picker dialog
        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                //dev
                Log.i(TAG, "onClick: DATE BUTTON");

                //call method invoke date picker dialog
                selectedDate(mSelectDate);
            }
        });

        //return create view
        return addTipView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //set add menu item
        inflater.inflate(R.menu.add_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addTipData:

                //check no required fields are empty
                if (mdate.getText().equals("") || FieldsCheck.isEmpty(mHoursWorked) || FieldsCheck.isEmpty(mTotalSales)
                        || FieldsCheck.isEmpty(mTipOutPercent) || FieldsCheck.isEmpty(mCashTips)
                        || FieldsCheck.isEmpty(mCreditCardTips)) {

                    //alert user no empty field
                    Toast.makeText(getActivity(), "No Empty Fields", Toast.LENGTH_SHORT).show();
                    return false;
                }

                //populate object
                TipData newTipData = new TipData(mdate.getText().toString().trim(),
                        Long.parseLong(mTotalSales.getText().toString().trim()),
                        Long.parseLong(mTipOutPercent.getText().toString().trim()),
                        Long.parseLong(mCashTips.getText().toString().trim()),
                        Long.parseLong(mCreditCardTips.getText().toString().trim()),
                        Long.parseLong(mHoursWorked.getText().toString().trim()));

                //check if note was entered
                if (mNote != null) {
                    //get note
                    newTipData.note = mNote.getText().toString().trim();
                    //dev
                    Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.note);
                }

                //dev
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.selectedDate);
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.hoursWorked);
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.totalSales);
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.tipOutPercent);
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.cashTips);
                Log.i(TAG, "onOptionsItemSelected: " + " " + newTipData.creditCardTips);

                //pass data to add tip activity
                mListener.newTipData(newTipData);
                return true;
        }
        return false;
    }

    /*
    method to display date picker dialog frag
    referenced from:
     https://developer.android.com/guide/topics/ui/controls/pickers.html#ShowingTheDatePicker
     */
    public void selectedDate(View view) {
        //instance of dialog frag
        DialogFragment dateFrag = new TipDatePicker();
        //show date picker
        dateFrag.show(getFragmentManager(), "TipDatePicker");
    }
}