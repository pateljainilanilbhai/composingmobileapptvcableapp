package com.data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;


import com.beans.CategoriesBean;
import com.beans.CategoriesBean.Category;
import com.beans.ChannelsBean;

public class CategoriesService {



	public CategoriesBean getCategories() {
		CategoriesBean categories = new CategoriesBean();
		ArrayList<Category> categoriesList;
		categoriesList=new ArrayList<CategoriesBean.Category>();		
		String sqlquery ="SELECT * FROM CATEGORYDATA";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/threecheerscable","root", "");
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sqlquery);
			//
			while(resultSet.next()){	
				CategoriesBean.Category categoryBean = new CategoriesBean.Category();
				categoryBean.setCategoryId(resultSet.getString(1));
				categoryBean.setName(resultSet.getString(2));
				categoryBean.setServiceProvider(resultSet.getString(3));
				categoryBean.setImgurl(resultSet.getString(4));
				categoryBean.setGenre(resultSet.getString(5));
				categoryBean.setQuality(resultSet.getString(6));
				
				categoriesList.add(categoryBean);	
				
				
						
				
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
		categories.setResult(categoriesList.size());
		categories.setCategories(categoriesList);
		return categories;		
	}		

}
