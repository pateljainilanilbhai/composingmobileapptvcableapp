package com.mad.contactcontentproviderdemo;

import java.util.ArrayList;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {
	ArrayList<String> contacts = new ArrayList<String>();
	ListView displayContactsListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayContactsListView = (ListView) findViewById(R.id.listView1);
		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		if (arg0 == 0) {
			return new CursorLoader(MainActivity.this,
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					null, null, null);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		int idContactName = arg1
				.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
		while (arg1.moveToNext()) {
			contacts.add(arg1.getString(idContactName));
		}
		ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, contacts);
		displayContactsListView.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}

}
