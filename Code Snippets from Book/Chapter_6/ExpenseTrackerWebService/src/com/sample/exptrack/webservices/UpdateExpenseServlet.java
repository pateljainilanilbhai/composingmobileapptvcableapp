package com.sample.exptrack.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.webtrack.services.ExpenseTrackerServices;

/**
 * Servlet implementation class UpdateExpenseServlet
 */
public class UpdateExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateExpenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		BufferedReader bufferedReader=new BufferedReader( new InputStreamReader(request.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		String requestParams=builder.toString();
		String params[]=requestParams.split("&");
		int expenseId=Integer.parseInt(params[0].split("=")[1]);
		String expenseType=params[1].split("=")[1];
		float expenseAmount=Float.parseFloat(params[2].split("=")[1]);		
		boolean updateSuccess=new ExpenseTrackerServices().updateExpense(expenseId, expenseType, expenseAmount);
		response.setContentType("text/json");
		Writer writer=response.getWriter();
		writer.write("{\"updateSuccess\": "+updateSuccess+"}");
		writer.flush();
		
	}


}
