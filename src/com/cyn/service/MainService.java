package com.cyn.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;

import com.cyn.entity.UserBean;
import com.cyn.utils.JdbcUtil;

public class MainService {

	public List<Map<String, Object>> leftTreeMenu(HttpServletRequest request) {
		int userid = 5;
		
		String userID = (String) request.getSession().getAttribute("userid");
		if (userID == null)
			userID = "5";
		userid = Integer.parseInt(userID);

		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> treeNode = null;
		Map<String, Map<String, Object>> id_nodeObjMap = new HashMap<String, Map<String, Object>>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer querySQL = new StringBuffer();
       
		querySQL.append("Select *");
		querySQL.append(" From Sys_Menu");
		querySQL.append(" where menu_id in");
		querySQL.append("        (Select distinct menu_id");
		querySQL.append("          From Sys_Role_Menu");
		querySQL.append("          where role_id in");
		querySQL.append("              (Select role_id From Sys_User_Role");
		querySQL.append("  where user_id = '" + userid + "'))");
		querySQL.append("  order by to_char(menu_id) asc");


		String menu_id = null;
		String menu_name = null;
		String parentid = null;
		String menu_href = null;
		int grade = 0;
		int isleaf = 0;
		String contextPath = request.getContextPath();
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				menu_id = rs.getString("menu_id");
				menu_name = rs.getString("menu_name");
				parentid = StringUtils.trimToNull(rs.getString("parentid"));
				menu_href = StringUtils.trimToNull(rs.getString("menu_href"));
				grade = rs.getInt("grade");
				isleaf = rs.getInt("isleaf");

		
				treeNode = new LinkedHashMap<String, Object>();
				treeNode.put("id", menu_id);
				treeNode.put("text", menu_name);
				treeNode.put("isleaf", isleaf);
				if (grade >= 2 && isleaf == 0) {
					treeNode.put("state", "closed");    // 控制层级展开树  grade 
				}
				if (isleaf == 1) {
					treeNode.put("menu_href", contextPath + menu_href);
				}
			
				
				id_nodeObjMap.put(menu_id, treeNode);

				if (parentid.equals("0")) {
					treeList.add(treeNode);
				} else {
					Map<String, Object> parentTreeNode = id_nodeObjMap
							.get(parentid);
					if (parentTreeNode != null) {
						List<Map<String, Object>> children = null;
						if (parentTreeNode.get("children") == null) {
							children = new ArrayList<Map<String, Object>>();
						} else {
							children = (List<Map<String, Object>>) parentTreeNode
									.get("children");
						}
						children.add(treeNode);
						parentTreeNode.put("children", children);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return treeList;

	}

	public List<Map<String, Object>> leftAccordionPrentMenu(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean userBean = null;
		int userid = 0;
		if (session.getAttribute("userBean") != null) {
			userBean = (UserBean) session.getAttribute("userBean");
			userid = userBean.getUserid();
		} else {
			userid = 5;
		}
		List<Map<String, Object>> parentMenuList = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		StringBuffer querySQL = new StringBuffer();

		querySQL.append("Select *");
		querySQL.append(" From Sys_Menu");
		querySQL.append(" where menu_id in");
		querySQL.append("        (Select distinct menu_id");
		querySQL.append("          From Sys_Role_Menu");
		querySQL.append("          where role_id in");
		querySQL.append("              (Select role_id From Sys_User_Role");
		querySQL.append("  where user_id = '" + userid + "'))");
		querySQL.append(" and parentid = '0'");
		querySQL.append("  order by to_char(menu_id) asc");

		try {
			conn = JdbcUtil.getConn();

			QueryRunner queryRunner = new QueryRunner();
			parentMenuList = queryRunner.query(conn, querySQL.toString(),
					new MapListHandler());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(null, null, conn);
		}
		return parentMenuList;
	}

	public List<Map<String, Object>> leftTreeMenuForAccordion(
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserBean userBean = null;
		int userid = 0;
		if (session.getAttribute("userBean") != null) {
			userBean = (UserBean) session.getAttribute("userBean");
			userid = userBean.getUserid();
		} else {
			userid = 5;
		}
		String param_menu_id = request.getParameter("menu_id");

		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> treeNode = null;
		Map<String, Map<String, Object>> id_nodeObjMap = new HashMap<String, Map<String, Object>>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer querySQL = new StringBuffer();

		querySQL.append("Select *");
		querySQL.append(" From Sys_Menu");
		querySQL.append(" where menu_id in");
		querySQL.append("        (Select distinct menu_id");
		querySQL.append("          From Sys_Role_Menu");
		querySQL.append("          where role_id in");
		querySQL.append("              (Select role_id From Sys_User_Role");
		querySQL.append("  where user_id = '" + userid + "'))");
		querySQL.append(" and menu_id like '" + param_menu_id + "%'");
		querySQL.append("  order by to_char(menu_id) asc");


		String menu_id = null;
		String menu_name = null;
		String parentid = null;
		String menu_href = null;
		int grade = 0;
		int isleaf = 0;
		String contextPath = request.getContextPath();
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				menu_id = rs.getString("menu_id");
				menu_name = rs.getString("menu_name");
				parentid = StringUtils.trimToNull(rs.getString("parentid"));
				menu_href = StringUtils.trimToNull(rs.getString("menu_href"));
				grade = rs.getInt("grade");
				isleaf = rs.getInt("isleaf");

				/**
				 * ����һ�����ν��
				 */
				treeNode = new LinkedHashMap<String, Object>();
				treeNode.put("id", menu_id);
				treeNode.put("text", menu_name);
				treeNode.put("isleaf", isleaf);
				if (grade >= 2 && isleaf == 0) {
					treeNode.put("state", "closed");
				}
				if (isleaf == 1) {
					treeNode.put("menu_href", contextPath + menu_href);
				}
				/**
				 * ���ڵ�ŵ�Map������
				 */
				id_nodeObjMap.put(menu_id, treeNode);

				if (parentid.equals("0")) {
					treeList.add(treeNode);
				} else {
					Map<String, Object> parentTreeNode = id_nodeObjMap
							.get(parentid);
					if (parentTreeNode != null) {
						List<Map<String, Object>> children = null;
						if (parentTreeNode.get("children") == null) {
							children = new ArrayList<Map<String, Object>>();
						} else {
							children = (List<Map<String, Object>>) parentTreeNode.get("children");
						}
						children.add(treeNode);
						parentTreeNode.put("children", children);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return treeList;
	}
}
