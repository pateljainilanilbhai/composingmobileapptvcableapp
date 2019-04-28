package com.mad.threaddemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends IntentService {
	boolean keepCounting = false;
	int count = 0;
	public boolean mServiceCreated, mServiceStarted, mServiceBound,
			onStartCommand;
	public int mServiceCreationCount;

	public CounterService() {
		super("CounterService");
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mServiceCreated = true;
		mServiceCreationCount++;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		mServiceBound = true;
		return super.onBind(intent);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		mServiceStarted = true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		mServiceBound = false;
		return super.onUnbind(intent);

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
		mServiceStarted = false;
		mServiceCreated = false;

		Log.i("Service Lifecycle", "onDestroy called");
		count = 0;
		keepCounting = false;

	}

}
