package com.app3c;

import java.util.ArrayList;
import java.util.List;

import com.app3c.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SubscribeActivity extends Activity {

	ListView categoryList;
	List<String>category,confirmed;
	Dialog subscribe_dialog;
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
				subscribe_dialog.show();
			}
		});

	}


	public List<String> getCategoryNames(){
		//Hard coded values for list view
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

}
