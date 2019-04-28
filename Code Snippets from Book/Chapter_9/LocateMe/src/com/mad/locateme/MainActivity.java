package com.mad.locateme;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.mad.locateme.R;

public class MainActivity extends FragmentActivity implements LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	// Setting normal and max update intervals ( in ms)
	public static final long UPDATE_INTERVAL = 5000;
	public static final long FASTEST_UPDATE_INTERVAL = 2000;

	public static final int ACTIVITY_REQUEST_CODE = 5555;

	private LocationRequest mLocationRequest;
	private LocationClient mClient;

	boolean mUpdatesRequested = false;
	private TextView address_from_coords;
	private TextView coordinates;
	private TextView updates_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getViewsHandles();

		setUpLocationParams();
	}

	private void getViewsHandles() {
		address_from_coords = (TextView) findViewById(R.id.address_textView);
		coordinates = (TextView) findViewById(R.id.coordinates_textView);
		updates_status = (TextView) findViewById(R.id.locationupdates_Status);
	}

	private void setUpLocationParams() {
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
		// Create a new location client. Using 'this' as the Activity handles
		// callbacks
		mClient = new LocationClient(this, this, this);

	}

	private boolean isConnectedToLocationServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode == ConnectionResult.SUCCESS) {
			return true;
		} else {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			dialog.show();
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		switch (requestCode) {

		case ACTIVITY_REQUEST_CODE:

			switch (resultCode) {
			// If Google Play services resolved the problem
			case Activity.RESULT_OK:

				updates_status.setText("Result is OK...");

				break;

			default:
				updates_status.setText("Can't resolve error...");

				break;
			}
		default:

			break;
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// Connect the client
		mClient.connect();
	}

	@Override
	public void onStop() {
		super.onStop();
		if (mClient.isConnected()) {
			mClient.removeLocationUpdates(this);
		}

		mClient.disconnect();

		
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		// Google play can resolve some errors. Use hasResolution() to find out

		if (connectionResult.hasResolution()) {
			try {
				// Use startResolutionForResult() to start the Activity
				connectionResult.startResolutionForResult(this,
						ACTIVITY_REQUEST_CODE);
			} catch (IntentSender.SendIntentException e) {
				e.printStackTrace();
			}
		} else {

			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					connectionResult.getErrorCode(), this,
					ACTIVITY_REQUEST_CODE);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {

				// Create a new DialogFragment
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();

				errorFragment.setDialog(errorDialog);
				errorFragment
						.show(getSupportFragmentManager(), "LOCATION DEMO");

			}
		}

	}

	@Override
	public void onConnected(Bundle arg0) {
		System.out.println("connected");
		// Check if updates are requested
		if (mUpdatesRequested) {
			mClient.requestLocationUpdates(mLocationRequest, this);
		}

	}

	@Override
	public void onDisconnected() {
		updates_status.setText("Disconnected...");

	}

	@Override
	public void onLocationChanged(Location arg0) {
		if (arg0 != null) {
			coordinates.setText(arg0.getLatitude() + " : "
					+ arg0.getLongitude());
		}
	}

	public void viewMap(View v) {

		startActivity(new Intent(MainActivity.this, LocateMeMap.class));
	}

	public void stopLocationUpdates(View v) {
		mUpdatesRequested = false;
		if (isConnectedToLocationServices()) {
			mClient.removeLocationUpdates(this);
			updates_status.setText("Periodic updates now disabled");
		}

	}

	public void startLocationUpdates(View v) {
		mUpdatesRequested = true;
		if (isConnectedToLocationServices()) {
			mClient.requestLocationUpdates(mLocationRequest, this);
			updates_status.setText("Periodic updates now enabled");
		}
	}

	public void getCurrentLocation(View v) {
		if (isConnectedToLocationServices()) {
			if (mClient.isConnected() && mClient.getLastLocation() != null) {
				Location currentLocation = new Location(
						mClient.getLastLocation());
				coordinates.setText(currentLocation.getLatitude() + " : "
						+ currentLocation.getLongitude());
			}
		}
	}

	@SuppressLint("NewApi")
	public void getCurrentAddress(View v) {

		if (!Geocoder.isPresent()) {
			updates_status.setText("Geocoder not present!!!");
			return;
		}

		if (isConnectedToLocationServices()) {
			Location currentLocation = mClient.getLastLocation();
			updates_status.setText("Getting address. PLease wait...");
			(new MainActivity.GetAddressTask(this)).execute(currentLocation);
		}

	}

	protected class GetAddressTask extends AsyncTask<Location, Void, String> {

		Context context;

		public GetAddressTask(Context context) {

			super();

			this.context = context;
		}

		@Override
		protected String doInBackground(Location... params) {
			Geocoder geocoder = new Geocoder(context, Locale.getDefault());

			List<Address> addresses = null;

			try {

				addresses = geocoder.getFromLocation(params[0].getLatitude(),
						params[0].getLongitude(), 1);

				// Catch network or other I/O problems.
			} catch (IOException exception1) {
				exception1.printStackTrace();
				return "IOException while getting address...";

				// Catch incorrect latitude or longitude values
			} catch (IllegalArgumentException exception2) {

				// updates_status.setText("Invalid coordinates...");
				return "Invalid coordinates...";
			}

			if (addresses != null && addresses.size() > 0) {

				// Get the first address
				Address address = addresses.get(0);

				String addressText = String.format(
						"%s, %s, %s",

						address.getMaxAddressLineIndex() > 0 ? address
								.getAddressLine(0) : "",

						address.getLocality(),

						address.getCountryName());

				return addressText;

			} else {
				return "No address found...";
			}
		}

		@Override
		protected void onPostExecute(String address) {

			updates_status.setText("Address updated...");

			address_from_coords.setText(address);
		}
	}

	public static class ErrorDialogFragment extends DialogFragment {

		private Dialog dialog;

		public ErrorDialogFragment() {
			super();
			dialog = null;
		}

		public void setDialog(Dialog dialog) {
			this.dialog = dialog;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return dialog;
		}
	}
}
