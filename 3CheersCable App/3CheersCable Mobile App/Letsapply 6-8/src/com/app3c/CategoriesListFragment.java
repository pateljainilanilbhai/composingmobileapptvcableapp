package com.app3c;

import java.util.ArrayList;

import com.app3c.beans.CategoriesBean;
import com.app3c.beans.CategoriesResponseParser;
import com.app3c.beans.CategoriesBean.Category;
import com.app3c.webserviceconsumer.GenerateURLs;
import com.app3c.webserviceconsumer.WebResponse;
import com.app3c.webserviceconsumer.WebServiceFinishedListener;
import com.app3c.webserviceconsumer.WebServiceHitter;
import com.app3c.webserviceconsumer.GenerateURLs.Operation;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesListFragment extends ListFragment implements WebServiceFinishedListener{
	
	public static String CATEGORY_ID = "id";
	public static String CATEGORY_NAME = "category";
	ArrayList<String> optionsList;
	private ProgressDialog myProgressDialog;
	private CategoriesBean bean;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		optionsList = new ArrayList<String>();
		new WebServiceHitter(this).execute(GenerateURLs.getPostURL(Operation.CATEGORIES));
		myProgressDialog = new ProgressDialog(getActivity());
		myProgressDialog.setMessage("Loading...");
		myProgressDialog.setCancelable(false);
		myProgressDialog.setMax(100);
		myProgressDialog.show();		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Bundle args = new Bundle();
		args.putString(CATEGORY_ID, bean.getCategories().get(position).getCategoryId());
		args.putString(CATEGORY_NAME, optionsList.get(position));
		ChannelsDialogFragment fragment = new ChannelsDialogFragment();
		fragment.setArguments(args);
		fragment.show(getFragmentManager(), CATEGORY_NAME);
	}

	@Override
	public void onNetworkCallComplete(WebResponse object) {
		bean = CategoriesResponseParser.parse(object.getResponse().toString());
		ArrayList<Category> categories = bean.getCategories();
		
		for(int i=0; i<categories.size(); i++){
			optionsList.add(categories.get(i).getName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
		setListAdapter(adapter);
		myProgressDialog.dismiss();
	}

	@Override
	public void onNetworkCallCancel() {
		// TODO Auto-generated method stub
		myProgressDialog.dismiss();
	}

}
