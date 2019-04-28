package com.mad.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ExpenseDBAdapter {
	public static final String DB_NAME="Expense_Database";
	public static final String DB_TABLE="Expenses_Table";
	public static final int DB_VERSION=1;
	
	public static final String KEY_ID="_id";
	
	public static final String COLUMN_EXPENSE="expense";
	public static final String COLUMN_AMOUNT="amount";
	
	
	private static final String DB_CREATE="create table "+DB_TABLE+" ("+KEY_ID+" integer primary key "+
		"autoincrement , "+COLUMN_EXPENSE+" text not null, "+COLUMN_AMOUNT+" integer not null);";
	
	
	private SQLiteDatabase  expenseDatabase;
	private final Context context;
	private MyDBHelper helper;
	
	public ExpenseDBAdapter(Context context)
	{
		
		this.context=context;
		helper=new MyDBHelper(context, DB_NAME, null, DB_VERSION);
		
	}
	public ExpenseDBAdapter open()
	{
		
		expenseDatabase=helper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		expenseDatabase.close();
	}
	public Cursor getAllExpenses()
	{
		return expenseDatabase.query(DB_TABLE, null, null, null, null, null, null);
	}
	public Cursor getExpensesWithinRange(int amount)
	{
		return expenseDatabase.query(DB_TABLE, null, COLUMN_AMOUNT+" <= "+amount, null, null, null, null);
		
	}
	public long addExpense(String expense,int amount) {
		
		ContentValues contentValues=new ContentValues();
		contentValues.put(COLUMN_EXPENSE, expense);
		contentValues.put(COLUMN_AMOUNT, amount);
		return expenseDatabase.insert(DB_TABLE, null, contentValues);
	}
	public boolean deleteExpense(long rowIndex) {
		return expenseDatabase.delete(DB_TABLE, KEY_ID+" = "+rowIndex, null)>0;
	}
	
	
	public int updateExpense(long rowIndex,String expense,int amount) {
		
		ContentValues updateValues=new ContentValues();
		updateValues.put(COLUMN_EXPENSE, expense);
		updateValues.put(COLUMN_AMOUNT, amount);
		return expenseDatabase.update(DB_TABLE, updateValues, KEY_ID+" = "+rowIndex, null);
	}
	public int getTotalExpense(){
		Cursor expensesSum=expenseDatabase.rawQuery("select sum(amount) from Expenses_Table", null);
		if(expensesSum!=null){
			expensesSum.moveToNext();
			return expensesSum.getInt(0);
		}
		else
			return 0;
	}
	public Cursor getTotalExpenseCursor()
	{
		return expenseDatabase.rawQuery("select sum(amount) from Expenses_Table", null);
	}
	private static class MyDBHelper extends SQLiteOpenHelper
	{
		public MyDBHelper(Context context,String name,CursorFactory cursorFactory, int version)
		{
			super(context, name, cursorFactory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Updation", "Data base version is being updated");
			db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
			onCreate(db);
		}
		
		
	}

}
