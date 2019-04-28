package com.app3c;

import java.util.ArrayList;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
		la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
		listView.setAdapter(la);
		if(categoryList.size()==0)
			textView.setVisibility(View.VISIBLE);
		
		listView.setOnItemClickListener(this);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// When dialog returns result
		if(data!=null){
			categoryList = data.getStringArrayListExtra(CATEGORY_LIST);
			categoryIdList = data.getStringArrayListExtra(CATEGORY_ID_LIST);
			la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
			textView.setVisibility(View.INVISIBLE);
			listView.setAdapter(la);
		}
		// When dialog doesn't return result
		else
		{
			categoryList.clear();
			la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
			listView.setAdapter(la);
			textView.setVisibility(View.VISIBLE);
		}
	}
	
	
	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(this, SubscribeActivity.class);
		startActivityForResult(intent, REQUEST_CATEGORY);
		
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
