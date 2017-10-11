package com.ohara.oharaedward_1703.breakdownActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ohara.oharaedward_1703.R;

//Edward O'Hara
//MDV469 - 1703
//BreakdownActivity
public class BreakdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakdown);

        BreakdownFragment breakdownFragment = BreakdownFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.breakdownContainer, breakdownFragment,
                BreakdownFragment.BREAKDOWN_TAG).commit();

    }
}
