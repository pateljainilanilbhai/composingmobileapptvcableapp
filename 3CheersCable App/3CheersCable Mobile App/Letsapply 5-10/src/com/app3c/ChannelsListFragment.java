package com.app3c;

import java.util.ArrayList;

import com.app3c.beans.ChannelResponseParser;
import com.app3c.beans.ChannelsBean;
import com.app3c.beans.ChannelsBean.Channel;
import com.app3c.webserviceconsumer.GenerateURLs;
import com.app3c.webserviceconsumer.WebResponse;
import com.app3c.webserviceconsumer.WebServiceFinishedListener;
import com.app3c.webserviceconsumer.WebServiceHitter;
import com.app3c.webserviceconsumer.GenerateURLs.Operation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ChannelsListFragment extends ListFragment implements WebServiceFinishedListener {
	
	Context context;
	String cID, cName;
	private ChannelsBean bean;
	OnTVGuideChannelSelectedListener channelCallback;
	ArrayList<String> channelIds;
	
	public interface OnTVGuideChannelSelectedListener {
		public void onTVGuideChannelSelected(String channelId);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getActivity().getIntent().getExtras();
		context=getActivity();
		if(bundle != null){
			cID = bundle.getString(CategoriesListFragment.CATEGORY_ID);
			cName = bundle.getString(CategoriesListFragment.CATEGORY_NAME);
			
			new WebServiceHitter(this).execute(GenerateURLs.getPostURL(Operation.CHANNELS,cID));
		}
		else{
			
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try{
			channelCallback = (OnTVGuideChannelSelectedListener) activity;
		}
		catch (Exception e) {
			throw new ClassCastException(activity.toString() + " must implement OnTVGuideChannelSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		channelCallback.onTVGuideChannelSelected(channelIds.get(position));
		getListView().setItemChecked(position, true);
	}
	
	
	
	@Override
	public void onNetworkCallComplete(WebResponse object) {
		bean = ChannelResponseParser.parse(object.getResponse().toString());
		ArrayList<Channel> channels = bean.getChannels();
		ArrayList<String> optionsList = new ArrayList<String>();
		channelIds = new ArrayList<String>();
		
		for(int i=0; i<channels.size(); i++){
			optionsList.add(channels.get(i).getName());
			channelIds.add(channels.get(i).getChannelId());
			
		}
		
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, optionsList);
		
		setListAdapter(adapter);
		
	}

	@Override
	public void onNetworkCallCancel() {
		// TODO Auto-generated method stub
		
	}

}
