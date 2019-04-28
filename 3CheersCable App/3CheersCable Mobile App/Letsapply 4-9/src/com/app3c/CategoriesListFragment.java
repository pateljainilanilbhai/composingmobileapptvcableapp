package com.app3c;

import java.util.ArrayList;
import java.util.Collections;

import com.app3c.R;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoriesListFragment extends ListFragment {
	
	public static String CATEGORY_NAME = "category";
	ArrayList<String> optionsList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		optionsList = new ArrayList<String>();
		Collections.addAll(optionsList, getResources().getStringArray(R.array.categoriesList));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Bundle args = new Bundle();
		args.putString(CATEGORY_NAME, optionsList.get(position));
		ChannelsDialogFragment fragment = new ChannelsDialogFragment();
		fragment.setArguments(args);
		fragment.show(getFragmentManager(), CATEGORY_NAME);
	}

}
