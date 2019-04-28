package com.mad.ringsilencer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class Silencer extends BroadcastReceiver implements SensorEventListener {
	private SensorManager sm;
	private static final String Mode_Number = "RingSilencerPrefs";
	private static final String LOG_TAG = "RingSilencer";
	private AudioManager aman;
	private static int count = 1;
	private static float prevZValue;
	private static boolean phoneFlipped = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		log("Broadcast Invocation: " + count++);
		aman = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
			if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).toString()
					.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				int prevMode;
				SharedPreferences modeSettings = context.getSharedPreferences(
						Mode_Number, 0);
				Editor editor = modeSettings.edit();
				prevMode = aman.getRingerMode();
				editor.putInt("silentMode", prevMode);
				editor.commit();
				startListening();
				log("Call recieved");
			} 
			else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
					.toString().equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
				// Call answered
				sm.unregisterListener(this);
				log("Call answered");
			} 
			else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
					.toString().equals(TelephonyManager.EXTRA_STATE_IDLE)) {
				log("Phone idle");
				SharedPreferences modeSettings = context.getSharedPreferences(
						Mode_Number, 0);
				int prevMode = modeSettings.getInt("silentMode",
						AudioManager.RINGER_MODE_VIBRATE);

				aman.setRingerMode(prevMode);
				sm.unregisterListener(this);

				Editor editMode = modeSettings.edit();
				editMode.clear();
				editMode.commit();

				phoneFlipped = false;
			}
		}

	}

	public void log(String msg) {
		Log.i(LOG_TAG, msg);
	}

	public void startListening() {
		Sensor accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sm.registerListener(this, accelSensor,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		Sensor sensor = event.sensor;
		if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if (event.values[2] < 0 && prevZValue > 0) {
				log("prev z=" + prevZValue);
				log("current z=" + event.values[2]);
				phoneFlipped = true;
			}
		}
		prevZValue = event.values[2];

		if (phoneFlipped == true) {
			log("phone flipped");
			aman.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			sm.unregisterListener(this);
		}
	}
}
