package com.mad.demo.telephoneymanagerdemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText phoneNumberEditText;
	private Button makeCallButton, networkTypeButton, phoneTypeButton;
	private BroadcastReceiver phoneStateChangeReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		phoneNumberEditText=(EditText)findViewById(R.id.editText1);

		makeCallButton=(Button)findViewById(R.id.makeCallbutton1);
		networkTypeButton=(Button)findViewById(R.id.newtworkTypebutton1);
		phoneTypeButton=(Button)findViewById(R.id.phoneTypebutton2);

		phoneStateChangeReceiver =new PhoneStateChangeReceiver();
		makeCallButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phoneNumber=phoneNumberEditText.getText().toString();

				if(phoneNumber.equals("") || phoneNumber.length()==0)
				{
					Toast.makeText(MainActivity.this, "Please enter the phone number", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Intent makeCallIntent=new Intent(Intent.ACTION_CALL);
					makeCallIntent.setData(Uri.parse("tel:"+phoneNumber));
					startActivity(makeCallIntent);
				}
			}
		});

		networkTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "Network Type: "+findNetworkType(), Toast.LENGTH_SHORT).show();
			}
		});

		phoneTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "Phone Type: "+findPhoneType(), Toast.LENGTH_SHORT).show();
			}
		});

	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(phoneStateChangeReceiver, new IntentFilter("android.intent.action.PHONE_STATE"));
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(phoneStateChangeReceiver);
	}


	private String findNetworkType()
	{
		String newtworkType;

		TelephonyManager telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_CDMA:newtworkType="CDMA";break;
		case TelephonyManager.NETWORK_TYPE_EDGE:newtworkType="EDGE";break;
		case TelephonyManager.NETWORK_TYPE_EHRPD: newtworkType="EHRPD"; break;
		case TelephonyManager.NETWORK_TYPE_HSDPA: newtworkType="HSDPA";break;
		case TelephonyManager.NETWORK_TYPE_HSPA: newtworkType="HSPA"; break;
		case TelephonyManager.NETWORK_TYPE_HSPAP: newtworkType="HSPAP";break;
		case TelephonyManager.NETWORK_TYPE_HSUPA: newtworkType="HSUPA";break;
		case TelephonyManager.NETWORK_TYPE_LTE: newtworkType="LTE";break;
		case TelephonyManager.NETWORK_TYPE_UMTS: newtworkType="UMTS";break;
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:newtworkType="UNKNOWN"; break;
		default: newtworkType="Unknown";
		break;
		}

		return newtworkType;
	}

	private String findPhoneType()
	{
		String phoneType;
		
		TelephonyManager  telephonyManager=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getPhoneType()) {
		case TelephonyManager.PHONE_TYPE_CDMA: phoneType="CDMA";break;
		case TelephonyManager.PHONE_TYPE_GSM: phoneType="GSM";break;
		case TelephonyManager.PHONE_TYPE_SIP:phoneType="SIP";break;
		case TelephonyManager.PHONE_TYPE_NONE: phoneType="NONE";break;
		default: phoneType="Unknown";
		break;
		}

		return phoneType;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
