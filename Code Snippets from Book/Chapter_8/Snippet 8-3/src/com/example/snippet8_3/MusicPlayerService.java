package com.example.snippet8_3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

public class MusicPlayerService extends Service implements OnCompletionListener {

	private WifiLock wifiLock;
	private MediaPlayer mediaPlayer;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		mediaPlayer = MediaPlayer.create(this,
				Uri.parse("http://www.mobmusicstore.com/music.mp3"));

		if (mediaPlayer == null) {
			Toast.makeText(this, "Unable to create Media player", 4000).show();
		} else {

			Toast.makeText(this, "Playing song", Toast.LENGTH_LONG).show();
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
			wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
					.createWifiLock(WifiManager.WIFI_MODE_FULL, "wifilock");
			wifiLock.acquire();
			mediaPlayer.start();
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		stopSelf();
		wifiLock.release();
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}

	}
}
