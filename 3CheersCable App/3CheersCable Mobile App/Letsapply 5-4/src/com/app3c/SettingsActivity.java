package com.app3c;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Menu;

@SuppressLint("NewApi")
public class SettingsActivity extends Activity {

	public static final String SELECTED_TAB = "selected_tab";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab = actionBar.newTab()
					.setIcon(R.drawable.category)
					.setTabListener(new TabListener<CategoriesListFragment>(this, "Categories", CategoriesListFragment.class));
		actionBar.addTab(tab);
		
		tab = actionBar.newTab()
				.setIcon(R.drawable.settings)
				.setTabListener(new TabListener<SettingsFragment>(this, "Settings", SettingsFragment.class));
		actionBar.addTab(tab);
			
		tab = actionBar.newTab()
				.setIcon(R.drawable.custcare)
				.setTabListener(new TabListener<CustomerCareFragment>(this, "CustomerCare", CustomerCareFragment.class));
		actionBar.addTab(tab);
		
		if(savedInstanceState != null){
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_TAB, 0));			
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt(SELECTED_TAB, getActionBar().getSelectedNavigationIndex());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return true;
	}
	
	public static class TabListener<T extends Fragment> implements android.app.ActionBar.TabListener{

		private Fragment fragment;
		private final Activity activity;
		private final String tag;
		private final Class<T> fragClass;
		
		public TabListener(Activity act, String tag1, Class<T> cls) {
			activity = act;
			tag = tag1;
			fragClass = cls;
		}
		
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			
			
		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
			fragment = activity.getFragmentManager().findFragmentByTag(tag);
			if(fragment == null){
				fragment = Fragment.instantiate(activity, fragClass.getName());
				arg1.add(android.R.id.content,fragment, tag);
			}
			else{
				arg1.attach(fragment);
			}
			
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			if(fragment != null){
				arg1.detach(fragment);
				FragmentManager manager = activity.getFragmentManager();
				if(manager.getBackStackEntryCount()>0){
					manager.popBackStack();
				}
			}			
		}
		
	}

}
