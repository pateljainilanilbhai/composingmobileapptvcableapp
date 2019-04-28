package com.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageDnld
 */
public class ImageDnld extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageDnld() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="";
		if(request.getParameter("img")!=null) {
			File imageFile = new File("D:\\Android tools\\eclipse4.0\\new eclipse\\eclipse\\ICONS\\"+request.getParameter("img")+".png");
			FileInputStream con = new FileInputStream(imageFile);
			InputStream ins = con;			   
			OutputStream os= response.getOutputStream();
			byte[] buf=new byte[10000];
			int size=0;
			while((size=ins.read(buf))>0)
				os.write(buf,0,size);
			os.close();
			
			/*for(int i=0;i<size;i++)*/
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
