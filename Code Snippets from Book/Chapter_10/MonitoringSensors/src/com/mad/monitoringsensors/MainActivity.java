package com.mad.monitoringsensors;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener,
		OnClickListener {

	private SensorManager sensorManager;
	private Sensor accl;
	private TextView xValTV, yValTV, zValTV;
	private boolean monitoring = false;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Assuming we have already confirmed that accelerometer is available
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accl = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		xValTV = (TextView) findViewById(R.id.xVal);
		yValTV = (TextView) findViewById(R.id.yVal);
		zValTV = (TextView) findViewById(R.id.zVal);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}

	@Override
	public final void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Do something on sensor accuracy change.
	}

	@Override
	public final void onSensorChanged(SensorEvent event) {
		if (monitoring) {
			float[] values = event.values;
			// Movement
			float acclX = values[0];
			float acclY = values[1];
			float acclZ = values[2];

			xValTV.setText(String.valueOf(acclX));
			yValTV.setText(String.valueOf(acclY));
			zValTV.setText(String.valueOf(acclZ));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accl,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Its a best practice to unregister listener as soon as Activity
		// pauses,
		// to save battery.
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Toast.makeText(this, "Displaying Accelerometer data..", Toast.LENGTH_SHORT).show();
		monitoring = true;
	}

}
