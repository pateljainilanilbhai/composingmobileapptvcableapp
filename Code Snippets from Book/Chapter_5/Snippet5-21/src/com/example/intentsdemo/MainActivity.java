package com.example.intentsdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Button sendBroadcastButton;
	private ToggleButton toggleBroadcastButton;
	BroadcastReceiver myBroadcastReceiver;
	private boolean toggleButtonStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sendBroadcastButton=(Button)findViewById(R.id.sendBroadcastbutton1);
		toggleBroadcastButton=(ToggleButton)findViewById(R.id.toggleBroadcastButton1);
		myBroadcastReceiver=new MyCustomBroadcastReceiver();

		toggleBroadcastButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				toggleButtonStatus=isChecked;

			}
		});

		sendBroadcastButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toggleButtonStatus)
				{
					//To send the broadcast intent 
					Intent intent=new Intent("com.exmaple.intentdemo.SIMPLE_BROADCAST");
					sendBroadcast(intent);

				}
				else 
				{
					Toast.makeText(MainActivity.this, "Swich on the button", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(myBroadcastReceiver, new IntentFilter("com.exmaple.intentdemo.SIMPLE_BROADCAST"));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(myBroadcastReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
