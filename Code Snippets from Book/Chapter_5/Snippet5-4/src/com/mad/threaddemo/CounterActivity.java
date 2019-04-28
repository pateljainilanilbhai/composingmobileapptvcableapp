package com.mad.threaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity implements OnClickListener {
	Button startCounter, stopCounter;
	Intent intent=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);
		startCounter = (Button) findViewById(R.id.startCounterButton);
		stopCounter = (Button) findViewById(R.id.stopCounterButton);
		startCounter.setOnClickListener(this);
		stopCounter.setOnClickListener(this);
		intent= new Intent(this, CounterService.class);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_counter, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.startCounterButton:
			startService(intent);
			break;
		case R.id.stopCounterButton:
			stopService(intent);
			break;
		default:
			break;
		}
	}
}
