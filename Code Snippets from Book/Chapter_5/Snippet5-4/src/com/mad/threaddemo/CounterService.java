package com.mad.threaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {
	boolean keepCounting = false;
	int count = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("Service Lifecycle", "onCreate called");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("Service Lifecycle", "onStartCommand called");
		keepCounting = true;
		count = 0;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (keepCounting) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;
					Log.i("CounterStatus",
							"Time elapsed since service started : " + count
									+ " seconds");
				}
			}
		}).start();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("Service Lifecycle", "onDestroy called");
		count = 0;
		keepCounting = false;
		
	}
}
