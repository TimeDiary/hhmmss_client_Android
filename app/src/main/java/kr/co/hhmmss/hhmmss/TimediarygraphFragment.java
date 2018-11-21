package kr.co.hhmmss.hhmmss;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;

import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * example of a heavily customized {@link LineChart} with limit lines, custom line shapes, etc.
 *
 * @version 3.1.0
 * @since 1.7.4
 */
public class TimediarygraphFragment extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_timediary_graph);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineChart = (LineChart) findViewById(R.id.chart);

        //사용자에게 별표 받아서 통계처리하는 방법을 사용해야함.
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 0));
        entries.add(new Entry(3, 1));
        entries.add(new Entry(4, 2));
        entries.add(new Entry(5, 3));


        LineDataSet dataset = new LineDataSet(entries, "평가");
        /**/

        /* label */
        //label 또한 사용자가 주,월별로 누르는 값에 따라 바뀌는 것으로 바꿔야함.
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        /* */

        dataset.setLineWidth(2);
        dataset.setCircleRadius(6);
        dataset.setCircleColor(Color.parseColor("#FFA1B4DC"));
        dataset.setCircleColorHole(Color.BLUE);
        dataset.setColor(Color.parseColor("#FFA1B4DC"));
        dataset.setDrawCircleHole(true);
        dataset.setDrawCircles(true);
        dataset.setDrawHorizontalHighlightIndicator(false);
        dataset.setDrawHighlightIndicators(false);
        dataset.setDrawValues(false);
        ArrayList<String> stj = null;

        lineDataSets.add(dataset);

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

//        Description description = new Description();
//        description.setText("");

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
//        lineChart.setDescription(String.valueOf(description));
        lineChart.animateY(2000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

//        MyMarkerView marker = new MyMarkerView(this,R.layout.markerviewtext);
//        marker.setChartView(lineChart);
//        lineChart.setMarker(marker);
    }
}
