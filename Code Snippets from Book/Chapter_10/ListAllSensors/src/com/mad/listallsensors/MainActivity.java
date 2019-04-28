package com.mad.listallsensors;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String LOG_TAG = "list_all_sensors";
	private Button button;
	private SensorManager sensorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(this);
	}	

	@Override
	public void onClick(View arg0) {
		Toast.makeText(this, "Discovering device sensors..", Toast.LENGTH_SHORT).show();
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
		for(Sensor sensor : sensorList){
			Log.i(LOG_TAG, "Sensor Name: "+sensor.getName()+"| Sensor Type: "+sensor.getType());
		}
		Toast.makeText(this, "Finished searching. Check logs for details.", Toast.LENGTH_LONG).show();
	}

}
