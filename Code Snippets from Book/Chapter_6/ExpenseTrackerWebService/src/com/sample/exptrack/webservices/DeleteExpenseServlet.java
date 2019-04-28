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
 * Servlet implementation class DeleteExpsenseServlet
 */
public class DeleteExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteExpenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	/*	BufferedReader bufferedReader=new BufferedReader( new InputStreamReader(request.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		String requestParams=builder.toString();
		System.out.print("In delete    ");
		int expenseId=Integer.parseInt(requestParams.split("=")[1]);
		System.out.print(expenseId);*/
		
		System.out.print(request.getHeader("expenseId"));
		int expenseId=Integer.parseInt(request.getHeader("expenseId"));
		int rowsaffected=new ExpenseTrackerServices().deleteExpense(expenseId);
		response.setContentType("text/json");
		Writer writer=response.getWriter();
		writer.write("{\"rowsDeleted\": "+rowsaffected+"}");
		writer.flush();
	}

}
