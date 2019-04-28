package com.mad.expensetracker;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ExpenseProvider extends ContentProvider {

	public static final String AUTHORITY = "com.mad.expensetracker.expenseprovider";
	public static final String PATH = "EXPENSES_TABLE";
	public static final Uri CONTENT_URI1 = Uri.parse("content://" + AUTHORITY
			+ "/" + PATH);
	public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY
			+ "/SUM");
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/" + PATH;
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/" + PATH;
	public static final int ALLEXPENSES = 1;
	public static final int SPECIFICEXPENSE = 2;
	public static final int SUMEXPENSES = 3;
	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		matcher.addURI(AUTHORITY, PATH, ALLEXPENSES); // paths matching
														// CONTENT_URI1
		matcher.addURI(AUTHORITY, PATH + "/#", SPECIFICEXPENSE); // paths
																	// matching
																	// CONTENT_URI1+"/id"
		matcher.addURI(AUTHORITY, "SUM", SUMEXPENSES); // paths matching
														// CONTENT_URI2
	}
	ExpenseDBAdapter db;

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		int uriType = matcher.match(arg0);
		switch (uriType) {
		case SUMEXPENSES:
			db.open();
			return db.getTotalExpenseCursor();
		default:
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		int uriType = matcher.match(arg0);
		long id = 0;
		switch (uriType) {
		case ALLEXPENSES:
			db.open();
			id = db.addExpense(arg1.get(ExpenseDBAdapter.COLUMN_EXPENSE)
					.toString(), Integer.parseInt(arg1.get(
					ExpenseDBAdapter.COLUMN_AMOUNT).toString()));
			db.close();
			break;

		default:
			throw new UnsupportedOperationException();
		}
		getContext().getContentResolver().notifyChange(arg0, null);
		return Uri.parse(PATH + "/" + id);
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3)
			throws UnsupportedOperationException {
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2)
			throws UnsupportedOperationException {
		return 0;
	}

	@Override
	public boolean onCreate() {
		db = new ExpenseDBAdapter(getContext());
		return true;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		switch (matcher.match(arg0)) {
		case ALLEXPENSES:
			return CONTENT_TYPE;
		case SPECIFICEXPENSE:
			return CONTENT_ITEM_TYPE;
		case SUMEXPENSES:
			return CONTENT_ITEM_TYPE;
		default:
			return null;
		}
	}

}
