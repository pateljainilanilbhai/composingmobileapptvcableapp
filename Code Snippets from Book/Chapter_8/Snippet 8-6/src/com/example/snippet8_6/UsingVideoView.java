package com.example.snippet8_6;

import com.infy.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class UsingVideoView extends Activity {
	VideoView vv;
	MediaController mc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		vv = (VideoView) this.findViewById(R.id.videoView1);
		Uri videoUri = Uri.parse("http://www.mobmusicstore.com/video.mp4");
		vv.setVideoURI(videoUri);
		vv.setKeepScreenOn(true);

		mc = new MediaController(this);
		vv.setMediaController(mc);

		vv.start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		mc.show();
		return true;
	}

}