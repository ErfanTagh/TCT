package com.services.tct;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by erfan on 9/15/2016.
 */

public class GraphTestClass extends Activity{


    LineGraphSeries<DataPoint> series;


    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_test_layout);

        double y,x;
        x=-5.0;

        GraphView graphView=(GraphView) findViewById(R.id.graph);
        series=new LineGraphSeries<DataPoint>();

        for (int i=0;i<500;i++)
        {
            x=x+0.1;
            y=Math.sin(x);
            series.appendData(new DataPoint(x,y),true,500);
        }

        graphView.addSeries(series);






    }
}
