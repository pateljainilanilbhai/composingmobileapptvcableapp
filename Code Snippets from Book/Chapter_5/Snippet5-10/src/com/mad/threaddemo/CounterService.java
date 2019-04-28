package com.mad.threaddemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class CounterService extends Service {
	boolean keepCounting = false;
	int count = 0;
	public static final int GET_COUNT=0; 
	
	class CounterServiceHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case GET_COUNT:
				Message sendCount=Message.obtain(null,GET_COUNT);
				sendCount.arg1=getCount();
				try {
					msg.replyTo.send(sendCount);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			super.handleMessage(msg);
		}
	}
	Messenger counterServiceMessenger = new Messenger(new CounterServiceHandler());
	
	@Override
	public IBinder onBind(Intent intent) {
		startCount();
		return counterServiceMessenger.getBinder();
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
