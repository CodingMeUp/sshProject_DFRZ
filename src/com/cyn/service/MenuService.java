package com.cyn.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.cyn.utils.JdbcUtil;
import com.sun.faces.lifecycle.UpdateModelValuesPhase;

public class MenuService {
  

	public Map<String, Object> getMenuListGrid(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowMap = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int flag = 0;
		StringBuffer querySQL = null;

		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");
		String role_remark = request.getParameter("role_remark");

		StringBuffer whereSQL = new StringBuffer();
		if (StringUtils.isNotEmpty(role_id)) {
			whereSQL.append(" and b.role_id = " + role_id.trim() + "");
			flag++;
		}

		if (StringUtils.isNotEmpty(role_name)) {
			whereSQL.append(" and c.role_name = '" + role_name.trim() + "'");
			flag++;
		}

		if (StringUtils.isNotEmpty(role_remark)) {
			whereSQL
					.append(" and c.role_remark = '" + role_remark.trim() + "'");
			flag++;
		}

		if (flag == 0) {
			querySQL = new StringBuffer();
			querySQL.setLength(0);
			querySQL
					.append("Select a.menu_id, a.menu_name, a.menu_href, a.parentid, a.grade, a.isleaf ");
			querySQL.append("  from sys_menu a, sys_role_menu b ");
			querySQL
					.append(" where 1 = 1  and a.menu_id = b.menu_id and b.role_id = 1");
			querySQL.append(" order by to_char(menu_id) asc");

		}
		if (flag > 0) {
			querySQL = new StringBuffer();
			querySQL.setLength(0);
			querySQL
					.append("Select a.menu_id, a.menu_name, a.menu_href, a.parentid, a.grade, a.isleaf");
			querySQL.append("  from sys_menu a, sys_role_menu b,sys_role c");
			querySQL.append(" where 1 = 1");
			querySQL.append(whereSQL);
			querySQL
					.append("   and a.menu_id = b.menu_id  and c.role_id = b.role_id");
			querySQL.append(" order by to_char(b.menu_id) asc");
		}

		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				String parentid = rs.getString("parentid");
				int isleaf = rs.getInt("isleaf");
				String isleaf_str = "";
				if (isleaf == 1) {
					isleaf_str = "<font color='red'>是</font>";
				} else {
					isleaf_str = "<font color='blue'>否</font>";
				}
				rowMap = new LinkedHashMap<String, Object>();
				rowMap.put("id", rs.getString("menu_id"));
				rowMap.put("menu_name", rs.getString("menu_name"));
				rowMap.put("menu_href", rs.getString("menu_href"));
				rowMap.put("grade", rs.getString("grade"));
				rowMap.put("isleaf_str", isleaf_str);
				if (role_id == null || "".equals(role_id)
						|| role_id.length() == 0) {
					rowMap.put("roleid", 1);
				} else {
					rowMap.put("roleid", role_id);
				}
				if (!parentid.equals("0")) {
					rowMap.put("_parentId", parentid);
				} else {
					rowMap.put("state", "open");
				}
				menuList.add(rowMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		dataMap.put("total", 10000);
		dataMap.put("rows", menuList);

		return dataMap;
	}

	public ArrayList<Map<String, Object>> loadMenuCombobox() {
		ArrayList<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
		Map<String, Object> menuMap = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);
		querySQL
				.append("select menu_id,menu_name from sys_menu where isleaf = '0' order by  to_char(menu_id) asc");
		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				menuMap = new HashMap<String, Object>();
				menuMap.put("menu_id", rs.getString("menu_id"));
				menuMap.put("menu_name", rs.getString("menu_name"));
				array.add(menuMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return array;
	}

	public Map<String, Object> edit(HttpServletRequest request) {
		String menu_id = request.getParameter("menu_id");
		Map<String, Object> menuMap = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);
		querySQL.append("select menu_id, menu_name, menu_href");
		querySQL.append("  from SYS_MENU ");
		querySQL.append("where 1 = 1");
		querySQL.append("  and menu_id = " + menu_id + "");
		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			if (rs.next()) {
				menuMap = new HashMap<String, Object>();
				menuMap.put("menu_id", rs.getString("menu_id"));
				menuMap.put("menu_name", rs.getString("menu_name"));
				menuMap.put("menu_href", rs.getString("menu_href"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return menuMap;
	}

	public Map<String, Object> modifyMenu(HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String menu_id = request.getParameter("menu_id");
		String menu_name = request.getParameter("menu_name");
		String menu_href = request.getParameter("menu_href");

		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer updateSQL = new StringBuffer();
		updateSQL.append("update sys_menu");
		updateSQL.append("   set menu_name = '" + menu_name.trim() + "',"
				+ "menu_href = '" + menu_href.trim() + "'");
		updateSQL.append(" where 1 = 1");
		updateSQL.append("  and menu_id = " + menu_id + "");
		try {
			conn = JdbcUtil.getConn();
			ps = conn.prepareStatement(updateSQL.toString());
			ps.execute();
			conn.commit();
			jsonMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage().contains("FK_MENUNAME")) {
				jsonMap.put("errormsg", "已有重复菜单");
			}
			jsonMap.put("flag", false);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtil.closeResource(ps, conn);
		}
		return jsonMap;
	}
	
	public Map<String, Object> saveMenu(HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String selectBox = request.getParameter("selectBox");
		String dirMenu = request.getParameter("dirMenu");
		String itemMenu = request.getParameter("itemMenu");
		String topMenu = request.getParameter("topMenu");
		
		StringBuffer query = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		StringBuffer insertSQL = new StringBuffer();
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
        
			//先取得数据库中所有菜单的名称 验证同名：
			query = new StringBuffer(); 
			query.append("select menu_name from sys_menu");
			rs = stmt.executeQuery(query.toString());
			StringBuffer  menuName = new StringBuffer(); 
			while (rs.next()) {
				 menuName.append(rs.getString("menu_name"));
				 menuName.append(",");
			}

				 // 传入的是新的顶级菜单
				 if(StringUtils.isNotEmpty(topMenu)){
					 if(menuName.toString().contains(topMenu) ){
						 jsonMap.put("flag", false);
						 jsonMap.put("errormsg", "同名菜单名");
					 }else{
							 query = new StringBuffer();
							 query.setLength(0);
							 query.append("select menu_id from SYS_MENU where grade = 1 and rownum = 1 order by menu_id desc ");
							 rs = stmt.executeQuery(query.toString());
							 String insertID = null;
							 while (rs.next()) {
								  insertID = String.valueOf(Integer.parseInt(rs.getString(1))+1);
							   }
							 insertSQL.append("Insert Into Sys_Menu(");
							 insertSQL.append("menu_id,menu_name,parentid,grade,isleaf");
							 insertSQL.append(" ) values(");
							 insertSQL.append("'" + insertID + "',");
							 insertSQL.append("'" + topMenu + "',");
							 insertSQL.append("0,1,0 )");
							 stmt.executeUpdate(insertSQL.toString());
							 conn.commit();
							 jsonMap.put("flag", true);
					 }		 
				 }else{
							 //传入的是现有菜单下的添加菜单
						 if(menuName.toString().contains(dirMenu) && dirMenu.length()>0 ){
							 jsonMap.put("flag", false);
							 jsonMap.put("errormsg", "同名菜单夹");
						 }else if(menuName.toString().contains(itemMenu) && itemMenu.length()>0)
						 {
							 jsonMap.put("flag", false);
							 jsonMap.put("errormsg", "同名菜单项");
						 }else{
							 query = new StringBuffer();
							 query.setLength(0);
							 query.append("select max(menu_id) as menu_id, max(grade) as grade");
							 query.append(" from sys_menu t");
							 query.append(" where parentid = "+selectBox+"");
							 query.append(" and grade = (select to_char(grade + 1)");
							 query.append(" from sys_menu");
							 query.append("  where menu_id = "+ selectBox+ " and rownum = 1)");
							 rs = stmt.executeQuery(query.toString());
							 String insertID = null;
							 String insertGrade = null;
							 String isleaf = null;
								 if (rs.next()) {
									 if(StringUtils.isNotEmpty(rs.getString("menu_id")) ){
										  insertID = String.valueOf(Integer.parseInt(rs.getString("menu_id"))+1);
										  insertGrade = rs.getString("grade");
									 }else{
										 insertID = selectBox+"01";
		 								 insertGrade ="2";
									 }
								 }
							 String insertMenu = null;
							 if(StringUtils.isNotEmpty(dirMenu)){
								  isleaf = "0";
								  insertMenu = dirMenu;
							 }else{
								 isleaf ="1";
								 insertMenu = itemMenu;
							 }
								 insertSQL.setLength(0);
								 insertSQL.append("Insert Into Sys_Menu(");
								 insertSQL.append("menu_id,menu_name,parentid,grade,isleaf");
								 insertSQL.append(" ) values(");
								 insertSQL.append("'" + insertID + "',");
								 insertSQL.append("'" + insertMenu + "',");
								 insertSQL.append("'" + selectBox + "',");
								 insertSQL.append("'" + insertGrade + "',");
								 insertSQL.append("'" + isleaf + "')");
								 stmt.executeUpdate(insertSQL.toString());
								 conn.commit();
								 jsonMap.put("flag", true);
						}
				 }
			} catch (Exception e) {
					e.printStackTrace();
					jsonMap.put("flag", false);
					jsonMap.put("errormsg", e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			JdbcUtil.closeResource(stmt, conn);
		}
		return jsonMap;
	}
		
	public Map<String, Object> deleteMenu(String delete_menu_id, String role_id) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		int row = 0;

		StringBuffer likeSQL = new StringBuffer();
		likeSQL.setLength(0);
		String str[] = delete_menu_id.split(",");

		if (str.length == 1)
			likeSQL.append("menu_id like '" + str[0] + "%'");
		if (str.length > 1) {
			for (int i = 0; i < str.length; i++) {
				if (i == str.length - 1) {
					likeSQL.append(" menu_id like '" + str[i] + "%'");
					break;
				}
				likeSQL.append(" menu_id like '" + str[i] + "%' or ");
			}
		}

		StringBuffer delSQL = new StringBuffer();
		delSQL.setLength(0);
		delSQL.append("	delete from sys_role_menu where 1 = 1 and role_id = "
				+ role_id);
		delSQL.append(" and (" + likeSQL.toString() + ")");

		try {
			conn = JdbcUtil.getConn();
			ps = conn.prepareStatement(delSQL.toString());
			row = ps.executeUpdate();
			if (row == 0) {
				dataMap.put("flag", false);
				dataMap.put("errormsg", "菜单使用中无法删除");
			} else if ("1".equals(role_id)) {
				dataMap.put("admin", true);
				dataMap.put("flag", true);
			} else {
				dataMap.put("flag", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
		} finally {
			JdbcUtil.closeResource(ps, conn);
		}
		return dataMap;
	}

}
