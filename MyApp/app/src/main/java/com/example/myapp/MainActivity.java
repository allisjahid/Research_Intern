package com.example.myapp;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor lightSensor, proximitySensor, accelerometerSensor, gyroscopeSensor;
    private TextView lightValue, proximityValue, accelerometerValue, gyroscopeValue;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Handler handler;
    private CardView lightCard, proximityCard, accelerometerCard, gyroscopeCard;

    static {
        System.loadLibrary("sensornative");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, SensorService.class));

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        initializeSensors();

        // Schedule periodic database insertions every 5 minutes
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                float lightValue = 40 ;
                float proximityValue =  100 ;
                float accelerometerValue = 50 ;
                float gyroscopeValue =  60 ;

                insertSensorData(lightValue, proximityValue, accelerometerValue, gyroscopeValue);
                handler.postDelayed(this, 5 * 60 * 1000); // 5 minutes
            }

            private void insertSensorData(float lightValue, float proximityValue, float accelerometerValue, float gyroscopeValue) {
            }
        }, 5 * 60 * 1000); // 5 minutes
    }

        private void insertSensorData(String sensorType, float sensorValue) {
        // Insert sensor data into the database
        ContentValues values = new ContentValues();


        values.put(DatabaseHelper.COLUMN_SENSOR_TYPE, "Light");
        values.put(DatabaseHelper.COLUMN_SENSOR_VALUE, String.valueOf(lightValue));
        database.insert(DatabaseHelper.TABLE_SENSOR_DATA, null, values);

        // Insert Proximity Sensor Data
        values.clear();
        values.put(DatabaseHelper.COLUMN_SENSOR_TYPE, "Proximity");
        values.put(DatabaseHelper.COLUMN_SENSOR_VALUE, String.valueOf(proximityValue));
        database.insert(DatabaseHelper.TABLE_SENSOR_DATA, null, values);

        // Insert Accelerometer Sensor Data
        values.clear();
        values.put(DatabaseHelper.COLUMN_SENSOR_TYPE, "Accelerometer");
        values.put(DatabaseHelper.COLUMN_SENSOR_VALUE, String.valueOf(accelerometerValue));
        database.insert(DatabaseHelper.TABLE_SENSOR_DATA, null, values);

        // Insert Gyroscope Sensor Data
        values.clear();
        values.put(DatabaseHelper.COLUMN_SENSOR_TYPE, "Gyroscope");
        values.put(DatabaseHelper.COLUMN_SENSOR_VALUE, String.valueOf(gyroscopeValue));
        database.insert(DatabaseHelper.TABLE_SENSOR_DATA, null, values);


    }
    private void initializeSensors() {
        // Initialize sensors
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // Initialize UI elements
        lightValue = findViewById(R.id.lightValue);
        proximityValue = findViewById(R.id.proximityValue);
        accelerometerValue = findViewById(R.id.accelerometerValue);
        gyroscopeValue = findViewById(R.id.gyroscopeValue);
    }


        @Override
    public void onSensorChanged(SensorEvent event) {
        // Update UI with sensor values
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightValue.setText("Light Sensor Value: " + event.values[0]);
            insertSensorData("Light", event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximityValue.setText("Proximity Sensor Value: " + event.values[0]);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValue.setText("Accelerometer Values: \nX: " + event.values[0] +
                    "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            gyroscopeValue.setText("Gyroscope Values: \nX: " + event.values[0] +
                    "\nY: " + event.values[1] + "\nZ: " + event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onDestroy() {
        super.onDestroy();


        stopSensors();
    }

    // Native methods declarations
    public native void startSensors();
    public native void stopSensors();


    public void handleSensorData(int sensorType, float value) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register sensor listeners
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners to save power when the app is in the background
        sensorManager.unregisterListener(this);
    }
}
