package com.app3c.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.JsonReader;

public class LoginResponseParser {

	public static LoginBean parseLoginResponse(String response){
		
		LoginBean bean = new LoginBean();
		byte[] stringByte = response.getBytes();
		InputStream stream = new ByteArrayInputStream(stringByte);
		JsonReader jsonReader = new JsonReader(new InputStreamReader(stream));
		try{
			jsonReader.beginObject();
			while(jsonReader.hasNext()){
				String token = jsonReader.nextName();
				if(token.equals("result"))
					bean.setResult(jsonReader.nextInt());
				else if(token.equals("subscriberId"))
					bean.setSubscriberId(jsonReader.nextString());
				else if(token.equals("userName"))
					bean.setUserName(jsonReader.nextString());
				else if(token.equals("userSurname"))
					bean.setUserSurname(jsonReader.nextString());
				else if(token.equals("password"))
					bean.setPassword(jsonReader.nextString());
				else if(token.equals("address"))
					bean.setAddress(jsonReader.nextString());
				else if(token.equals("city"))
					bean.setCity(jsonReader.nextString());
				else if(token.equals("state"))
					bean.setState(jsonReader.nextString());
				else if(token.equals("country"))
					bean.setCountry(jsonReader.nextString());
				else if(token.equals("mobileNo"))
					bean.setMobileNo(jsonReader.nextString());
				else if(token.equals("email"))
					bean.setEmail(jsonReader.nextString());
				else
					jsonReader.skipValue();
				
			}
			jsonReader.endObject();
			jsonReader.close();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bean;
	}
}
