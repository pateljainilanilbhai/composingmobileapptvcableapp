package com.app3c;



import java.util.ArrayList;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment {
	ArrayList<String> contactNames =new ArrayList<String>();
	ArrayList<String> contactNumbers =new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.userprefs);
	}

}
