package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.beans.CategoriesBean;
import com.beans.ShowsBean;
import com.data.CategoriesService;
import com.data.ShowsService;
import com.google.gson.Gson;

/**
 * Servlet implementation class ShowsSvc
 */
public class ShowsSvc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ShowsService showSvc;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowsSvc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		showSvc = new ShowsService();
		ShowsBean showsBean;
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		showsBean =  showSvc.getShows(request.getParameter("ChannelId"));
			
		
		//		Type mapType = new TypeToken <HashMap<String, ArrayList<ShowsBean>>>(){}.getType();
		out.println(gson.toJson(showsBean));
	}

}
