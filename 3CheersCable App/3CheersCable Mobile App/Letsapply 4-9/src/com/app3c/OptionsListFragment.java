package com.app3c;

import java.util.ArrayList;
import java.util.Collections;

import com.app3c.R;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OptionsListFragment extends ListFragment {
	
	OnOptionSelectedListener optionsCallback;
	
	public interface OnOptionSelectedListener{
		public void onOptionSelected(int option);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		ArrayList<String> optionsList = new ArrayList<String>();
		Collections.addAll(optionsList, getResources().getStringArray(R.array.optionsList));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, optionsList);
		setListAdapter(adapter);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try{
			optionsCallback = (OnOptionSelectedListener) activity;
		}
		catch (Exception e) {
			throw new ClassCastException(activity.toString() + " must implement OnOptionSelectedListener");
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		optionsCallback.onOptionSelected(position);
		getListView().setItemChecked(position, true);
	}
}
