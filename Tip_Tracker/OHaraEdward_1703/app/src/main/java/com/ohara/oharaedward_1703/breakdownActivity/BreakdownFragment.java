package com.ohara.oharaedward_1703.breakdownActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ohara.oharaedward_1703.R;
import com.ohara.oharaedward_1703.breakdownGraphs.MonthlyTipActivity;
import com.ohara.oharaedward_1703.breakdownGraphs.WeeklyTipActivity;
import com.ohara.oharaedward_1703.breakdownGraphs.WeeklyTipOutActivity;

//Edward O'Hara
//MDV469 - 1703
//BreakdownFragment
public class BreakdownFragment extends Fragment implements View.OnClickListener {

    //TAG
    private static final String TAG = "BreakdownFragment";
    public static final String BREAKDOWN_TAG = "BREAKDOWN_TAG";

    //new instance constructor
    public static BreakdownFragment newInstance() {
        BreakdownFragment breakdownFragment = new BreakdownFragment();
        Bundle args = new Bundle();
        breakdownFragment.setArguments(args);
        return breakdownFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.breakdown_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get view
        View view = getView();

        if (view != null) {
            //get and set text for breakdown
            TextView tv = (TextView) view.findViewById(R.id.wkTipOutTV);
            tv.setText("WK TIP OUT AVERAGE");

            tv = (TextView) view.findViewById(R.id.wkTipTV);
            tv.setText("WK TIPS AVERAGE");

            tv = (TextView) view.findViewById(R.id.mthTipTV);
            tv.setText("MONTHLY AVERAGE");

            //init buttons
            Button wkTipOutBtn = (Button) view.findViewById(R.id.wkTipOutButton);
            Button wkTipAvgBtn = (Button) view.findViewById(R.id.wkTipButton);
            Button mthTipAvgBtn = (Button) view.findViewById(R.id.mthTipButton);
            //set onClickListener
            wkTipOutBtn.setOnClickListener(this);
            wkTipAvgBtn.setOnClickListener(this);
            mthTipAvgBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.wkTipOutButton:

                //transition to graph
                Intent wkGraph = new Intent(getActivity(), WeeklyTipOutActivity.class);
                startActivity(wkGraph);
                break;

            case R.id.wkTipButton:
                //transition to graph
                Intent wkTipGraph = new Intent(getActivity(), WeeklyTipActivity.class);
                startActivity(wkTipGraph);

                break;

            case R.id.mthTipButton:
                //transition to graph
                Intent mthTipGraph = new Intent(getActivity(), MonthlyTipActivity.class);
                startActivity(mthTipGraph);
                break;
        }
    }
}
