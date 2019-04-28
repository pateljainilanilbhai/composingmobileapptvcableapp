package com.app3c.webserviceconsumer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;




public class GenerateURLs {

	public enum Operation{
		CATEGORIES, CHANNELS, SHOWS, LOGIN
	};
	
	public static HttpPost getPostURL(Operation type, String...params){
		HttpPost post = null; 
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		switch(type){
		case LOGIN:
			
			post= new HttpPost(AppConfig.ServerAddress+"LoginSvc");
			nameValuePairs.add(new BasicNameValuePair("username", params[0]));
			nameValuePairs.add(new BasicNameValuePair("password",params[1]));
			break;	
		case CATEGORIES:
			post= new HttpPost(AppConfig.ServerAddress+"CategoriesSvc");
			break;
		case CHANNELS : 
			post= new HttpPost(AppConfig.ServerAddress+"ChannelSvc");
			nameValuePairs.add(new BasicNameValuePair("categoryId", params[0]));
			break;
		case SHOWS :
			post = new HttpPost(AppConfig.ServerAddress + "ShowsSvc");
			nameValuePairs.add(new BasicNameValuePair("ChannelId", params[0]));
			break;
		}
		try {
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
			post.setEntity(urlEncodedFormEntity);
			System.out.println(post.getURI());
			//Printing all parameters
			HttpEntity entity = post.getEntity();
			InputStream content = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(content);
			byte[] buffer = new byte[1024];
			while(bis.available()>0){
				bis.read(buffer);
				System.out.println(new String(buffer));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return post;
	}

}
