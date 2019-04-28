package com.mad.threaddemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends IntentService {
	boolean keepCounting = false;
	int count = 0;

	public CounterService() {
		super("CounterService");

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		keepCounting = true;
		count = 0;
		while (keepCounting) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			Log.i("CounterStatus", "Time elapsed since service started : "
					+ count + " seconds");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("Service Lifecycle", "onDestroy called");
		count = 0;
		keepCounting = false;
	}

}
