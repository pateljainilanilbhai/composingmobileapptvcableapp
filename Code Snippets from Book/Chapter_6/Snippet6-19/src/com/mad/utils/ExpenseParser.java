package com.mad.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.util.JsonReader;

import com.mad.beans.Expense;


@SuppressLint("NewApi")
public class ExpenseParser {

	public List<Expense> readJsonStream(String jsonExpense)
	{
		List<Expense> expenses=new ArrayList<Expense>();
		byte[] stringBytes = jsonExpense.getBytes();
		InputStream reader = new ByteArrayInputStream(stringBytes);
		JsonReader jsonReader = new JsonReader(new InputStreamReader(reader));
		try {
			
			jsonReader.beginObject();
			if(jsonReader.hasNext())
			{
				if(jsonReader.nextName().equals("expenseList"))
				{
					jsonReader.beginArray();
					expenses=readExpensesArray(jsonReader);
					jsonReader.endArray();
				}
			}
			jsonReader.endObject();
			jsonReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return expenses;
		
		
	}
	@SuppressLint("NewApi")
	public Expense parseExpense(JsonReader jsonReader) throws IOException {
		Expense expense = new Expense();
		jsonReader.beginObject();
		while (jsonReader.hasNext()) {
			String token = jsonReader.nextName();
			if (token.equals("expenseId")) {
				expense.setExpenseId(jsonReader.nextInt());
			} else if (token.equals("expenseType")) {
				expense.setExpenseType(jsonReader.nextString());

			}

			else if (token.equals("expenseAmount")) {
				expense.setExpenseAmount((float) jsonReader.nextDouble());
			} 
			else {
				jsonReader.skipValue();
			}
		}

		jsonReader.endObject();
		

		return expense;
	}
	public List<Expense> readExpensesArray(JsonReader jsonReader) {
		// TODO Auto-generated method stub
		List<Expense> expenses=new  ArrayList<Expense>();
		try {
			
		while(jsonReader.hasNext())
		{
			expenses.add(parseExpense(jsonReader));
		}
		
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return expenses;
	}
}
