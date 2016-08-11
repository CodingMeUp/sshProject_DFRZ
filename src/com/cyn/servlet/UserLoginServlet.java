package com.cyn.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		System.out.println("name=" + name + "\npassword=" + password);
		
		String resultJson = null;
		
		// 失败
		resultJson = "{\"success\":false,\"msg\":\"用户名或密码错误!\"}";

		// 成功
		if ( name != null && "admin".equals(name) ) {
			resultJson = "{\"success\":true,\"msg\":\"登陆成功....\"}";
		}
		
		response.getWriter().print(resultJson);
	}

}
