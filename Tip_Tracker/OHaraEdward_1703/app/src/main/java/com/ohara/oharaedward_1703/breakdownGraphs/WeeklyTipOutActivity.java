package com.ohara.oharaedward_1703.breakdownGraphs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ohara.oharaedward_1703.R;

//Edward O'Hara
//MDV469 - 1703
//WeeklyTipOutActivity
public class WeeklyTipOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_tip_out);

        GraphView graphView = (GraphView) findViewById(R.id.wkTipOutGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 100),
                new DataPoint(2, 150),
                new DataPoint(3, 200),
                new DataPoint(4, 250),
                new DataPoint(5, 300)
        });

        graphView.addSeries(series);
    }
}
