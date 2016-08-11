package com.cyn.action;

import java.io.PrintWriter;
import java.util.Map;

import com.cyn.utils.BaseAction;
import com.google.gson.Gson;


/**
 * 框架整合的Action
 * 
 * @author ctd
 * 
 */
public class MessageAction extends BaseAction {


	public String viewTipMsg() throws Exception {
		String view_tip_attr = "{view_notice:'1',view_mail:'1',view_vote:'1'}";

		StringBuffer msgBuffer = new StringBuffer();
		Gson gson = new Gson();
		Map<String, Object> tipMap = gson.fromJson(view_tip_attr, Map.class);

		String view_notice = String.valueOf(tipMap.get("view_notice"));
		String view_mail    = String.valueOf(tipMap.get("view_mail"));
		String view_vote   = String.valueOf(tipMap.get("view_vote"));

		int unread_notice_count = 5;
		int unread_mail_count = 10;
		int unread_vote_count = 20;

		String node_text = "";
		String menu_href = "";
		if (view_notice.equals("1")) {
			node_text = "用户管理";
			menu_href = request.getContextPath() + "/userAction!list.action";

			msgBuffer.append("<a href='javascript:void(0);' onclick=\"addTab('"
					+ node_text + "', '" + menu_href + "', null);\">您还有 "
					+ unread_notice_count + " 个用户待处理</a><br/><br/><br/>");
		}

		if (view_mail.equals("1")) {
			node_text = "菜单管理";
			menu_href = request.getContextPath() + "/menuAction!list.action";
			
			msgBuffer.append("<a href='javascript:void(0);' onclick=\"addTab('"
					+ node_text + "', '" + menu_href + "', null);\">您还有 " + unread_mail_count + " 个菜单待处理</a><br/><br/><br/>");
		}

		if (view_vote.equals("1")) {
			node_text = "角色管理";
			menu_href = request.getContextPath() + "/roleAction!list.action";
			
			
			msgBuffer.append("<a href='javascript:void(0);' onclick=\"addTab('"
					+ node_text + "', '" + menu_href + "', null);\">您还有 " + unread_vote_count + " 位角色待处理</a><br/><br/><br/>");
		}

		PrintWriter out = response.getWriter();

		out.print(msgBuffer.toString());
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
