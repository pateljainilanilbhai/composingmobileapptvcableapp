package com.mad.expenseproviderclient;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements LoaderCallbacks<Cursor>{
	Button addExpense;
	EditText expense, amount;
	TextView textView;
	ContentResolver contentResolver;
	
	public static final int QUERY_CODE=1;
	Uri allExpenseUri = Uri
			.parse("content://com.mad.expensetracker.expenseprovider/EXPENSES_TABLE");
	Uri sumOfExpenseUri = Uri
			.parse("content://com.mad.expensetracker.expenseprovider/SUM");
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addExpense = (Button) findViewById(R.id.button1);
		expense = (EditText) findViewById(R.id.editText1);
		amount = (EditText) findViewById(R.id.editText2);
		textView = (TextView) findViewById(R.id.showTotalExpense);
		contentResolver = getContentResolver();
		getLoaderManager().initLoader(QUERY_CODE, null, this);
		addExpense.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ContentValues values = new ContentValues();
				values.put("expense", expense.getText().toString());
				values.put("amount", amount.getText().toString());
				contentResolver.insert(allExpenseUri, values);
				getLoaderManager().restartLoader(QUERY_CODE, null, MainActivity.this);
			}
		});

	}

	

	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case QUERY_CODE:
			return new CursorLoader(MainActivity.this, sumOfExpenseUri, null,
					null, null, null);
		default:
			break;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		updateTextView(arg1);
	}

	private void updateTextView(Cursor cursor) {
		// TODO Auto-generated method stub
		while (cursor.moveToNext()) {
			textView.setText(cursor.getInt(0) + "");
		}
	}
	@SuppressLint("NewApi")
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}
}
