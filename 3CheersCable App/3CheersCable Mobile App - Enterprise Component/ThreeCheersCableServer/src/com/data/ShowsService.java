package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.beans.CategoriesBean;
import com.beans.CategoriesBean.Category;
import com.beans.ShowsBean;
import com.beans.ShowsBean.Show;

public class ShowsService {
	public ShowsBean getShows(String channelID) {
		ShowsBean shows = new ShowsBean();
		ArrayList<Show> showsList;
		showsList=new ArrayList<ShowsBean.Show>();		
		String sqlquery ="SELECT * FROM SHOWSDATA where channelId = " + channelID;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/threecheerscable","root", "");
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sqlquery);
			//
			while(resultSet.next()){	
				ShowsBean.Show showBean=new Show();
				showBean.setShowId(resultSet.getString(1));
				showBean.setShowName(resultSet.getString(2));
				showBean.setChannelId(resultSet.getString(3));
				showBean.setShowTiming(resultSet.getString(4));
				
				
				showsList.add(showBean);	
				
				
						
				
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
		shows.setResult(showsList.size());
		shows.setShows(showsList);
		return shows;		
	}		

}
