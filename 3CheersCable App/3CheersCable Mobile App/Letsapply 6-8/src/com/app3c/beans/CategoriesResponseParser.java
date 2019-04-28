package com.app3c.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.util.JsonReader;

import com.app3c.beans.CategoriesBean.Category;


public class CategoriesResponseParser {

	public static CategoriesBean parse(String resultString) throws NullPointerException {
		
		byte[] stringByte = resultString.getBytes();
		InputStream stream = new ByteArrayInputStream(stringByte);
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		CategoriesBean categories = new CategoriesBean();
		try{
			jsonReader.beginObject();
			if(jsonReader.hasNext()){
				String token = jsonReader.nextName();
				if(token.equals("result"))
					categories.setResult(jsonReader.nextInt());
				token = jsonReader.nextName();
				if(token.equals("categories")){
					jsonReader.beginArray();
					categories.setCategories(readCategoryArray(jsonReader));
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
		return categories;
	}
	
	public static ArrayList<Category> readCategoryArray(JsonReader jsonReader) {
		ArrayList<Category> categoryList = new ArrayList<Category>();
		try{
			while(jsonReader.hasNext()){
				categoryList.add(parseCategory(jsonReader));
			}		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return categoryList;
	}
	
	public static Category parseCategory(JsonReader jsonReader) throws IOException {
		Category category = new Category();
		jsonReader.beginObject();
		while(jsonReader.hasNext()){
			String token = jsonReader.nextName();
			if(token.equals("categoryId"))
				category.setCategoryId(jsonReader.nextString());
			else if(token.equals("name"))
				category.setName(jsonReader.nextString());
			else if(token.equals("serviceProvider"))
				category.setServiceProvider(jsonReader.nextString());
			else if(token.equals("imgurl"))
				category.setImgurl(jsonReader.nextString());
			else if(token.equals("language"))
				category.setLanguage(jsonReader.nextString());
			else if(token.equals("genre"))
				category.setGenre(jsonReader.nextString());
			else if(token.equals("quality"))
				category.setQuality(jsonReader.nextString());
			else
				jsonReader.skipValue();
		}
		jsonReader.endObject();
		return category;
	}
}
