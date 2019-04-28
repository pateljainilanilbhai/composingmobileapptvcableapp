package com.mad.threaddemo;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

public class CounterService extends IntentService {
	boolean keepCounting = false;
	int count = 0;

	public CounterService() {
		super("CounterService");

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		keepCounting = true;
		count = 0;
		while (keepCounting) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
			Log.i("CounterStatus", "Time elapsed since service started : "
					+ count + " seconds");
		}
	}

	@Override
	public void onDestroy() {
		Log.i("Service Lifecycle", "onDestroy called");
		triggerNotification(false);								//pass true to see the Big notification
		count = 0;
		keepCounting = false;
		super.onDestroy();
	}

	@SuppressLint("NewApi")
	private void triggerNotification(boolean big) {
		NotificationCompat.Builder builder = new Builder(this);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
				R.drawable.servicestopped));
		builder.setSmallIcon(R.drawable.ic_small_servicestop);
		builder.setContentTitle("CounterService stopped");
		builder.setContentText("Counter stopped incrementing");
		Intent intent = new Intent(this, ResultActivity.class);
		intent.putExtra("Count", count);
		intent.putExtra("notificationid", 1);
		 PendingIntent pendingIntent=PendingIntent.getActivity(this, 0,
		 intent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		if (big) {
			String[] events = new String[6];
			events[0] = "Type: Intent Service";
			events[1] = "Name: CounterService";
			events[2] = "Function: Integer Counter";
			events[3] = "Frequency: One second";
			events[4] = "Start Value: 0";
			events[5] = "Stop Value: " + count;
			NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
			inboxStyle.setBigContentTitle("CounterService stopped");
			inboxStyle.setSummaryText("Counter stopped incrementing");
			for (int i = 0; i < events.length; i++) {
				inboxStyle.addLine(events[i]);
			}
			builder.setStyle(inboxStyle);
		}
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(1, builder.build());
	}
	/* This is Snippet 5-18 from the book. */
	/*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	stackBuilder.addParentStack(ResultActivity.class);
	stackBuilder.addNextIntent(intent);
	PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
			PendingIntent.FLAG_UPDATE_CURRENT);*/
}
