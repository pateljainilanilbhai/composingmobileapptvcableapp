package com.mad.fragmentsdemo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@SuppressLint("NewApi")
public class StateFragment extends Fragment {
	ListView stateListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.state_fragment, container, false);
		stateListView = (ListView) view.findViewById(R.id.listView1);
		return view;
	}

	public void setList(String country) {
		// TODO Auto-generated method stub
		Resources res = getResources();
		ListView lv = (ListView) getView().findViewById(R.id.listView1);
		String[] states = null;
		System.out.println(country);

		if (country.equalsIgnoreCase(res.getStringArray(R.array.nationList)[0])) {
			states = res.getStringArray(R.array.IndiaStateList);
		}
		if (country.equalsIgnoreCase(res.getStringArray(R.array.nationList)[1])) {
			states = res.getStringArray(R.array.SriLankaStateList);
		}
		if (country.equalsIgnoreCase(res.getStringArray(R.array.nationList)[2])) {
			states = res.getStringArray(R.array.AustralianStateList);
		}
		if (country.equalsIgnoreCase(res.getStringArray(R.array.nationList)[3])) {
			states = res.getStringArray(R.array.BangladeshStateList);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, states);
		lv.setAdapter(adapter);
	}
}
