package com.mad.fragmentsdemo;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);//Uncomment this line to add fragments  statically, and comment below line and other life cycle methods
		
		setContentView(R.layout.activity_main_dynamic);
		
	}
	@SuppressLint("NewApi")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
		fragmentTransaction.add(R.id.nationfragmentContainer,new NationFragment(),"tagNationFragment");
		fragmentTransaction.add(R.id.stateFragmentContainer,new StateFragment(),"tagStateFragment");
		fragmentTransaction.commit();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
		fragmentTransaction.remove(getFragmentManager().findFragmentByTag("tagNationFragment"));
		fragmentTransaction.remove(getFragmentManager().findFragmentByTag("tagStateFragment"));
		fragmentTransaction.commit();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@SuppressLint("NewApi")
	
	public void onFragmentAction(String country) {
		// TODO Auto-generated method stub
		FragmentManager fragmentManager=getFragmentManager();
		StateFragment stateFragment=(StateFragment)fragmentManager.findFragmentByTag("tagStateFragment");
		stateFragment.setList(country);
	}

}
