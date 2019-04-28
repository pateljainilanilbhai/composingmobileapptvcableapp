package com.mad.threaddemo;

import android.R.integer;
import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		TextView textView = (TextView) findViewById(R.id.displayCount);
		int count = getIntent().getIntExtra("Count", 0);
		textView.setText(count + "");
		int notificationid=getIntent().getIntExtra("notificationid", 0);
		NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(notificationid);
	}
}
