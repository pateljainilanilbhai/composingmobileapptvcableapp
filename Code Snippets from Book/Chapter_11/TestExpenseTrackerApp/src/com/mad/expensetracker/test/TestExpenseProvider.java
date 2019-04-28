package com.mad.expensetracker.test;

import com.mad.expensetracker.ExpenseProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

public class TestExpenseProvider extends ProviderTestCase2<ExpenseProvider> {

	private MockContentResolver contentResolver;
	private Uri sumuri,expenseuri;
	
	public TestExpenseProvider()
	{
		super(ExpenseProvider.class, "com.mad.expensetracker.expenseprovider");
	}
	protected void setUp() throws Exception {
		super.setUp();
		contentResolver=this.getMockContentResolver();
		sumuri=Uri.parse("content://com.mad.expensetracker.expenseprovider/SUM");
		expenseuri=Uri.parse("content://com.mad.expensetracker.expenseprovider/EXPENSES_TABLE");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		contentResolver=null;
	}
	
	public void testPreConditions()
	{
		String MIMEType=contentResolver.getType(sumuri);
		assertEquals("vnd.android.cursor.item/EXPENSES_TABLE", MIMEType);
		Cursor cursor=contentResolver.query(sumuri, null, null, null, null);
		assertNotNull(cursor);
		assertEquals(1, cursor.getCount());
		assertEquals(1, cursor.getColumnCount());
	}
	
	public void testQueryMethod() {
		
		String [] expenses=new String []{"Clothes","Groceries","Snacks"};
		String [] amounts=new String []{"1000","2000","100"};
		
		for ( int i=0;i<3;i++)
		{
			ContentValues contentValues=new ContentValues();
			contentValues.put("expense", expenses[i]);
			contentValues.put("amount", amounts[i]);
			contentResolver.insert(expenseuri, contentValues);
		}
		Cursor cursor=contentResolver.query(sumuri, null, null, null, null);
		cursor.moveToNext();
		assertEquals(3100, cursor.getInt(0));
	}
}
