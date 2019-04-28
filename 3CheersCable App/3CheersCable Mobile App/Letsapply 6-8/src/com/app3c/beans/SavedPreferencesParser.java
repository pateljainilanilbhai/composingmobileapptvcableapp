package com.app3c.beans;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SavedPreferencesParser {
	
	public static ArrayList<String> parse(String prefString){
		ArrayList<String> prefList = new ArrayList<String>();
		String name = "pref";
		if(prefString!=null){			
			try {
				JSONObject obj = new JSONObject(prefString);
				for(int i =0; i<obj.length(); i++){
					prefList.add(obj.getString(name + i));
					System.out.println(prefList.get(i));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		else
			prefList = null;
		return prefList;
	}

	public static JSONObject getPrefJSON(List<String> prefList){
		JSONObject obj = new JSONObject();
		String name = "pref";
		try{
			for(int i=0; i<prefList.size(); i++){
				obj.put(name + i, prefList.get(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
