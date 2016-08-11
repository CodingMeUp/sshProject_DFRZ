package com.cyn.action;

import java.util.ArrayList;
import java.util.Map;


import com.cyn.service.MenuService;
import com.cyn.utils.BaseAction;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class MenuAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6431634295825939985L;
	private MenuService menuService = new MenuService();
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String list() throws Exception {
		return "list";
	}

	public String listGridJson() throws Exception {
		Map<String, Object> dataMap = menuService.getMenuListGrid(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}

	public String loadMenuCombobox() throws Exception {
		ArrayList<Map<String, Object>> menuList = menuService.loadMenuCombobox();
		Gson gson = new Gson();
		String jsonStr = gson.toJson(menuList);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	
	
	public String edit() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		Map<String, Object> menuMap = menuService.edit(request);
		if (menuMap != null) {
			valueStack.push(menuMap);
		}
		return "edit";
	}

	public String save() throws Exception {
		Map<String, Object> jsonMap = menuService.saveMenu(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}
	
	
	public String modify() throws Exception {
		Map<String, Object> jsonMap = menuService.modifyMenu(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}

	public String deleteMenu() throws Exception {
		String delete_menu_id = request.getParameter("delete_menu_id");
		String role_id = request.getParameter("role_id");
		Map<String, Object> dataMap = menuService.deleteMenu(delete_menu_id.trim(),role_id.trim());
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	
	public String add() throws Exception {
		return "add";
	}
}
