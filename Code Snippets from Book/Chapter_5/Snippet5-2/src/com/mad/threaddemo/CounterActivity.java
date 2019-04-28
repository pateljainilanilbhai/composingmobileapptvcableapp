package com.mad.threaddemo;

import android.os.AsyncTask;
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
			keepCounting = true;
			new CounterAsyncTask().execute(count);
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

	class CounterAsyncTask extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected Integer doInBackground(Integer... arg0) {
			count = arg0[0];
			while (keepCounting) {
				try {
					Thread.sleep(1000);
					count++;
					publishProgress(count);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return count;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			displayValue.setText(values[0] + "");
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			displayValue.setText(result + "");
		}

	}

}
