package com.ohara.oharaedward_1703.addTipActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import com.ohara.oharaedward_1703.R;

/*
class to display dialog frag with date picker as view
referenced from: https://developer.android.com/guide/topics/ui/controls/pickers.html#DatePickerFragment
*/

//Edward O'Hara
//MDV469 - 1703
//TipDatePicker
public class TipDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    //TAG
    private static final String TAG = "TipDatePicker";

    //create dialog w/ instance of calandar
    @RequiresApi(api = Build.VERSION_CODES.N)//calender .get() & .getInstance() methods require different api then targeted
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get calendar info
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //create new instance of DatePicker dialog and return
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    //require method for OnDataSetListener, is called when user selected a date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //dev
        Log.i(TAG, "onDateSet: " + month);
        Log.i(TAG, "onDateSet: " + day);
        Log.i(TAG, "onDateSet: " + year);

        //string w/ selected date formatted
        final String dateSelected = month + 1 + "/" + day + "/" + year;
        //init textView to display date
        TextView selectedDateTV = (TextView) getActivity().findViewById(R.id.selectedDateTextView);
        //set text of w/ selected date
        selectedDateTV.setText(dateSelected);
    }
}
