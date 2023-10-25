package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class ChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private DatabaseHelper dbHelper;
    private String sensorType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // Retrieve the selected sensor type from the intent
        Intent intent = getIntent();
        sensorType = intent.getStringExtra("SENSOR_TYPE");

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize chart
        lineChart = findViewById(R.id.lineChart);
        setupChartProperties();
        loadChartData();
    }

    private void setupChartProperties() {
        // Set up chart properties
        // ...
    }

    private void loadChartData() {
        // Load sensor data for the selected sensor type from the database
        ArrayList<Entry> sensorEntries = new ArrayList<>();
        // Fetch sensor data from the database based on sensorType and add entries to sensorEntries
        // ...

        LineDataSet sensorDataSet = new LineDataSet(sensorEntries, sensorType + " Sensor");
        LineData sensorData = new LineData(sensorDataSet);
        lineChart.setData(sensorData);
        lineChart.invalidate(); // Refresh chart
    }
}
