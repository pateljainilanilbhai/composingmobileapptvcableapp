package com.app3c;

import com.app3c.ChannelsListFragment.OnTVGuideChannelSelectedListener;
import com.app3c.webserviceconsumer.AppConfig;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class TVGuide extends Activity implements
		OnTVGuideChannelSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvguide);

		if (getFragmentManager().findFragmentByTag("tag2") != null) {
			getFragmentManager().beginTransaction()
					.remove(getFragmentManager().findFragmentByTag("tag2"))
					.commit();
		}
		if (getFragmentManager().findFragmentByTag("tag1") != null) {
			getFragmentManager().beginTransaction()
					.remove(getFragmentManager().findFragmentByTag("tag1"))
					.commit();
		}

		if (findViewById(R.id.container) != null) {
			ChannelsListFragment channelsFrag = new ChannelsListFragment();
			getFragmentManager().beginTransaction()
					.replace(R.id.container, channelsFrag, "tag1").commit();
		}
		if (findViewById(R.id.frTVGuideShow) != null) {
			ShowsListFragment showsListFragment = new ShowsListFragment();
			getFragmentManager().beginTransaction()
					.replace(R.id.frTVGuideShow, showsListFragment, "tag2")
					.commit();
		}
	}

	@Override
	public void onTVGuideChannelSelected(String channel) {
		// TODO Auto-generated method stub

		if (findViewById(R.id.frTVGuideShow) == null) {
			// in portrait mode
			ShowsListFragment fragment = new ShowsListFragment();
			Bundle args = new Bundle();
			args.putString(ShowsListFragment.CHANNEL_ID, channel);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "tag2").commit();
		} else {
			ShowsListFragment fragment = new ShowsListFragment();
			Bundle args = new Bundle();
			args.putString(ShowsListFragment.CHANNEL_ID, channel);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction()
					.replace(R.id.frTVGuideShow, fragment, "tag2").commit();
		}
	}

	public void playMedia(View v) {

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		DialogFragment newFragment = MediaFragment.newInstance("sample video");
		newFragment.show(ft, "dialog");

	}

	public static class MediaFragment extends DialogFragment {
		String showName = null;
		VideoView videoView;

		static MediaFragment newInstance(String name) {
			MediaFragment f = new MediaFragment();

			// Supply num input as an argument.
			Bundle args = new Bundle();
			args.putString("show_name", name);
			f.setArguments(args);

			return f;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			showName = getArguments().getString("show_name");
			setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.mediafragment_layout, container,
					false);
			videoView = (VideoView) v.findViewById(R.id.videoView1);
			MediaController mController = new MediaController(getActivity());
			videoView.setMediaController(mController);
			videoView.setVideoURI(Uri.parse(AppConfig.ServerAddress
					+ AppConfig.MediaRelativeAddress));
			videoView.start();
			mController.show();
			return v;
		}
	}
}
