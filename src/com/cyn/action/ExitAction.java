package com.cyn.action;

import javax.servlet.http.HttpSession;

import com.cyn.utils.BaseAction;

public class ExitAction  extends BaseAction{
  
	public String exit()
	{ 
		HttpSession session = request.getSession(false);
		session.invalidate();
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		return "exit";
	}
	
	public String pwdModify(){
		
		
		
		return "exit";
	}
	

	@Override
	public String execute() throws Exception {
		return null;
	}
}
