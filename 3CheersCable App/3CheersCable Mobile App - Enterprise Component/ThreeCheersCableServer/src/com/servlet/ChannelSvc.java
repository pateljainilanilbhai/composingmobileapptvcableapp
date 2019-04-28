package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.ChannelsBean;
import com.data.ChannelServiceNew;
import com.google.gson.Gson;

/**
 * Servlet implementation class ChannelSvc
 */
public class ChannelSvc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ChannelServiceNew chnSvc;

    /**
     * Default constructor. 
     */
    public ChannelSvc() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		chnSvc = new ChannelServiceNew();
		ArrayList<ChannelsBean.Channel> channels = chnSvc.getChannelList(request.getParameter("categoryId"));
		ChannelsBean channelsBean = new ChannelsBean();
		channelsBean.setResult(channels.size());
		channelsBean.setChannels(channels);
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		out.println(gson.toJson(channelsBean));
	}


}
