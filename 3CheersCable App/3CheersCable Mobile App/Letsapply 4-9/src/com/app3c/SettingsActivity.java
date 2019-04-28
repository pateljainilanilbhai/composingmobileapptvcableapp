package com.app3c;

import com.app3c.OptionsListFragment.OnOptionSelectedListener;
import com.app3c.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.Menu;

public class SettingsActivity extends Activity implements OnOptionSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		if(getFragmentManager().findFragmentByTag("tag2")!=null)
		{
			getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("tag2")).commit();
		}
		if(getFragmentManager().findFragmentByTag("tag1")!=null)
		{
			getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("tag1")).commit();
		}
		if(findViewById(R.id.container) != null){
			OptionsListFragment optionsFrag = new OptionsListFragment();
			getFragmentManager().beginTransaction().replace(R.id.container, optionsFrag,"tag1").commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onOptionSelected(int option) {
		Fragment fragment;
		switch(option){
		case 0: fragment = new CategoriesListFragment();
			break;
		case 1: fragment = new SettingsFragment();
			break;
		case 2: fragment = new CustomerCareFragment();
			break;
		default: fragment = new CategoriesListFragment();
			break;
		}
		
		if(findViewById(R.id.optionsContainer) != null){
			getFragmentManager().beginTransaction().replace(R.id.optionsContainer, fragment, "tag2").commit();
		}
		else if(findViewById(R.id.container) != null){
			getFragmentManager().beginTransaction().replace(R.id.container, fragment, "tag2").addToBackStack(null).commit();
		}
		
	}
}
