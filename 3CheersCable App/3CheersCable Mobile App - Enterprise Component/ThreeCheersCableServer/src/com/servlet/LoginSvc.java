package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.LoginBean;

import com.data.LoginService;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoginSvc
 */
public class LoginSvc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginSvc;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginSvc() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		loginSvc = new LoginService();
		
		String password = request.getParameter("password");
		String subscriberId = request.getParameter("username");
		PrintWriter out;
		
		
		out = response.getWriter();
		if(subscriberId!=null && password!=null){
			LoginBean loginBn = loginSvc.getUserInfo(subscriberId, password);
			Gson gson = new Gson();
			out.println(gson.toJson(loginBn));
		}
		else{
			out.println("Enter some input " + subscriberId + "  " + password);
		}
	}

}
