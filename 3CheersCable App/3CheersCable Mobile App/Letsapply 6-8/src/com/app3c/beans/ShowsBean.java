package com.app3c.beans;

import java.util.ArrayList;

public class ShowsBean {
	int result;
	ArrayList<Show> shows;
	
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public ArrayList<Show> getShows() {
		return shows;
	}

	public void setShows(ArrayList<Show> shows) {
		this.shows = shows;
	}


	public static class Show{
		private String showId, channelId, showName, showTiming;
		private boolean favourite;
		
		public Show(String id, String cid, String name, String time, boolean fav ) {
			this.showId = id;
			this.channelId = cid;
			this.showName = name;
			this.showTiming = time;
			this.favourite = fav;
		}
		
		public Show() {
			// TODO Auto-generated constructor stub
		}

		public String getShowId() {
			return showId;
		}
		public void setShowId(String showId) {
			this.showId = showId;
		}
		public String getChannelId() {
			return channelId;
		}
		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}
		public String getShowName() {
			return showName;
		}
		public void setShowName(String showName) {
			this.showName = showName;
		}
		public String getShowTiming() {
			return showTiming;
		}
		public void setShowTiming(String showTiming) {
			this.showTiming = showTiming;
		}
		public boolean isFavourite() {
			return favourite;
		}
		public void setFavourite(boolean favourite) {
			this.favourite = favourite;
		}
		
	}

}
