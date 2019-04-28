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
	public void recordAudio(View v){

	Intent intent = new Intent( MediaStore.Audio.Media.RECORD_SOUND_ACTION);
	startActivityForResult(intent, REQUEST_CODE);

	}
	
	public void recordVideo(View v){
			Intent intent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);	
			startActivityForResult(intent,REQUEST_CODE);

	}
	public void takePicture(View v){
			Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE); 
					String filepath= Environment.getExternalStorageDirectory()+ "/mypicture.jpg";
					File myImage= new File(filepath);
					Uri imageUri = Uri.fromFile(myImage);
					intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
					startActivityForResult(intent, REQUEST_CODE);
	
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE){
			if(resultCode==Activity.RESULT_OK){
				//Get the details of capture here..
				
			}
			
		}
	}
}
