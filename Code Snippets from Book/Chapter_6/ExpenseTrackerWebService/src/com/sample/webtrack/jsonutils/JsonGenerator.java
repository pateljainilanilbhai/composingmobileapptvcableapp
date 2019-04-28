package com.sample.webtrack.jsonutils;

import com.google.gson.Gson;
import com.sample.webtrack.bean.Expenses;

public class JsonGenerator {

	public String  getExpenses(Expenses expenses) {
		Gson gson=new Gson();
		return gson.toJson(expenses);
	}
}
