package com.app3c;

import com.app3c.ChannelsListFragment.OnTVGuideChannelSelectedListener;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;


@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
public class TVGuide extends Activity implements OnTVGuideChannelSelectedListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvguide);
		
		if(getFragmentManager().findFragmentByTag("tag2")!=null)
		{
			getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("tag2")).commit();
		}
		if(getFragmentManager().findFragmentByTag("tag1")!=null)
		{
			getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag("tag1")).commit();
		}
		
		if(findViewById(R.id.container) != null){			
			ChannelsListFragment channelsFrag = new ChannelsListFragment();
			getFragmentManager().beginTransaction().replace(R.id.container, channelsFrag,"tag1").commit();
		}
		if(findViewById(R.id.frTVGuideShow)!=null){
			ShowsListFragment showsListFragment=new ShowsListFragment();
			getFragmentManager().beginTransaction().replace(R.id.frTVGuideShow, showsListFragment,"tag2").commit();
		}
	}

	@Override
	public void onTVGuideChannelSelected(String channel) {
		// TODO Auto-generated method stub
				
		if(findViewById(R.id.frTVGuideShow)==null){
			//in portrait mode
			ShowsListFragment fragment = new ShowsListFragment(); 
			Bundle args = new Bundle();
			args.putString(ShowsListFragment.CHANNEL_ID, channel);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction().replace(R.id.container, fragment,"tag2").commit();
		}
		else{
			ShowsListFragment fragment = new ShowsListFragment(); 
			Bundle args = new Bundle();
			args.putString(ShowsListFragment.CHANNEL_ID, channel);
			fragment.setArguments(args);
			getFragmentManager().beginTransaction().replace(R.id.frTVGuideShow, fragment,"tag2").commit();
		}
	}
}
