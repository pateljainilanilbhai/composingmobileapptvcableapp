package com.mad.expensetracker.withoutprovider;

import java.util.ArrayList;

import com.mad.expensetracker.withoutprovider.R;
import com.mad.expensetracker.database.ExpenseDBAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity implements OnItemClickListener,
		OnItemLongClickListener {
	ExpenseDBAdapter expensesDatabase;
	TextView showTotalExpense;
	Spinner filterByAmount;
	ListView showExpenses;
	ArrayList<Integer> expenseIds;
	ArrayList<String> expenseText;
	ArrayList<Integer> expenseAmount;
	Cursor getExpenses;
	String[] filterAmounts = { "All", " <=100 "," <=500 "," <=1000 ", " <=5000" };
	ListAdapter expensesAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		expensesDatabase = new ExpenseDBAdapter(getApplicationContext());
		expensesDatabase.open();
		showTotalExpense = (TextView) findViewById(R.id.expenseIncurredDisplay);
		filterByAmount = (Spinner) findViewById(R.id.filterByAmountSpinner);
		filterByAmount.setAdapter(new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, filterAmounts));

		getExpenses = expensesDatabase.getAllExpenses();
		showExpenses = (ListView) findViewById(R.id.expensesList);
		showExpenses.setOnItemClickListener(this);
		showExpenses.setOnItemLongClickListener(this);
		refreshViews();
		filterByAmount.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 1:
					getExpenses = expensesDatabase.getExpensesWithinRange(100);
					refreshViews();
					break;
				case 2:
					getExpenses = expensesDatabase.getExpensesWithinRange(500);
					refreshViews();
					break;
				case 3: getExpenses=expensesDatabase.getExpensesWithinRange(1000);
						refreshViews();
						break;
				case 4: getExpenses=expensesDatabase.getExpensesWithinRange(5000);
						refreshViews();
						break;
				default:
					getExpenses = expensesDatabase.getAllExpenses();
					refreshViews();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	void refreshViews() {
		showTotalExpense.setText(expensesDatabase.getTotalExpense() + "");

		expenseIds = new ArrayList<Integer>();
		expenseText = new ArrayList<String>();
		expenseAmount=new ArrayList<Integer>();
		while (getExpenses.moveToNext()) {
			expenseIds.add(getExpenses.getInt(0));
			expenseText.add(getExpenses.getString(1));
			expenseAmount.add(getExpenses.getInt(2));
		}
		expensesAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, expenseText);
		showExpenses.setAdapter(expensesAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.addExpense:
			View dialog = getLayoutInflater().inflate(
					R.layout.addexpensedialog, null);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(dialog);
			final EditText expenseEntered = (EditText) dialog
					.findViewById(R.id.editText1);
			final EditText amountEntered = (EditText) dialog
					.findViewById(R.id.editText2);
			builder.setCancelable(false);
			builder.setPositiveButton("Add", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					expensesDatabase.addExpense(expenseEntered.getText()
							.toString(), Integer.parseInt(amountEntered
							.getText().toString()));
					getExpenses = expensesDatabase.getAllExpenses();
					refreshViews();
				}
			});
			builder.setNegativeButton("Cancel", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.cancel();
				}
			});
			AlertDialog alertdialog = builder.create();
			alertdialog.show();

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		AlertDialog.Builder deleteDialogBuilder = new Builder(this);
		deleteDialogBuilder.setTitle("Delete Item?");
		deleteDialogBuilder
				.setMessage("Do you want to delete the selected item?");
		deleteDialogBuilder.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				expensesDatabase.deleteExpense(expenseIds.get(arg2));
				getExpenses = expensesDatabase.getAllExpenses();
				refreshViews();
				
			}
		});
		deleteDialogBuilder.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
		AlertDialog deleteDialog=deleteDialogBuilder.create();
		deleteDialog.show();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		AlertDialog.Builder updateDialogBuilder = new Builder(this);
		updateDialogBuilder.setTitle("Update Item?");
		updateDialogBuilder
				.setMessage("Do you want to update the selected item?");
		updateDialogBuilder.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				View dialog = getLayoutInflater().inflate(
						R.layout.addexpensedialog, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setView(dialog);
				final EditText expenseEntered = (EditText) dialog
						.findViewById(R.id.editText1);
				final EditText amountEntered = (EditText) dialog
						.findViewById(R.id.editText2);
				builder.setCancelable(false);
				expenseEntered.setText(expenseText.get(arg2));
				amountEntered.setText(expenseAmount.get(arg2)+"");
				builder.setPositiveButton("Update", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						expensesDatabase.updateExpense(expenseIds.get(arg2), expenseEntered.getText().toString(), Integer.parseInt(amountEntered.getText().toString()));
						getExpenses = expensesDatabase.getAllExpenses();
						refreshViews();
						
					}
				});
				builder.setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.cancel();
					}
				});
				AlertDialog alertdialog = builder.create();
				alertdialog.show();
			}
		});
		updateDialogBuilder.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
		AlertDialog updateDialog=updateDialogBuilder.create();
		updateDialog.show();
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		expensesDatabase.close();
	}

}
