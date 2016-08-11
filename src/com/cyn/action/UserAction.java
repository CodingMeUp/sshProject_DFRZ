package com.cyn.action;

import java.util.List;
import java.util.Map;

import com.cyn.entity.UserBean;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.cyn.service.RoleService;
import com.cyn.service.UserService;
import com.cyn.utils.*;

public class UserAction extends BaseAction implements ModelDriven<UserBean> {
	private UserService userService = new UserService();
	private UserBean userBean;

	public UserBean getModel() {
		userBean = new UserBean();
		return userBean;
	}

	@Override
	public String execute() throws Exception {
		return null;
	}

	public String list() throws Exception {
		return "list";
	}

	public String listGridJson() throws Exception {
		Map<String, Object> dataMap = userService.getUserListGrid(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}

	public String deleteUser() throws Exception {

		String delete_user_id = request.getParameter("delete_user_id");
		Map<String, Object> dataMap = userService.deleteUser(delete_user_id);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	
	public String add() throws Exception {
	   
		//跳转到增加页面
		
		RoleService roleService = new RoleService();
		List<Map<String, Object>> roleList = roleService.getRoleList();
		ActionContext context = ActionContext.getContext();
		context.put("roleList", roleList);

		return "add";
	}

	public String save() throws Exception {
		
		Map<String, Object> jsonMap = userService.saveUser(this.userBean);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}

	public String edit() throws Exception {
		
	   // 跳转到修改页面	
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		this.userBean = userService.edit(request);
		if (this.userBean != null) 
			valueStack.push(userBean);
		RoleService roleService = new RoleService();
		List<Map<String, Object>> roleList = roleService.getRoleList();
		context.put("roleList", roleList);

		return "edit";
	}

	public String modify() throws Exception {
		Map<String, Object> jsonMap = userService.modifyUser(this.userBean);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}
}
