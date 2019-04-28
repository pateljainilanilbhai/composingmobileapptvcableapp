package com.app3c;

import com.app3c.beans.LoginBean;
import com.app3c.beans.LoginResponseParser;
import com.app3c.webserviceconsumer.AppConfig;
import com.app3c.webserviceconsumer.GenerateURLs;
import com.app3c.webserviceconsumer.ResponseCode;
import com.app3c.webserviceconsumer.WebResponse;
import com.app3c.webserviceconsumer.WebServiceFinishedListener;
import com.app3c.webserviceconsumer.WebServiceHitter;
import com.app3c.webserviceconsumer.GenerateURLs.Operation;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener, WebServiceFinishedListener{
	private Button button;
	private SharedPreferences prefs;
	private String password;
	private String loginId;
	EditText loginIdField,passwordField;
	private ProgressDialog progDailog;
	private AsyncTask<Object, Void, WebResponse> asyncTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		loginIdField=((EditText)findViewById(R.login.editText1));
		passwordField=((EditText)findViewById(R.login.editText2));
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		button = (Button)findViewById(R.login.button1);
		button.setOnClickListener(this);
		if(!prefs.getString(AppConfig.USER_NAME, "").equalsIgnoreCase("")  &&  !prefs.getString(AppConfig.PASSWORD, "").equalsIgnoreCase(""))
		{
			loginIdField.setText(prefs.getString(AppConfig.USER_NAME, ""));
			passwordField.setText(prefs.getString(AppConfig.PASSWORD, ""));
		}
		
	}

	@Override
	public void onNetworkCallComplete(WebResponse object) {
		if(object.getResponseCode() == ResponseCode.SUCCESS){
			LoginBean loginresponse = LoginResponseParser.parseLoginResponse(object.getResponse().toString());
			if(progDailog!=null && progDailog.isShowing())
				progDailog.dismiss();
			if(loginresponse!=null && loginresponse.getResult()==1 ){
				Editor editor = prefs.edit();
				editor.putString(AppConfig.USER_NAME, loginresponse.getUserName());
				editor.putString(AppConfig.PASSWORD, loginresponse.getPassword());
				editor.commit();
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(this, "Wrong Login id/Password", Toast.LENGTH_LONG).show();
				button.setEnabled(true);
				button.setText("      Login      ");
			}
		}
		else{
			Toast.makeText(this, "Unable to contact server", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		progDailog=null;
	}
	@Override
	public void onNetworkCallCancel() {
		if(progDailog!=null && progDailog.isShowing())
			progDailog.dismiss();
		Toast.makeText(this, "Login failed due to network error. Please try again", Toast.LENGTH_LONG).show();
		((EditText)findViewById(R.login.editText2)).setText("");
		button.setEnabled(true);
		button.setText("      Login      ");
	}
	public void onClick(View v) {
		
		loginId = loginIdField.getText().toString();
		password = passwordField.getText().toString();
		if(loginId.equalsIgnoreCase("") || password.equalsIgnoreCase("")){
			Toast.makeText(LoginActivity.this, "Please enter the login credentials", Toast.LENGTH_LONG).show();
			return;
		}
		button.setEnabled(false);
		button.setText("Wait a moment...");
		/*Editor editor = prefs.edit();
		editor.putString(AppConfig.USER_NAME, loginId);
		editor.putString(AppConfig.PASSWORD, password);
		editor.commit();*/
		if(!loginId.equalsIgnoreCase("") && !password.equalsIgnoreCase("")){
			progDailog = ProgressDialog.show(this, "Please wait...", "Authenticating...");
			progDailog.setCancelable(true);
			progDailog.setOnCancelListener(new OnCancelListener(){
				@Override
				public void onCancel(DialogInterface dialog){
					Toast.makeText(LoginActivity.this, "You've cancelled login operation. App will exit now.", Toast.LENGTH_LONG).show();
					asyncTask.cancel(true);
					finish();
				}
			});
			asyncTask = new WebServiceHitter(LoginActivity.this).execute(GenerateURLs.getPostURL(Operation.LOGIN, loginId,password, null));
		}
		else{
			Toast.makeText(this, "Please enter Login id & Password", Toast.LENGTH_LONG).show();
			button.setEnabled(true);
		}
	}

}

