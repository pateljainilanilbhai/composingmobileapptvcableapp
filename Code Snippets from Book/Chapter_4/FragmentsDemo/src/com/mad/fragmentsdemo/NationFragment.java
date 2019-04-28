package com.mad.fragmentsdemo;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class NationFragment extends Fragment {
	
	MainActivity mainActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.nation_fragment, container,false);
		mainActivity=(MainActivity)getActivity();
		ListView nationsList=(ListView)view.findViewById(R.id.listView1);
		nationsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mainActivity.onFragmentAction(getResources().getStringArray(R.array.nationList)[(int)arg3]);
			}
		});
		return view;
	}
}
