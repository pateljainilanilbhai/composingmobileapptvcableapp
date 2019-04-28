package com.mad.threaddemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends Activity implements OnClickListener {
	Button startCounter, stopCounter, reset;
	TextView displayValue;
	Handler handler;
	int count = 0;
	boolean keepCounting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter);

		startCounter = (Button) findViewById(R.id.startCounterButton);
		stopCounter = (Button) findViewById(R.id.stopCounterButton);
		reset = (Button) findViewById(R.id.resetButton);
		displayValue = (TextView) findViewById(R.id.displayValue);
		displayValue.setText(count + "");
		startCounter.setOnClickListener(this);
		stopCounter.setOnClickListener(this);
		reset.setOnClickListener(this);
		/*
		 * handler = new Handler() {
		 * 
		 * @Override public void handleMessage(Message msg) { // TODO
		 * Auto-generated method stub super.handleMessage(msg);
		 * displayValue.setText((String) msg.obj); } };
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_counter, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.startCounterButton:
			keepCounting = true;
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (keepCounting) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						count++;
						displayValue.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								displayValue.setText(count + "");
							}
						});
					}
				}
			}).start();
			break;
		case R.id.stopCounterButton:
			keepCounting = false;
			break;
		case R.id.resetButton:
			count = 0;
			displayValue.setText(count + "");
			break;
		default:
			break;
		}
	}

}
