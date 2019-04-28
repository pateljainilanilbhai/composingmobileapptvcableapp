package com.sample.webtrack.services;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.sample.webtrack.bean.Expense;
import com.sample.webtrack.bean.Expenses;
import com.sample.webtrack.database.ExpenseDatabaseAdapter;

public class ExpenseTrackerServices {

	ExpenseDatabaseAdapter databaseAdapter;
	
	public ExpenseTrackerServices() {
		// TODO Auto-generated constructor stub
		
		databaseAdapter=new ExpenseDatabaseAdapter();
	}
	public Expenses fetchAllExpenses() {
		
		Expenses expenses=new Expenses();
		ResultSet resultSet=databaseAdapter.getExpensesResultSet();
		try
		{
			while(resultSet.next())
			{
				Expense expense=new Expense();
				expense.setExpenseId(resultSet.getInt("expenseId"));
				expense.setExpenseType(resultSet.getString("expenseType"));
				expense.setExpenseAmount(resultSet.getFloat("expenseAmount"));
				expenses.addExpensetoList(expense);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return expenses;
	}
	
	public int insertExpense(String expenseType, float expenseAmount ) {
		
		return databaseAdapter.insertExpenseInDatabase(expenseType, expenseAmount);
	}
	
	public int deleteExpense(int expenseId) {
		return databaseAdapter.deleteExpenseFromDatabase(expenseId);
	}
	
	public boolean updateExpense(int expenseId, String expenseType,float expenseAmount)
	{
		return databaseAdapter.updateExpense(expenseId, expenseType, expenseAmount)>0;
	}
}
