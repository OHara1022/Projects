package com.ohara.oharaedward_1703.breakdownGraphs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ohara.oharaedward_1703.R;


//Edward O'Hara
//MDV469 - 1703
//MonthlyTipActivity
public class MonthlyTipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_tip);

        GraphView graphView = (GraphView) findViewById(R.id.mthTipAverageGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 200),
                new DataPoint(2, 300),
                new DataPoint(3, 400),
                new DataPoint(4, 500),
                new DataPoint(5, 600)
        });
        graphView.addSeries(series);
    }
}
