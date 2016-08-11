package com.cyn.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyn.service.SampleImageCaptchaService;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String sessID = request.getSession().getId(); 
		String checkCode = request.getParameter("vcode");
		if(checkCode==null)
			checkCode="a";   // a肯定匹配不对
		checkCode = checkCode.trim().toLowerCase(Locale.getDefault());
		 Boolean isResponseCorrect = false;
		  try{
			   isResponseCorrect = SampleImageCaptchaService.getInstance().validateResponseForID(sessID, checkCode); 
		  }
		  catch (Exception e) {
			    response.sendRedirect("login.jsp");
		}
		if(isResponseCorrect){ 
		    System.out.println("true");
		}else{ 
			System.out.println("false");
		}
	}
   
	
}
