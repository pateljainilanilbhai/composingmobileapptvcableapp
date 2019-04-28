package com.mad.demo.telephoneymanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;


public class PhoneStateChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("com.mad.demo.telephoneymanagerdemo", "PhoneStateChangeReceiver triggered");
		// TODO Auto-generated method stub
		String triggeredState=intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		if(triggeredState.equals(TelephonyManager.EXTRA_STATE_IDLE))
		{
			Log.i("com.mad.demo.telephoneymanagerdemo", "Phone is in idle state");
		}

		else if(triggeredState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
		{
			Log.i("com.mad.demo.telephoneymanagerdemo", "Phone is off the hook");
		}
		else if (triggeredState.equals(TelephonyManager.EXTRA_STATE_RINGING))
		{
			Log.i("com.mad.demo.telephoneymanagerdemo", "Phone is ringing");
		}

	}

}
