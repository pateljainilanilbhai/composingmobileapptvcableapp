package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.beans.ChannelsBean;

public class ChannelServiceNew {
	ArrayList<ChannelsBean.Channel> channels;	

	public ArrayList<ChannelsBean.Channel> getChannelList(String categoryID) {
		channels=new ArrayList<ChannelsBean.Channel>();		
		String sqlquery ="SELECT * FROM CHANNELDATA where categoryid = " + categoryID;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/threecheerscable","root", "");
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sqlquery);
			while(resultSet.next()){	
				ChannelsBean.Channel channelBean = new ChannelsBean.Channel();
				channelBean.setChannelId(resultSet.getString(1));
				channelBean.setName(resultSet.getString(2));
				channelBean.setCategoryId(resultSet.getString(3));
				channelBean.setImgurl(resultSet.getString(4));
				channelBean.setGenre(resultSet.getString(5));	
				channelBean.setHd(resultSet.getString(6));
				
				channels.add(channelBean);			
			}	
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channels;
	}
}
