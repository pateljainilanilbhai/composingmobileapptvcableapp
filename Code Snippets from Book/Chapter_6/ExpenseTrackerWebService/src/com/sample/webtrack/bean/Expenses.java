package com.sample.webtrack.bean;

import java.util.ArrayList;

public class Expenses {

	private ArrayList<Expense> expenseList;

	public Expenses() {
		// TODO Auto-generated constructor stub
		expenseList=new ArrayList<Expense>();
	}
	public ArrayList<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(ArrayList<Expense> expenseList) {
		this.expenseList = expenseList;
	}
	
	public void addExpensetoList(Expense expense)
	{
		expenseList.add(expense);
	}
}
