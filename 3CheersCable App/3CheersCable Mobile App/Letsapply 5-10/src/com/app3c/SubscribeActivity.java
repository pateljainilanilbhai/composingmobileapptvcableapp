package com.app3c;

import java.util.ArrayList;
import java.util.List;

import com.app3c.beans.CategoriesBean;
import com.app3c.beans.CategoriesResponseParser;
import com.app3c.beans.CategoriesBean.Category;
import com.app3c.webserviceconsumer.GenerateURLs;
import com.app3c.webserviceconsumer.WebResponse;
import com.app3c.webserviceconsumer.WebServiceFinishedListener;
import com.app3c.webserviceconsumer.WebServiceHitter;
import com.app3c.webserviceconsumer.GenerateURLs.Operation;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class SubscribeActivity extends Activity implements OnClickListener, WebServiceFinishedListener{

	ListView categoryList;
	List<String> optionsList,optionsIdList, confirmed, confirmedId;
	Dialog subscribe_dialog;
	Button btn_ok,btn_cancel;
	private ProgressDialog myProgressDialog;
	private CategoriesBean bean;
	ArrayList<String> subscribed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subscribe);
		new WebServiceHitter(this).execute(GenerateURLs.getPostURL(Operation.CATEGORIES));
		myProgressDialog = new ProgressDialog(this);
		myProgressDialog.setMessage("Loading...");
		myProgressDialog.setCancelable(false);
		myProgressDialog.setMax(100);
		myProgressDialog.show();	

		categoryList=(ListView) findViewById(R.id.listView1);		
		categoryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		
		Button next=(Button)findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				confirmed=new ArrayList<String>();
				confirmedId=new ArrayList<String>();
				SparseBooleanArray sp=categoryList.getCheckedItemPositions();		 		 				
				for(int i=0;i<optionsList.size();i++) {
					System.out.println("The size is"+sp.size());
					if(sp.get(i)){
						confirmed.add(optionsList.get(i));
						confirmedId.add(optionsIdList.get(i));
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
			
			intent.putStringArrayListExtra(MainActivity.CATEGORY_LIST, (ArrayList<String>) confirmed);		
			intent.putStringArrayListExtra(MainActivity.CATEGORY_ID_LIST, (ArrayList<String>) confirmedId);
			if(getParent()==null){
				setResult(MainActivity.REQUEST_CATEGORY, intent);
			}
			else{
				getParent().setResult(MainActivity.REQUEST_CATEGORY, intent);
			}
			subscribe_dialog.dismiss();
			this.finish();
		}			
		else if(arg0==btn_cancel)
		{
			subscribe_dialog.dismiss();
		}
	}


	@Override
	public void onNetworkCallComplete(WebResponse object) {
		bean = CategoriesResponseParser.parse(object.getResponse().toString());
		optionsList = new ArrayList<String>();
		optionsIdList = new ArrayList<String>();
		subscribed = new ArrayList<String>();
		ArrayList<Category> categories = bean.getCategories();
		
		
		for(int i=0; i<categories.size(); i++){
			optionsList.add(categories.get(i).getName());
			optionsIdList.add(categories.get(i).getCategoryId());
		}
		ArrayAdapter<String> channelListAdapter=new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice,optionsList);
		categoryList.setAdapter(channelListAdapter);
		myProgressDialog.dismiss();
	}


	@Override
	public void onNetworkCallCancel() {
		myProgressDialog.dismiss();
	}

}
