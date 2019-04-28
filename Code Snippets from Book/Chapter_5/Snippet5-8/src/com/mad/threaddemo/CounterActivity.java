package com.mad.threaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity implements OnClickListener {
	Button startCounter, stopCounter, refresh;
	Intent intent = null;
	TextView displayCountValue;
	boolean bound = false;
	CounterService counterService = null;

	ServiceConnection counterServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			bound = false;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			CounterService.CounterServiceBinder counterServiceBinder = (CounterService.CounterServiceBinder) arg1;
			counterService = counterServiceBinder.getService();
			bound = true;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		startCounter = (Button) findViewById(R.id.startCounterButton);
		stopCounter = (Button) findViewById(R.id.stopCounterButton);
		displayCountValue = (TextView) findViewById(R.id.displayCountValue);
		refresh = (Button) findViewById(R.id.fetchCount);
		startCounter.setOnClickListener(this);
		stopCounter.setOnClickListener(this);
		refresh.setOnClickListener(this);
		intent = new Intent(this, CounterService.class);
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.startCounterButton:
			bindService(intent, counterServiceConnection, BIND_AUTO_CREATE);
			break;
		case R.id.stopCounterButton:
			unbindService(counterServiceConnection);
			break;
		case R.id.fetchCount:
			if (bound == true) {
				displayCountValue.setText(counterService.getCount() + "");
			}
			break;
		default:
			break;
		}
	}

}
