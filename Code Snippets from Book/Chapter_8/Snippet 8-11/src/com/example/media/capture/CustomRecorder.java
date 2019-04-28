package com.example.media.capture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.media.capture.R;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomRecorder extends Activity implements OnClickListener,
		OnCompletionListener {

	TextView statusTextView, amplitudeTextView;
	Button startRecording, stopRecording, finishButton;
	MediaRecorder recorder;

	File avFile;

	Camera c;
	boolean isRecording = false;
	SurfaceView surfaceView;
	private static String TAG = "CAMERA_DEMO";
	private CameraPreview cameraPreview;
	private Uri videoUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		c = getCameraInstance();
		if (c == null) {
			Log.d(TAG, "Error accessing camera!!!");
			Toast.makeText(this, "Error accessing camera!!!", 5000).show();
			finish();
		}

		cameraPreview = new CameraPreview(this, c);
		((FrameLayout) findViewById(R.id.camera_preview))
				.addView(cameraPreview);

		setUp();

	}

	private void setUp() {
		statusTextView = (TextView) this.findViewById(R.id.StatusTextView);
		statusTextView.setText("Ready");

		stopRecording = (Button) this.findViewById(R.id.StopRecording);
		startRecording = (Button) this.findViewById(R.id.StartRecording);

		startRecording.setOnClickListener(this);
		stopRecording.setOnClickListener(this);

		stopRecording.setEnabled(false);

	}

	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
		} catch (Exception e) {
			// Camera is not available (in use or does not exist)
			Log.e(TAG, "cAMERA eRROR!!!" + e);
		}
		return c; // returns null if camera is unavailable
	}

	public void onClick(View v) {
		if (v == stopRecording) {
			isRecording = false;

			recorder.stop();
			releaseMediaRecorder();
			c.lock();

			statusTextView.setText("File saved...");

			stopRecording.setEnabled(false);
			startRecording.setEnabled(true);

		} else if (v == startRecording) {

			if (prepareVideoRecorder()) {
				// Camera is available and unlocked, MediaRecorder is prepared,
				// now you can start recording
				recorder.start();

				// inform the user that recording has started
				statusTextView.setText("Recording...");
				isRecording = true;

				stopRecording.setEnabled(true);
				startRecording.setEnabled(false);

			} else {
				// prepare didn't work, release the camera
				releaseMediaRecorder();
				statusTextView.setText("Error while trying to record");
			}

		}
	}

	private Uri getVideoUri() {

		// To be safe, you should check that the SDCard is mounted

		if (!Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {

			return null;

		}
		// Create a folder for your app within the shared location.
		// Media won't get deleted when the app is uninstalled

		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				TAG);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ "VID_" + timeStamp + ".mp4");
		;

		return Uri.fromFile(mediaFile);

	}

	public void onCompletion(MediaPlayer mp) {

		stopRecording.setEnabled(false);
		startRecording.setEnabled(true);
		statusTextView.setText("Ready");
	}

	boolean prepareVideoRecorder() {
		recorder = new MediaRecorder();

		// Step 1: Unlock and set camera to MediaRecorder
		c.unlock();
		recorder.setCamera(c);

		// Step 2: Set sources
		recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

		// Step 3: Check for CamcorderProfile and use it, if available
		useCamcorderIfAvailable();

		// Step 4: Set output file
		videoUri = getVideoUri();

		recorder.setOutputFile(videoUri.getPath());
		// Step 5: Set the preview output
		recorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

		// Step 6: Prepare configured MediaRecorder
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			Log.e(TAG,
					"IllegalStateException preparing MediaRecorder: "
							+ e.getMessage());
			releaseMediaRecorder();
			return false;
		} catch (IOException e) {
			Log.e(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
			releaseMediaRecorder();
			return false;
		}

		return true;
	}

	private void useCamcorderIfAvailable() {

		if (android.os.Build.VERSION.SDK_INT > 8) {
			// Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
			recorder.setProfile(CamcorderProfile
					.get(CamcorderProfile.QUALITY_HIGH));
		} else {

			// Step 3: Set output format and encoding (for versions prior to API
			// Level 8)
			recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
		}

	}

	private void releaseMediaRecorder() {
		if (recorder != null) {
			recorder.reset(); // clear recorder configuration
			recorder.release(); // release the recorder object
			recorder = null;
			c.lock(); // lock camera for later use
		}
	}

	private void releaseCamera() {
		if (c != null) {
			c.release(); // release the camera for other applications
			c = null;
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		releaseMediaRecorder(); // if you are using MediaRecorder, release it first
		releaseCamera(); // release the camera immediately on pause event
	}
}