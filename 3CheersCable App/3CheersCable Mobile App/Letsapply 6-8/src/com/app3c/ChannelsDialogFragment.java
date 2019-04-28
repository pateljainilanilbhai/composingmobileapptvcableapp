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


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChannelsDialogFragment extends DialogFragment implements WebServiceFinishedListener{
	
	AlertDialog.Builder builder;
	String cID, cName;
	private ChannelsBean bean;
	View view;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		builder = new AlertDialog.Builder(getActivity());
		cID = getArguments().getString(CategoriesListFragment.CATEGORY_ID);
		cName = getArguments().getString(CategoriesListFragment.CATEGORY_NAME);
		
		new WebServiceHitter(this).execute(GenerateURLs.getPostURL(Operation.CHANNELS,cID));
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.channel_dialog,null);
		builder.setTitle(cName)
		.setView(view)
		.setNegativeButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		});		
		return builder.create();
	}

	@Override
	public void onNetworkCallComplete(WebResponse object) {
		bean = ChannelResponseParser.parse(object.getResponse().toString());
		ArrayList<Channel> channels = bean.getChannels();
		ArrayList<String> optionsList = new ArrayList<String>();
		for(int i=0; i<channels.size(); i++){
			optionsList.add(channels.get(i).getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
		((ListView) view.findViewById(R.id.lvChannels)).setAdapter(adapter);	
		
	}

	@Override
	public void onNetworkCallCancel() {
		
	}

	

}
