package com.mad.locateme;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mad.locateme.R;
import com.mad.locateme.MainActivity.ErrorDialogFragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.R.fraction;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;

import android.content.IntentSender;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

@SuppressLint("NewApi")
public class LocateMeMap extends Activity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	GoogleMap map;

	LatLng coordinates;
	MapFragment myMapFragment;
	private LocationClient mClient;
	Location currentLocation;
	public static final int ACTIVITY_REQUEST_CODE = 5555;
	public static final int GET_DIRECTIONS = 0;
	public static final int ADD_MARKER = 1;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withmap);
		mClient = new LocationClient(this, this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		map.clear();
		return super.onOptionsItemSelected(item);
	}

	private void initializeMap() {
		coordinates = new LatLng(currentLocation.getLatitude(),
				currentLocation.getLongitude());
		myMapFragment = (MapFragment) getFragmentManager().findFragmentById(
				R.id.mapLayout);
		map = myMapFragment.getMap();
		if (map != null) {
			map.setMyLocationEnabled(true);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					coordinates.latitude, coordinates.longitude), 13));
			activateLongClickOnMap();
		}
	}

	private void activateLongClickOnMap() {
		map.setOnMapLongClickListener(new OnMapLongClickListener() {
			@Override
			public void onMapLongClick(final LatLng arg0) {
				View dialogLayout = getLayoutInflater().inflate(
						R.layout.dialog_options, null);
				AlertDialog.Builder builder = new Builder(LocateMeMap.this);
				builder.setView(dialogLayout);
				Button addMarker = (Button) dialogLayout
						.findViewById(R.id.addMarker);
				Button getDirections = (Button) dialogLayout
						.findViewById(R.id.getDirections);
				addMarker.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MarkerOptions markerOptions = new MarkerOptions();
						markerOptions.position(arg0);
						markerOptions.draggable(false);
						markerOptions.title("Custom location");
						markerOptions.snippet("Latitude" + arg0.latitude
								+ "Longitude" + arg0.longitude);
						map.addMarker(markerOptions);
						alertDialog.dismiss();
					}
				});
				getDirections.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new GetDirectionsTask().execute(new String[] {
								arg0.latitude + "", arg0.longitude + "",
								currentLocation.getLatitude() + "",
								currentLocation.getLongitude() + "" });
						alertDialog.dismiss();
					}
				});
				alertDialog = builder.create();
				alertDialog.show();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		mClient.connect();

	}

	@Override
	public void onStop() {
	  super.onStop();		
 	  mClient.disconnect();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// Google play can resolve some errors. Use hasResolution() to find out

		if (arg0.hasResolution()) {
			try {
				// Use startResolutionForResult() to start the Activity
				arg0.startResolutionForResult(this, ACTIVITY_REQUEST_CODE);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {

			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					arg0.getErrorCode(), this, ACTIVITY_REQUEST_CODE);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				errorDialog.show();
			}
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub

		if (mClient.getLastLocation() != null) {

			currentLocation = new Location(mClient.getLastLocation());

			initializeMap();
		} else {
			Toast.makeText(this, "No location obtained", 10).show();
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	AlertDialog alertDialog = null;

	private void drawDirections(List<LatLng> points) {
		PolylineOptions options = new PolylineOptions().width(5).color(
				Color.BLACK);
		for (LatLng point : points) {
			options.add(point);
		}
		map.addPolyline(options);
	}

	public class GetDirectionsTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String latitude = params[0];
			String longitude = params[1];
			String currentLatitude = params[2];
			String currentLongitude = params[3];
			String response = null;
			HttpURLConnection connection = null;
			URL url;
			try {
				url = new URL(
						"http://maps.googleapis.com/maps/api/directions/json?origin="
								+ currentLatitude + "," + currentLongitude
								+ "&destination=" + latitude + "," + longitude
								+ "&sensor=false");
				connection = (HttpURLConnection) url.openConnection();
				connection.setReadTimeout(2000);
				connection.setConnectTimeout(4000);
				connection.setRequestMethod("POST");
				connection.setDoInput(true);
				connection.connect();
				int responseCode = connection.getResponseCode();

				if (responseCode == 200) {
					InputStream inputStream = connection.getInputStream();
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(inputStream));
					StringBuilder builder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						builder.append(line);
					}

					response = builder.toString();
				} else {
					response = "Response was not successful";
				}

			} catch (Exception exception) {
				exception.printStackTrace();
			} finally {
				connection.disconnect();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			drawDirections(new DirectionParser().parseDirections(result));
		}

	}
}
