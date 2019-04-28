package com.mad.demo.smsmanagerdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.telephony.SmsMessage;


import android.util.Log;

public class IncomingSmsReader extends BroadcastReceiver{

	private String smsMessageString;
	private boolean sameOriginatingadress;

	private SmsMessage [] smsMessages;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("com.mad.demo.smsmanagerdemo", "Messge received");
		Bundle bundle=intent.getExtras();
		if(bundle!=null)
		{
			Object[] pdus=(Object[])bundle.get("pdus");
			//If the message is small, i.e. single message
			if(pdus.length==1)
			{
				Log.i("com.mad.demo.smsmanagerdemo", "It's a short message");
				Log.i("com.mad.demo.smsmanagerdemo", "Messge body: "+SmsMessage.createFromPdu((byte[])pdus[0]).getMessageBody());
				Log.i("com.mad.demo.smsmanagerdemo", "Originating Number: "+SmsMessage.createFromPdu((byte[])pdus[0]).getOriginatingAddress());
			}
			//If the message is multi-part
			else
			{
				Log.i("com.mad.demo.smsmanagerdemo", "It's a long message");
				sameOriginatingadress=true;//Initialize the boolean variable to check all messages are from the same originatine address

				smsMessages=new SmsMessage[pdus.length];


				//Initialize a SmsMessage object from array of PDU object
				for(int i=0;i<pdus.length;i++)
				{
					smsMessages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);		
				}	
				String [] messagedOriginantineAddress= new String [smsMessages.length];//Create a array to hold the originating address of all SmsMessage object
				//Iterate to concatenate the message from the multiple SmsMessage object, and initialize originating address array
				for(int i=0;i<smsMessages.length;i++)
				{
					smsMessageString+=smsMessages[i].getMessageBody();
					messagedOriginantineAddress[i]=smsMessages[i].getOriginatingAddress();
				}
				String temp=messagedOriginantineAddress[0];

				// If one of the SmsMessage has different originating address then initilize the boolean cariable to false
				for(String string: messagedOriginantineAddress)
				{
					if(!string.equals(temp))
					{
						sameOriginatingadress=false;
					}
				}

				Log.i("com.mad.demo.smsmanagerdemo", "Message body: "+smsMessageString);
				//Display the originating address only if the boolean value is true
				if(sameOriginatingadress)
				{
					Log.i("com.mad.demo.smsmanagerdemo", "Originating Number: "+SmsMessage.createFromPdu((byte[])pdus[0]).getOriginatingAddress());
				}
			}
		}
	}
}
