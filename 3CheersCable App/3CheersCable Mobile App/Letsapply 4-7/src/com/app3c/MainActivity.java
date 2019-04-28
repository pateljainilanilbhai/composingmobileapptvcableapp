package com.app3c;

import java.util.ArrayList;

import com.app3c.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{

	public static final int REQUEST_CATEGORY = 1;
	public static final String CATEGORIES_LIST = "categories";
	ListView listView;
	TextView textView;
	Button button;
	ArrayList<String> categoryList;
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
		la=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
		listView.setAdapter(la);
		// When no categories subscribed
		if(categoryList.size() == 0){
			textView.setVisibility(View.VISIBLE);
		}

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		// When dialog returns result
		if(data!=null){
			categoryList = data.getStringArrayListExtra(CATEGORIES_LIST);
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
}
