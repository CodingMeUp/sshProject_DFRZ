package com.cyn.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.cyn.entity.UserBean;
import com.cyn.service.MainService;
import com.cyn.utils.BaseAction;
import com.cyn.utils.ParamUtil;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;

public class MainAction extends BaseAction {
	private MainService mainService = new MainService();

	@Override
	public String execute() throws Exception {
		HttpSession session = request.getSession();
		UserBean userBean = null;
		int userid = 0;
		if (session.getAttribute("userBean") != null) {
			userBean = (UserBean) session.getAttribute("userBean");
			userid = userBean.getUserid();
		} else {
			userid = 5;
		}

		ParamUtil paramUtil = new ParamUtil();
		Map<String, String> paramMap = paramUtil.getUserParamMap(String
				.valueOf(userid));

		String left_menu_style = String
				.valueOf(paramMap.get("left_menu_style"));
		if (StringUtils.isEmpty(left_menu_style)) {
			left_menu_style = "tree";
		}
		if (left_menu_style.equals("tree")) {
			return "main_index_1";
		} else {
			ActionContext context = ActionContext.getContext();
			List<Map<String, Object>> parentMenuList = mainService
					.leftAccordionPrentMenu(request);
			context.put("parentMenuList", parentMenuList);

			return "main_index_2";
		}
	}

	/**
	 * �������ҳ�����״�˵���json���
	 * 
	 * @return
	 * @throws Exception
	 */
	public String leftTreeMenu() throws Exception {
		List<Map<String, Object>> treeList = mainService.leftTreeMenu(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(treeList);
		 
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}

	public String leftTreeMenuForAccordion() throws Exception {
		List<Map<String, Object>> treeList = mainService
				.leftTreeMenuForAccordion(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(treeList);

		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
}
