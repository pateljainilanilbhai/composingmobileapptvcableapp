package com.mad.sharedpreference;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class SMSSender extends BroadcastReceiver {
	Context context;
	static boolean unAttended=true;
	static String incomingNumber="";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		context=arg0;
		if(arg1.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))
		{
			if(unAttended)
			{
				sendSMS();
			}
		}
		else if(arg1.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
		{
			incomingNumber=arg1.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		}
		else
		{
			unAttended=false;
		}
	}
	private void sendSMS() {
		SharedPreferences preferences=context.getSharedPreferences("SMSPreferences", context.MODE_PRIVATE);
		boolean sendSms=preferences.getBoolean("SendSMS", false);
		String message=preferences.getString("Message", "");
		String signature=preferences.getString("Signature", "");
		if(sendSms==true)
		{
			SmsManager.getDefault().sendTextMessage(incomingNumber+"", null, message+"\n"+signature, null, null);
		}
	}

}
