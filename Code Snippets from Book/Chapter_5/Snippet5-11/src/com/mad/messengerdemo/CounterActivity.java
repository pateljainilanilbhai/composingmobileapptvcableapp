package com.mad.messengerdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
	int countValue=0;
	TextView displayCountValue;
	public static final int GET_COUNT=0;
	boolean bound = false;
	Messenger requestCountMessenger, recieveCountMessenger;
	
	class RecieveCountHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			countValue=0;
			switch (msg.what) {
			case GET_COUNT:
				countValue=msg.arg1;
				displayCountValue.setText(countValue+ "");
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	}
	ServiceConnection CounterServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			requestCountMessenger=null;
			recieveCountMessenger=null;
			bound = false;
		}
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			requestCountMessenger=new Messenger(arg1);
			recieveCountMessenger=new Messenger(new RecieveCountHandler());
			bound=true;
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
		intent = new Intent("com.mad.counterservice");
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.startCounterButton:
			bindService(intent, CounterServiceConnection, BIND_AUTO_CREATE);
			break;
		case R.id.stopCounterButton:
			unbindService(CounterServiceConnection);
			break;
		case R.id.fetchCount:
			if (bound == true) {
				Message requestMessage=Message.obtain(null, GET_COUNT);
				requestMessage.replyTo=recieveCountMessenger;
				try {
					requestCountMessenger.send(requestMessage);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
}
