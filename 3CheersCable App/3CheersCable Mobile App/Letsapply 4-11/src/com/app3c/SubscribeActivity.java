package com.app3c;

import java.util.ArrayList;
import java.util.List;

import com.app3c.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SubscribeActivity extends Activity implements OnClickListener{

	ListView categoryList;
	List<String>category,confirmed;
	Dialog subscribe_dialog;
	Button btn_ok,btn_cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		category=getCategoryNames();
		ArrayAdapter<String> categoryListAdapter=new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice,category);
		categoryList=(ListView) findViewById(R.id.listView1);
		categoryList.setAdapter(categoryListAdapter);
		categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		Button next=(Button)findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				// TODO Logic for the event handler goes here
				confirmed=new ArrayList<String>();
				SparseBooleanArray sp=categoryList.getCheckedItemPositions();		 		 				
				for(int i=0;i<category.size();i++) {
					if(sp.get(i)){
						confirmed.add(category.get(i));
					}		 		 			 	    	
				}

				subscribe_dialog=new Dialog(SubscribeActivity.this);
				subscribe_dialog.setContentView(R.layout.subscribe_dialog);
				ListView lv=(ListView)subscribe_dialog.findViewById(R.id.listView2);
				ArrayAdapter<String> la=new ArrayAdapter<String>(SubscribeActivity.this,
						android.R.layout.simple_list_item_1,confirmed);
				lv.setAdapter(la);
				subscribe_dialog.setTitle("Confirm subscription");	
				btn_ok = (Button) subscribe_dialog.findViewById(R.id.button2);
				btn_ok.setOnClickListener(SubscribeActivity.this);
				btn_cancel=(Button)subscribe_dialog.findViewById(R.id.button1);
				btn_cancel.setOnClickListener(SubscribeActivity.this);
				subscribe_dialog.show();
			}
		});

	}


	public List<String> getCategoryNames(){
		List<String> categoryNames=new ArrayList<String>();
		categoryNames.add("Movies");
		categoryNames.add("Music");
		categoryNames.add("Sports");
		categoryNames.add("Entertainment");
		categoryNames.add("Kids");
		categoryNames.add("Lifestyle");

		return categoryNames;
	}    	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.subscribe, menu);
		return true;
	}


	@Override
	public void onClick(View arg0) {
		if(arg0 == btn_ok){
			
			Intent intent = new Intent();
			intent.putStringArrayListExtra(MainActivity.CATEGORIES_LIST, (ArrayList<String>) confirmed);		
				setResult(MainActivity.REQUEST_CATEGORY, intent);
			subscribe_dialog.dismiss();
			this.finish();
		}
		else if(arg0==btn_cancel)
		{
			subscribe_dialog.dismiss();
		}
	}

}
