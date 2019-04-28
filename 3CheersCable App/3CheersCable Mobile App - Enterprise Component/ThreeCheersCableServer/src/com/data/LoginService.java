package com.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.beans.LoginBean;

public class LoginService {
	public LoginBean getUserInfo(String name, String password) {
		String sqlquery ="SELECT * FROM USERDATA WHERE username='"+name+"' AND password ='"+password+"'";
		
		LoginBean loginBean=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/threecheerscable","root", "");
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sqlquery);
			if(resultSet.next()){	
				loginBean = new LoginBean();
				loginBean.setResult(1);
				loginBean.setSubscriberId(resultSet.getString(1));
				loginBean.setUserName(resultSet.getString(2));
				loginBean.setUserSurname(resultSet.getString(3));
				loginBean.setPassword(resultSet.getString(4));
				loginBean.setAddress(resultSet.getString(5));
				loginBean.setCity(resultSet.getString(6));
				loginBean.setState(resultSet.getString(7));
				loginBean.setCountry(resultSet.getString(8));
				loginBean.setMobileNo(resultSet.getString(9));
				loginBean.setEmail(resultSet.getString(10));
			}	
			else{
				loginBean = new LoginBean();
				loginBean.setResult(0);
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
		return loginBean;
	}
}
