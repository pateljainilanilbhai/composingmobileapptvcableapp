package com.mad.threaddemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CounterService extends Service {
	boolean keepCounting = false;
	int count = 0;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		startCount();
		return new CounterServiceBinder();
	}

	public class CounterServiceBinder extends Binder {
		public CounterService getService() {
			return CounterService.this;
		}
	}

	private void startCount() {
		keepCounting = true;
		count = 0;
		new Thread(new Runnable() {

			@Override
			public void run() {

				while (keepCounting) {
					count++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	private void stopCount() {
		keepCounting = false;
		count = 0;
	}

	public int getCount() {
		return count;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		stopCount();
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("Service Lifecycle", "onDestroy called");
	}

}
