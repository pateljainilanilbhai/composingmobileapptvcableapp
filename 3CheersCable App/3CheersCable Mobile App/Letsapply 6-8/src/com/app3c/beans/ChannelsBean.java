package com.app3c.beans;

import java.util.ArrayList;

public class ChannelsBean{
	int result;
	ArrayList<Channel> channels;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public ArrayList<Channel> getChannels() {
		return channels;
	}
	public void setChannels(ArrayList<Channel> channels) {
		this.channels = channels;
	}
	public static class Channel {
		private String channelId, name, categoryId, imgurl, genre, hd;

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}

		public String getImgurl() {
			return imgurl;
		}

		public void setImgurl(String imgurl) {
			this.imgurl = imgurl;
		}

		public String getGenre() {
			return genre;
		}

		public void setGenre(String genre) {
			this.genre = genre;
		}

		public String getHd() {
			return hd;
		}

		public void setHd(String hd) {
			this.hd = hd;
		}				
	}
}

