package com.mad.recievesms;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Bundle bundle=arg1.getExtras();
		Object[]pdus = (Object[]) bundle.get("pdus");
		SmsMessage message=SmsMessage.createFromPdu((byte[])pdus[0]);
		Log.i("com.mad.smsreceived",message.getMessageBody());
		Log.i("com.mad.smsreceived",message.getOriginatingAddress());
	}
}
