package com.mad.expensetrackerwebclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mad.beans.Expense;

import com.mad.utils.ExpenseParser;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity implements OnItemClickListener,
		OnItemLongClickListener {
	// ExpenseDBAdapter expensesDatabase;
	TextView showTotalExpense;
	Spinner filterByAmount;
	ListView showExpenses;
	ArrayList<Integer> expenseIds;
	ArrayList<String> expenseText;
	ArrayList<Float> expenseAmount;
	String[] filterAmounts = { "All", " <=100 ", " <=500 ", " <=1000 ",
			" <=5000" };
	ListAdapter expensesAdapter;
	String[] operationCodes = { "1", "2", "3", "4" }; // Read , add, update,
	boolean filtered = false; // delete codes
	ArrayList<Integer> filterExpenseIds ;
	ArrayList<String> filterExpenseText ;
	ArrayList<Float> filterExpenseAmount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		showTotalExpense = (TextView) findViewById(R.id.expenseIncurredDisplay);
		filterByAmount = (Spinner) findViewById(R.id.filterByAmountSpinner);
		filterByAmount.setAdapter(new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.simple_spinner_dropdown_item, filterAmounts));

		showExpenses = (ListView) findViewById(R.id.expensesList);
		showExpenses.setOnItemClickListener(this);
		showExpenses.setOnItemLongClickListener(this);
		if (checkNetworkAccess()) {
			readExpenses();
		}
		filterByAmount.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				System.out.println(arg2);
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					filterExpenses("0");
					break;
				case 1:
					filterExpenses("100");
					break;
				case 2:
					filterExpenses("500");
					break;
				case 3:
					filterExpenses("1000");
					break;
				default:
					filterExpenses("5000");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				filtered = false;
			}
		});
	}

	void populateViews(List<Expense> expenses) {
		float totalAmount = 0;
		expenseIds = new ArrayList<Integer>();
		expenseText = new ArrayList<String>();
		expenseAmount = new ArrayList<Float>();
		for (Expense expense : expenses) {
			expenseIds.add(expense.getExpenseId());
			expenseText.add(expense.getExpenseType());
			expenseAmount.add(expense.getExpenseAmount());
			totalAmount += expense.getExpenseAmount();
		}
		expensesAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, expenseText);
		showExpenses.setAdapter(expensesAdapter);
		showTotalExpense.setText(totalAmount + "");
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
					addExpense(expenseEntered.getText().toString(),
							amountEntered.getText().toString());

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
				if(filtered)
				{
					deleteExpense(filterExpenseIds.get(arg2)+"");
				}
				else
				{
					deleteExpense(expenseIds.get(arg2)+"");
				}

			}
		});
		deleteDialogBuilder.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				arg0.cancel();
			}
		});
		AlertDialog deleteDialog = deleteDialogBuilder.create();
		deleteDialog.show();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
			final int arg2, long arg3) {
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
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setView(dialog);
				final EditText expenseEntered = (EditText) dialog
						.findViewById(R.id.editText1);
				final EditText amountEntered = (EditText) dialog
						.findViewById(R.id.editText2);
				builder.setCancelable(false);
				expenseEntered.setText(expenseText.get(arg2));
				amountEntered.setText(expenseAmount.get(arg2) + "");
				builder.setPositiveButton("Update", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						if(filtered)
						{
							updateExpense(filterExpenseIds.get(arg2)+"", expenseEntered.getText().toString(), amountEntered.getText().toString());
						}
						else
						{
							updateExpense(expenseIds.get(arg2)+"", expenseEntered.getText().toString(), amountEntered.getText().toString());
						}
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
		AlertDialog updateDialog = updateDialogBuilder.create();
		updateDialog.show();
		return true;
	}

	private boolean checkNetworkAccess() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		} else {
			Toast.makeText(MainActivity.this,
					"No network access, network resource not accessible",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	}

	private void addExpense(String expense, String amount) {
		// TODO Auto-generated method stub
		String[] data = { "2", expense, amount };
		new ExpenseConsumerTask().execute(data);
	}

	private void deleteExpense(String id) {
		// TODO Auto-generated method stub
		String[] data = { "4", id };
		new ExpenseConsumerTask().execute(data);
	}

	private void updateExpense(String id, String expense, String amount) {
		// TODO Auto-generated method stub
		String[] data = { "3", id, expense, amount };
		new ExpenseConsumerTask().execute(data);
	}

	private void readExpenses() {
		// TODO Auto-generated method stub
		String[] data = { "1" };
		new ExpenseConsumerTask().execute(data);
	}

	private void filterExpenses(String upperLimit) {

		filterExpenseIds = new ArrayList<Integer>();
		filterExpenseText = new ArrayList<String>();
		filterExpenseAmount = new ArrayList<Float>();
		if(expenseAmount!=null){
		if (!upperLimit.equals("0")) {
			filtered = true;
			for (int i = 0; i < expenseAmount.size(); i++) {
				if (expenseAmount.get(i) <= Float.parseFloat(upperLimit)) {
					filterExpenseIds.add(expenseIds.get(i));
					filterExpenseText.add(expenseText.get(i));
					filterExpenseAmount.add(expenseAmount.get(i));
				}
			}
			expensesAdapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, filterExpenseText);
			showExpenses.setAdapter(expensesAdapter);
		}
		else {
			filtered = false;
			expensesAdapter = new ArrayAdapter<String>(getApplicationContext(),
					android.R.layout.simple_list_item_1, expenseText);
			showExpenses.setAdapter(expensesAdapter);
		}
	}
	}

	class ExpenseConsumerTask extends AsyncTask<String, Void, String> {
		String operationCode;

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String data[] = arg0;
			operationCode = data[0];
			String response = null;
			if (operationCode.equalsIgnoreCase("1")) {
				// read the data
				HttpURLConnection connection = null;
				try {
					URL url = new URL(
							"http://10.0.2.2:8080/ExpenseTrackerWebService/FetchExpensesServlet");
					connection = (HttpURLConnection) url.openConnection();
					connection.setReadTimeout(2000);
					connection.setConnectTimeout(4000);
					connection.setRequestMethod("GET");
					connection.setDoInput(true);
					connection.connect();
					int responseCode = connection.getResponseCode();

					if (responseCode == 200) {
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));
						StringBuilder builder = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							builder.append(line);
						}

						response = builder.toString();
					} else {
						response = "Response was not successful";
					}

				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					connection.disconnect();
				}

			} else if (operationCode.equalsIgnoreCase("2")) {
				String expenseType = data[1];
				String expenseAmount = data[2];
				HttpURLConnection connection = null;
				try {
					String urlParams = "expenseType=" + expenseType
							+ "&expenseAmount=" + expenseAmount;
					URL url = new URL(
							"http://10.0.2.2:8080/ExpenseTrackerWebService/InsertExpenseServlet");

					connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setInstanceFollowRedirects(false);
					connection.setRequestMethod("PUT");
					connection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					connection.setRequestProperty("charset", "utf-8");
					connection.setRequestProperty("Content-Length", ""
							+ Integer.toString(urlParams.getBytes().length));
					connection.setUseCaches(false);
					connection.setReadTimeout(2000);
					connection.setConnectTimeout(4000);

					DataOutputStream outputStream = new DataOutputStream(
							connection.getOutputStream());
					outputStream.writeBytes(urlParams);
					outputStream.flush();
					// connection.connect();
					int responseCode = connection.getResponseCode();
					if (responseCode == 200) {
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));
						StringBuilder builder = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							builder.append(line);
						}

						response = builder.toString();
					} else {
						response = "Response was not successful";
					}

				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					connection.disconnect();
				}
			} else if (operationCode.equalsIgnoreCase("3")) {
				String expenseId = data[1];
				String expenseType = data[2];
				String expenseAmount = data[3];
				HttpURLConnection connection = null;
				try {
					String urlParams = "expenseId="+expenseId+"&expenseType=" + expenseType
							+ "&expenseAmount=" + expenseAmount;
					URL url = new URL(
							"http://10.0.2.2:8080/ExpenseTrackerWebService/UpdateExpenseServlet");

					connection = (HttpURLConnection) url.openConnection();
					connection.setDoOutput(true);
					connection.setDoInput(true);
					connection.setInstanceFollowRedirects(false);
					connection.setRequestMethod("POST");
					connection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					connection.setRequestProperty("charset", "utf-8");
					connection.setRequestProperty("Content-Length", ""
							+ Integer.toString(urlParams.getBytes().length));
					connection.setUseCaches(false);
					connection.setReadTimeout(2000);
					connection.setConnectTimeout(4000);
					
					DataOutputStream outputStream = new DataOutputStream(
							connection.getOutputStream());
					outputStream.writeBytes(urlParams);
					outputStream.flush();
					// connection.connect();
					int responseCode = connection.getResponseCode();
					if (responseCode == 200) {
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));
						StringBuilder builder = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							builder.append(line);
						}

						response = builder.toString();
					} else {
						response = "Response was not successful";
					}

				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					connection.disconnect();
				}
				
			} else if (operationCode.equalsIgnoreCase("4")) {
				String expenseId = data[1];
				HttpURLConnection connection = null;
				try {
					String urlParams = "expenseId="+expenseId;
					URL url = new URL(
							"http://10.0.2.2:8080/ExpenseTrackerWebService/DeleteExpenseServlet");

					connection = (HttpURLConnection) url.openConnection();
					connection.addRequestProperty("expenseId", expenseId);
					connection.setDoInput(true);
					connection.setInstanceFollowRedirects(false);
					connection.setRequestMethod("DELETE");
					connection.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					connection.setRequestProperty("charset", "utf-8");
					connection.setRequestProperty("Content-Length", ""
							+ Integer.toString(urlParams.getBytes().length));
					connection.setUseCaches(false);
					connection.setReadTimeout(2000);
					connection.setConnectTimeout(4000);

					/*DataOutputStream outputStream = new DataOutputStream(
							connection.getOutputStream());
					outputStream.writeBytes(urlParams);
					outputStream.flush();*/
					// connection.connect();
					int responseCode = connection.getResponseCode();
					if (responseCode == 200) {
						InputStream inputStream = connection.getInputStream();
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(inputStream));
						StringBuilder builder = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							builder.append(line);
						}

						response = builder.toString();
					} else {
						response = "Response was not successful";
					}

				} catch (Exception exception) {
					exception.printStackTrace();
				} finally {
					connection.disconnect();
				}
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//System.out.println(result);
			List<Expense> expenses = new ArrayList<Expense>();
			if (operationCode.equalsIgnoreCase("1")) {
				expenses = new ExpenseParser().readJsonStream(result);
				populateViews(expenses);
			} else if (operationCode.equalsIgnoreCase("2")) {
				readExpenses();
			}
			else if (operationCode.equalsIgnoreCase("3")) {
				readExpenses();
			} else if (operationCode.equalsIgnoreCase("4")) {
				readExpenses();
			}

		}

	}

}
