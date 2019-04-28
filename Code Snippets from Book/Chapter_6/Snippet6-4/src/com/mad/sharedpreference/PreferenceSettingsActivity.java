package com.mad.sharedpreference;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class PreferenceSettingsActivity extends Activity {
	Button btnSave;
	EditText etMessage,etSignature;
	CheckBox chkEnable;
	SharedPreferences preferences;
	Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference_settings);
		btnSave=(Button)findViewById(R.id.btnSave);
		etMessage=(EditText)findViewById(R.id.etMessage);
		etSignature=(EditText)findViewById(R.id.etSignature);
		chkEnable=(CheckBox)findViewById(R.id.chkEnable);
		preferences=getSharedPreferences("SMSPreferences", MODE_PRIVATE);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				editor=preferences.edit();
				editor.putBoolean("SendSMS", chkEnable.isChecked());
				editor.putString("Message", etMessage.getText().toString());
				editor.putString("Signature", etSignature.getText().toString());
				editor.commit();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_preference_settings, menu);
		return true;
	}

}
