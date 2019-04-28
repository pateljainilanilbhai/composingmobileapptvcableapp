package com.mad.demo.smsmanagerdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText smsEditText;
	private Button sendSmsButton;
	private BroadcastReceiver smsReader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		smsEditText=(EditText)findViewById(R.id.smsEditText1);
		sendSmsButton=(Button)findViewById(R.id.sendSmsButton1);
		smsReader=new IncomingSmsReader();
		sendSmsButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String message=smsEditText.getText().toString();
				if(message.length()==0 || message.equals(""))
				{
					Toast.makeText(MainActivity.this, "Please enter the text", Toast.LENGTH_SHORT).show();
				}
				else
				{
					SmsManager manager=SmsManager.getDefault();
					manager.sendTextMessage("5556", null, message, null, null);
				}
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(smsReader, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(smsReader);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
