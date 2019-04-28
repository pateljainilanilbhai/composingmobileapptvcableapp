package com.action;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class ActionBarPOCActivity extends Activity implements TabListener {
	private CountingFragment frag2;
	private CountingFragment frag1;
	private CountingFragment frag3;
	private int currentFragId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		frag1 = CountingFragment.newInstance(100);
		frag2 = CountingFragment.newInstance(200);
		frag3 = CountingFragment.newInstance(300);
		Tab tab1 = actionBar.newTab().setText("Tab 1").setTabListener(this);
		Tab tab2 = actionBar.newTab().setText("Tab 2").setTabListener(this);
		Tab tab3 = actionBar.newTab().setText("Tab 3").setTabListener(this);
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbarmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i("actionbar", "onOptionsItemSelected ");
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, ActionBarPOCActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			Toast.makeText(this, "Selected item = " + item.getTitle(), 3000)
					.show();
			return super.onOptionsItemSelected(item);
		}
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		//Log.i("actionbar", "onTabSelected ");
		int position = tab.getPosition();
		Fragment fragR;
		switch (position) {
		case 0:
			fragR = frag1;
			break;
		case 1:
			fragR = frag2;
			break;
		case 2:
			fragR = frag3;
			break;
		default:
			fragR = null;
			break;
		}
		ft.replace(R.id.fragmentContent, fragR);
	}
	

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	public static class CountingFragment extends Fragment {

		static int mNum = 11000;
		private ActionMode mActionMode;

		static CountingFragment newInstance(int num) {
			Log.i("actionbar", "Counting Fragment New Instance ");
			CountingFragment f = new CountingFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putInt("num", num);
			f.setArguments(args);

			return f;
		}

		private TextView tv;

		/**
		 * When creating, retrieve this instance's number from its arguments.
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Log.i("actionbar", "Counting Fragment onCreate ");
			mNum = getArguments() != null ? getArguments().getInt("num") : 1;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.i("actionbar", "Counting Fragment onCreateView ");
			View v = inflater.inflate(R.layout.hello_world, container, false);
			tv = (TextView) v.findViewById(R.id.text);
			tv.setText("Fragment #" + mNum);
			tv.setBackgroundDrawable(getResources().getDrawable(
					android.R.drawable.gallery_thumb));
			tv.setOnLongClickListener(new View.OnLongClickListener() {

				// Called when the user long-clicks on someView
				public boolean onLongClick(View view) {
					if (mActionMode != null) {
						return false;
					}

					// Start the CAB using the ActionMode.Callback defined above
					mActionMode = getActivity().startActionMode(
							mActionModeCallback);
					view.setSelected(true);
					return true;
				}
			});
			return v;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			Log.i("actionbar", "Counting Fragment onActivityCreated ");
			
		}

		private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

			// Called when the action mode is created; startActionMode() was
			// called
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				Log.i("actionbar", "ActionMode.CallBack onCreateActionMode");
				// Inflate a menu resource providing context menu items
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.context_menu, menu);
				return true;
			}

			// Called each time the action mode is shown. Always called after
			// onCreateActionMode, but
			// may be called multiple times if the mode is invalidated.
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				Log.i("actionbar", "ActionMode.CallBack onPrepareActionMode");
				return false; // Return false if nothing is done
			}

			// Called when the user selects a contextual menu item
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				Log.i("actionbar", "ActionMode.CallBack onActionItemClicked");
				switch (item.getItemId()) {
				case R.id.item1:
					Toast.makeText(getActivity(), "Clicked context item 1",
							3000).show();
					// shareCurrentItem();
					mode.finish(); // Action picked, so close the CAB
					return true;
				case R.id.item2:
					Toast.makeText(getActivity(), "Clicked context item 2",
							3000).show();
					mode.finish();
				default:
					return false;
				}
			}

			// Called when the user exits the action mode
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				Log.i("actionbar", "ActionMode.CallBack onDestroyActionMode");
				mActionMode = null;
			}
		};
	}
}