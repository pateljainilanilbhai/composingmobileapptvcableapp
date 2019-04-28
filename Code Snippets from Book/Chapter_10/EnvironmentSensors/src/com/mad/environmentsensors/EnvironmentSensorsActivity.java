package com.mad.environmentsensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class EnvironmentSensorsActivity extends Activity implements
		SensorEventListener {

	private Sensor ambientTemperatureSensor;
	private Sensor lightSensor;
	private Sensor pressureSensor;
	private Sensor relativeHumiditySensor;
	private Sensor temperatureSensor;

	private SensorManager sensorManager;

	private TextView atTV;
	private TextView lightTV;
	private TextView pressureTV;
	private TextView rhTV;
	private TextView temperatureTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_environment_sensors);

		// Setting up UI references
		atTV = (TextView) findViewById(R.id.at);
		lightTV = (TextView) findViewById(R.id.light);
		pressureTV = (TextView) findViewById(R.id.pressure);
		rhTV = (TextView) findViewById(R.id.rh);
		temperatureTV = (TextView) findViewById(R.id.temperature);

		// Discover sensors
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		ambientTemperatureSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
		lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
		relativeHumiditySensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
		temperatureSensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.environment_sensors, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		monitorSensors(ambientTemperatureSensor, lightSensor, pressureSensor,
				relativeHumiditySensor, temperatureSensor);
	}

	@Override
	protected void onPause() {
		super.onPause();
		deregisterSensors(ambientTemperatureSensor, lightSensor,
				pressureSensor, relativeHumiditySensor, temperatureSensor);
	}

	private void monitorSensors(Sensor... sensors) {
		for (Sensor s : sensors) {
			if (s != null) {
				sensorManager.registerListener(this, s,
						SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
	}

	private void deregisterSensors(Sensor... sensors) {
		for (Sensor s : sensors) {
			if (s != null) {
				sensorManager.unregisterListener(this, s);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		int sensorType = event.sensor.getType();
		float sensorValue = event.values[0];
		switch (sensorType) {
		case Sensor.TYPE_AMBIENT_TEMPERATURE:
			atTV.setText(String.valueOf(sensorValue));
			break;
		case Sensor.TYPE_LIGHT:
			lightTV.setText(String.valueOf(sensorValue));
			break;
		case Sensor.TYPE_PRESSURE:
			pressureTV.setText(String.valueOf(sensorValue));
			break;
		case Sensor.TYPE_RELATIVE_HUMIDITY:
			rhTV.setText(String.valueOf(sensorValue));
			break;
		case Sensor.TYPE_TEMPERATURE:
			temperatureTV.setText(String.valueOf(sensorValue));
			break;
		}
	}
}
