package com.cyn.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cyn.service.LoginService;
import com.cyn.service.SampleImageCaptchaService;
import com.cyn.utils.BaseAction;
import com.google.gson.Gson;

public class LoginAction  extends BaseAction{
  
	public String login()
	{ 
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Gson gs = new Gson();
		String jsonStr=null;
		/**
		 *先做验证码判断 ， 失败就不做登陆密码验证
		 */
		String vcode = request.getParameter("vcode");
		if(StringUtils.isEmpty(vcode))   // 假如为空娶不到默认为A肯定为假
			vcode = "a";
		vcode = vcode.trim().toLowerCase(Locale.getDefault());   //将验证码全部转为小写处理 忽略大小写
        Boolean isVcode = null;
        String  sessID = request.getSession().getId();
        isVcode = SampleImageCaptchaService.getInstance().validateResponseForID(sessID, vcode); 
        if(isVcode){
        	dataMap.put("vcodeFlag", true);
        	/**
        	 *  验证码通过之后做密码账户验证
        	 */
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			if (StringUtils.isEmpty(username) && StringUtils.isEmpty(password)) {
				dataMap.put("loginFlag", false);
			} else {
				Map<String, Object> loginMap = new HashMap<String, Object>();
				loginMap = new LoginService().loginResult(username,password);
				if (loginMap!=null) {
					dataMap.put("loginFlag", true);
					request.getSession().setAttribute("loginName", loginMap.get("username"));
					request.getSession().setAttribute("userid", 		loginMap.get("userid").toString());
				} else {
					dataMap.put("loginFlag", false);
				}
			}
        } else{
        	dataMap.put("vcodeFlag",false);
        }
        jsonStr = gs.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	
	

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
