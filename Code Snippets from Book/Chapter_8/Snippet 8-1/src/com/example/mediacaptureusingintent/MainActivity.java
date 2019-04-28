package com.example.mediacaptureusingintent;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	private static final int REQUEST_CODE = 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void playAudio(View v){

			Intent i = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse ("http://www.mobmusicstore.com/music.mp3");
			i.setDataAndType(uri,"audio/*"); 
			startActivity(i); 


	}
	public void playVideo(View v){
			Intent i = new Intent(Intent.ACTION_VIEW);
			Uri uri = Uri.parse("http://www.mobmusicstore.com/video.mp4");
			i.setDataAndType(uri,"video/*"); 
			startActivity(i);

	}



}
