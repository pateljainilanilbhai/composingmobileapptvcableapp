package com.sample.webtrack.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;






public class ExpenseDatabaseAdapter {

	private Connection expenseDatabaseConnection;
	
	
	public ExpenseDatabaseAdapter(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			expenseDatabaseConnection=DriverManager.getConnection("jdbc:mysql://localhost:3306/expensetracker", "root", "");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch(SQLException exception)
		{
			exception.printStackTrace();
		}
	}
	
	public ResultSet getExpensesResultSet()
	{
		ResultSet resultSet=null;
		try {
			Statement statement=expenseDatabaseConnection.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_SCROLL_SENSITIVE);
			resultSet=statement.executeQuery("select * from expenses");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
	
	public int insertExpenseInDatabase(String expenseType, float expenseAmount)
	{
		int primaryId=0;
		try {
			PreparedStatement preparedStatement=expenseDatabaseConnection.prepareStatement("insert into expenses (expenseType,expenseAmount) values (?,?)");
			
			preparedStatement.setString(1, expenseType);
			preparedStatement.setFloat(2, expenseAmount);
			System.out.println("ps:"+preparedStatement.toString());
			primaryId=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return primaryId;
	}
	
	public int deleteExpenseFromDatabase(int idToDelete) {
		int deleteId=0;
		
		try {
			PreparedStatement preparedStatement=expenseDatabaseConnection.prepareStatement("delete from expenses where expenseId=?");
			preparedStatement.setInt(1, idToDelete);
			deleteId=preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteId;
	}
	
	public int updateExpense(int expenseId, String expenseType,float expenseAmount) {
		
		int updateReturn=0;
		
		try {
			PreparedStatement preparedStatement=expenseDatabaseConnection.prepareStatement("update expenses set expenseType=?, expenseAmount=? where expenseId=?");
			preparedStatement.setString(1, expenseType);
			preparedStatement.setFloat(2, expenseAmount);
			preparedStatement.setInt(3, expenseId);
			updateReturn=preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updateReturn;
	}
}
