package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.beans.CategoriesBean;
import com.data.CategoriesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CategoriesSvc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoriesService categorySvc;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriesSvc() {
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
		categorySvc = new CategoriesService();
		CategoriesBean categoriesBean;
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		categoriesBean =  categorySvc.getCategories();
			
		
		//		Type mapType = new TypeToken <HashMap<String, ArrayList<ShowsBean>>>(){}.getType();
		out.println(gson.toJson(categoriesBean));
	}

}
