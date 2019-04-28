package com.app3c.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.JsonReader;

import com.app3c.beans.ChannelsBean.Channel;


public class ChannelResponseParser{

	public static HashMap<String, ChannelsBean> channelsList = new HashMap<String, ChannelsBean>();
	public static ArrayList<String> chnList = new ArrayList<String>();
	
	public static ChannelsBean parse(String Resultstring) throws NullPointerException {
		
		byte[] stringByte = Resultstring.getBytes();
		InputStream stream = new ByteArrayInputStream(stringByte);
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		ChannelsBean channels = new ChannelsBean();
		try{
			jsonReader.beginObject();
			if(jsonReader.hasNext()){
				String token = jsonReader.nextName();
				if(token.equals("result"))
					channels.setResult(jsonReader.nextInt());
				token = jsonReader.nextName();
				if(token.equals("channels")){
					jsonReader.beginArray();
					channels.setChannels(readChannelArray(jsonReader));
					jsonReader.endArray();
				}
			}
			jsonReader.endObject();
			jsonReader.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return channels;
	}
	
	public static ArrayList<Channel> readChannelArray(JsonReader jsonReader) {
		ArrayList<Channel> channelList = new ArrayList<Channel>();
		try{
			while(jsonReader.hasNext()){
				channelList.add(parseChannel(jsonReader));
			}
				
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return channelList;
	}
	
	public static Channel parseChannel(JsonReader jsonReader) throws IOException {
		Channel channel = new Channel();
		jsonReader.beginObject();
		while(jsonReader.hasNext()){
			String token = jsonReader.nextName();
			if(token.equals("categoryId"))
				channel.setCategoryId(jsonReader.nextString());
			else if(token.equals("name"))
				channel.setName(jsonReader.nextString());
			else if(token.equals("channelId"))
				channel.setChannelId(jsonReader.nextString());
			else if(token.equals("imgurl"))
				channel.setImgurl(jsonReader.nextString());
			else if(token.equals("genre"))
				channel.setGenre(jsonReader.nextString());
			else if(token.equals("hd"))
				channel.setHd(jsonReader.nextString());
			else
				jsonReader.skipValue();
		}
		jsonReader.endObject();
		return channel;
	}
}
