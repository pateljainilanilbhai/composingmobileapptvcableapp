package com.mad.brightnessmanager;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class BrightnessManagerActivity extends Activity implements OnCheckedChangeListener,
		SensorEventListener {

	private CheckBox enableCB;
	private SensorManager sensorManager;
	private Sensor proximitySensor;
	private TextView infoTV;

	public enum BRIGHTNESS_LEVEL {
		LOW, MEDIUM, HIGH;
	}

	public BRIGHTNESS_LEVEL currentBrightness = BRIGHTNESS_LEVEL.LOW;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		enableCB = (CheckBox) findViewById(R.id.checkBox1);
		enableCB.setOnCheckedChangeListener(this);
		
		infoTV= (TextView) findViewById(R.id.textView2);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (enableCB.isChecked()) {
			initiateBrightnessManager();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		terminateBrightnessManager();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			log("Initiating Brightness Manager");
			initiateBrightnessManager();
			infoTV.setVisibility(View.VISIBLE);
		} else {
			terminateBrightnessManager();
			infoTV.setVisibility(View.GONE);
		}
	}

	private void terminateBrightnessManager() {
		if (sensorManager != null) {
			log("Deregistering proximity sensor listener.");
			sensorManager.unregisterListener(this);
		}
	}

	private void initiateBrightnessManager() {
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		if (proximitySensor != null) {
			sensorManager.registerListener(this, proximitySensor,
					SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	private static final String LOG_TAG = "BrightnessManager";

	public void log(String msg) {
		Log.i(LOG_TAG, msg);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float distance = event.values[0];
		log("Distance = " + distance);
		if (distance == 0) {
			int nextBrigtnessLevelNumber=(currentBrightness.ordinal()+1)%3;
			WindowManager.LayoutParams params = getWindow().getAttributes();
			params.screenBrightness = nextBrigtnessLevelNumber-1;
			getWindow().setAttributes(params);
			currentBrightness=BRIGHTNESS_LEVEL.values()[nextBrigtnessLevelNumber];
		}
	}
}
