package com.app3c;


import java.util.ArrayList;
import com.app3c.beans.ShowsBean;
import com.app3c.beans.ShowsResponseParser;
import com.app3c.beans.ShowsBean.Show;
import com.app3c.webserviceconsumer.GenerateURLs;
import com.app3c.webserviceconsumer.WebResponse;
import com.app3c.webserviceconsumer.WebServiceFinishedListener;
import com.app3c.webserviceconsumer.WebServiceHitter;
import com.app3c.webserviceconsumer.GenerateURLs.Operation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class ShowsListFragment extends ListFragment implements WebServiceFinishedListener {

	ArrayList<String> showNames, favs;
	ShowsArrayAdapter adapter;
	ShowsBean bean;
	ArrayList<Show> showslist;
	String channel = null;
	Context context;
	public static final String CHANNEL_ID = "channelid";
	public static final String FAVOURITE_CHANNELS = "fav";
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString(CHANNEL_ID, channel);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		if(savedInstanceState!=null)
		channel=savedInstanceState.getString(CHANNEL_ID);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub				
		setHasOptionsMenu(true);		
		return inflater.inflate(R.layout.showslist_layout, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		showNames = new ArrayList<String>();
		favs = new ArrayList<String>();
		context=getActivity();
		if(getArguments() != null){
			channel = getArguments().getString(CHANNEL_ID);		
			showChannelShows(channel);
		}		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.activity_tvguide, menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.sendSMS:
			showSendSMSDialog();
			return true;

		default:
			return false;
		}
	}
	
	public void showSendSMSDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Send favorites through SMS?")
		.setMessage("Do you wish to send your favorite shows to a contact through SMS?")
		.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String sms = "My favorite shows are - Hollywood Movie 08:00-12:00, Bollywood Movie 12:00-15:00";//This hard coding has to be replaced
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:1234"));
	            sendIntent.putExtra("sms_body", sms); 
	            startActivity(sendIntent);
			}
		})
		.setNegativeButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				
			}
		}).create().show();
			
	}
	
	public void showChannelShows(String cid) {
		System.out.println("show channel shows called " + cid);
		new WebServiceHitter(this).execute(GenerateURLs.getPostURL(Operation.SHOWS,cid));
	}

	@Override
	public void onNetworkCallComplete(WebResponse object) {
		bean = ShowsResponseParser.parse(object.getResponse().toString());
		showslist = bean.getShows();
		System.out.println("arraylist size "+showslist.size());				
		adapter = new ShowsArrayAdapter(context, showslist);
		setListAdapter(adapter);
	}

	@Override
	public void onNetworkCallCancel() {
		// TODO Auto-generated method stub
		
	}
	
	
	class ShowsArrayAdapter extends ArrayAdapter<Show>{
		
		Context context;
		ArrayList<Show> showList;
		
		public ShowsArrayAdapter(Context context, ArrayList<Show> objects) {
			super(context,R.layout.shows_list, objects);
			this.context = context;
			this.showList = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if(view == null){
				LayoutInflater inflater = ((Activity)context).getLayoutInflater();
				view = inflater.inflate(R.layout.shows_list, parent, false);
			}
			
			final Show show = showList.get(position);
			if(show != null){
				TextView name = (TextView) view.findViewById(R.id.txtShowName);
				name.setText(show.getShowName());
				TextView time = (TextView) view.findViewById(R.id.txtShowTime);
				time.setText(show.getShowTiming());
				ToggleButton fav = (ToggleButton) view.findViewById(R.id.btnFav);
				fav.setChecked(show.isFavourite());
				fav.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						if(arg1){
							System.out.println(show.getShowId());
							favs.add(show.getShowId()+"");
						}
						else{
							favs.remove(show.getShowId());
						}
					}
				});
			}
			
			return view;
		}
		
	}
}