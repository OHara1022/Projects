package com.ohara.oharaedward_1703.formHelpers;

import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


//Edward O'Hara
//MDV469 - 1703
//FieldsCheck
public class FieldsCheck {

    //check for empty edit text fields
    public static Boolean isEmpty(EditText editText) {

        if (editText.getText().toString().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    //check for valid password
    public static Boolean validPassword(String password, Context context) {

        //firebase password must be at least 6 characters
        if (password.length() < 5) {
            //alert user password is invalid
            Toast.makeText(context, "Password must be 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //check email is valid
    public static Boolean validEmail(CharSequence email, Context context) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        //alert user email is invalid
        Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show();
        return false;
    }

    //method to hide text view if data is available
    public static Boolean noData(Boolean hasData, TextView noData) {

        if (hasData) {
            //if data hide TV
            noData.setVisibility(View.GONE);

        } else {
            //if no data show TV
            noData.setVisibility(View.VISIBLE);
        }
        return false;
    }
}