package com.cyn.action;

import java.util.List;
import java.util.Map;

import com.cyn.service.RoleService;
import com.cyn.utils.BaseAction;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class RoleAction extends BaseAction {
	private RoleService roleService = new RoleService();

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String list() throws Exception {
		return "list";
	}

	/**
	 * 返回角色列表网格所需的json数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listGridJson() throws Exception {
		Map<String, Object> dataMap = roleService.getRoleListGrid(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);

		out.flush();
		out.close();
		return NONE;
	}

	public String deleteRole() throws Exception {
		String delete_role_id = request.getParameter("delete_role_id");
		Map<String, Object> dataMap = roleService.deleteRole(delete_role_id);
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

	/**
	 * 生成菜单树，并转化为json的数据结构
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loadMenuTree() throws Exception {
		List<Map<String, Object>> treeList = roleService.loadMenuTree(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(treeList);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}

	public String save() throws Exception {
		Map<String, Object> jsonMap = roleService.saveRole(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}

	public String edit() throws Exception {
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		Map<String, Object> roleMap = roleService.edit(request);
		if (roleMap != null) {
			valueStack.push(roleMap);
		}
		return "edit";
	}

	public String modify() throws Exception {
		Map<String, Object> jsonMap = roleService.modifyRole(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(jsonMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;

	}
}
