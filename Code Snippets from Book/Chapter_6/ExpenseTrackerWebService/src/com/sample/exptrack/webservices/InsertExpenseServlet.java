package com.sample.exptrack.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sample.webtrack.jsonutils.JsonGenerator;
import com.sample.webtrack.services.ExpenseTrackerServices;

/**
 * Servlet implementation class InsertExpensesServlet
 */
public class InsertExpenseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertExpenseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
		System.out.println("In put method");
		BufferedReader bufferedReader=new BufferedReader( new InputStreamReader(req.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			builder.append(line);
		}
		String requestParams=builder.toString();
		String params[]=requestParams.split("&");
		String expenseType=params[0].split("=")[1];
		float expenseAmount=Float.parseFloat(params[1].split("=")[1]);
		/*String expenseType=req.getParameter("expenseType");
		System.out.println(expenseType);
		System.out.println(req.getParameter("expenseAmount"));
		float expenseAmount=Float.parseFloat(req.getParameter("expenseAmount"));*/
		int rowInserted=new ExpenseTrackerServices().insertExpense(expenseType, expenseAmount);
		System.out.println("row inserterd"+rowInserted);
		resp.setContentType("text/json");
		Writer writer=resp.getWriter();
		writer.write("{\"rowsId\": "+rowInserted+"}");
		writer.flush();
		
		
	}

}
