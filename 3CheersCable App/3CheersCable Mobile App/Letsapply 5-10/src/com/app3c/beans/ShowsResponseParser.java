package com.app3c.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.app3c.beans.ShowsBean.Show;

import android.util.JsonReader;


public class ShowsResponseParser {

	public static ShowsBean parse(String Resultstring) throws NullPointerException {
		
		byte[] stringByte = Resultstring.getBytes();
		InputStream stream = new ByteArrayInputStream(stringByte);
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		ShowsBean shows = new ShowsBean();
		try{
			jsonReader.beginObject();
			if(jsonReader.hasNext()){
				String token = jsonReader.nextName();
				if(token.equals("result"))
					shows.setResult(jsonReader.nextInt());
				token = jsonReader.nextName();
				if(token.equals("shows")){
					jsonReader.beginArray();
					shows.setShows(readShowArray(jsonReader));
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
		return shows;
	}
	
	public static ArrayList<Show> readShowArray(JsonReader jsonReader) {
		ArrayList<Show> showsList = new ArrayList<Show>();
		try{
			while(jsonReader.hasNext()){
				showsList.add(parseCategory(jsonReader));
			}
				
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return showsList;
	}
	
	public static Show parseCategory(JsonReader jsonReader) throws IOException {
		Show show = new Show();
		jsonReader.beginObject();
		while(jsonReader.hasNext()){
			String token = jsonReader.nextName();
			if(token.equals("showId"))
				show.setShowId(jsonReader.nextString());
			else if(token.equals("channelId"))
				show.setChannelId(jsonReader.nextString());
			else if(token.equals("showName"))
				show.setShowName(jsonReader.nextString());
			else if(token.equals("showTiming"))
				show.setShowTiming(jsonReader.nextString());
			else if(token.equals("favourite"))
				show.setFavourite(jsonReader.nextBoolean());
			else
				jsonReader.skipValue();
		}
		jsonReader.endObject();
		return show;
	}
}
