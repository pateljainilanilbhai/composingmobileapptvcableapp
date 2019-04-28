package com.app3c;

import java.util.ArrayList;

import com.app3c.beans.SavedPreferencesParser;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{

	public static final int REQUEST_CATEGORY = 1;
	public static final String CATEGORY_ID_LIST = "categorieIDs";
	public static final String CATEGORY_LIST = "categories";
	public static final String SHARED_PREF_NAME = "sharedpref";
	ListView listView;
	TextView textView;
	Button button;
	ArrayList<String> categoryList, categoryIdList;
	ArrayAdapter<String> la;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView1);
		textView = (TextView) findViewById(R.id.textView1);
		button=(Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
		textView.setVisibility(View.INVISIBLE);
		categoryList = new ArrayList<String>();
		categoryIdList = new ArrayList<String>();
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences pref = getSharedPreferences(MainActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		String ids = pref.getString(CATEGORY_ID_LIST, null);
		String names = pref.getString(CATEGORY_LIST, null);
		
		if(ids != null){
			categoryIdList = SavedPreferencesParser.parse(ids);
			categoryList =SavedPreferencesParser.parse(names);
			
			la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
			listView.setAdapter(la);
			textView.setVisibility(View.INVISIBLE);
		}
		else{
			if(la!=null)la.clear();
			textView.setVisibility(View.VISIBLE);
		}
		
		listView.setOnItemClickListener(this);
		
	}
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, SubscribeActivity.class);
		startActivity(intent);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;

		default:
			return false;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent(this, TVGuide.class);
		intent.putExtra(CategoriesListFragment.CATEGORY_ID, categoryIdList.get(arg2));	
		intent.putExtra(CategoriesListFragment.CATEGORY_NAME, categoryList.get(arg2));
		startActivity(intent);
	}

}
