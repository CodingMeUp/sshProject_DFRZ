package com.cyn.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;

import com.cyn.utils.JdbcUtil;

public class RoleService {

	public Map<String, Object> getRoleListGrid(HttpServletRequest request) {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
		Map<String, Object> rowMap = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int roleCount = 0;
		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);

	
			
			
		/**
		 * 获取排序的参数
		 */
		String orderField = request.getParameter("sort");
		String orderType = request.getParameter("order");

		/**
		 * 获取分页的参数
		 */
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		if (StringUtils.isEmpty(rows)) {      // 设置没页的条数
			rows = "30";
		}
		if (StringUtils.isEmpty(page)) {
			page = "1";
		}

		int pageSize = Integer.parseInt(rows);
		int currentPage = Integer.parseInt(page);
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;

		/**
		 * 获取查询的参数
		 */
		String begin_role_id = request.getParameter("begin_role_id");
		String end_role_id = request.getParameter("end_role_id");
		String role_name = request.getParameter("role_name");
		String role_remark = request.getParameter("role_remark");
  
		 
		StringBuffer whereSQL = new StringBuffer();
		if (StringUtils.isNotEmpty(begin_role_id)) {
			if (StringUtils.isEmpty(end_role_id)) {
				end_role_id = begin_role_id;
			}
			whereSQL.append(" and role_id >=" + begin_role_id + "");
			whereSQL.append(" and role_id <=" + end_role_id + "");
		}

		if (StringUtils.isNotEmpty(role_name)) {
			role_name = role_name.trim();
			whereSQL.append(" and role_name like '%" + role_name + "%'");
		}

		if (StringUtils.isNotEmpty(role_remark)) {
			role_remark= role_remark.trim();
			whereSQL.append(" and role_remark like '%" + role_remark + "%'");
		}

		/**
		 * 生成分页的语句
		 */

		querySQL.append("select * \n");
		querySQL.append("  From (Select rownum rn, a.*  \n");
		querySQL.append("         From ( \n");
		querySQL.append("Select role_id,role_name,");
		querySQL.append("role_remark");
		querySQL.append(" From Sys_Role");
		querySQL.append(" where 1 = 1");
		querySQL.append(whereSQL);
		if (StringUtils.isNotEmpty(orderField)) {
			querySQL.append(" order by " + orderField + " " + orderType + "");
		} else {
			querySQL.append(" order by role_id asc");
		}
		querySQL.append(") a \n");
		querySQL.append("        where rownum <= " + endRow + ") t \n");
		querySQL.append(" where t.rn >= " + startRow + "");


		StringBuffer countSQL = new StringBuffer();
		countSQL.setLength(0);
		countSQL.append("Select count(1) as rsCount");
		countSQL.append(" From Sys_Role");
		countSQL.append(" where 1 = 1");
		countSQL.append(whereSQL);
		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();

			/**
			 * 1：获取总数量
			 */
			rs = stmt.executeQuery(countSQL.toString());
			if (rs.next()) {
				roleCount = rs.getInt("rsCount");
			} else {
				roleCount = 0;
			}
			rs.close();
			rs = null;

			/**
			 * 2：获取角色的集合
			 */
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				rowMap = new HashMap<String, Object>();
				rowMap.put("role_id", rs.getInt("role_id"));
				rowMap.put("role_name", rs.getString("role_name"));
				rowMap.put("role_remark", rs.getString("role_remark"));
				roleList.add(rowMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		dataMap.put("total", roleCount);
		dataMap.put("rows", roleList);

		return dataMap;
	}

	public Map<String, Object> deleteRole(String delete_role_id) {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
        int row = 0; 
		StringBuffer delUsedRole = new StringBuffer();
		delUsedRole.setLength(0);
		delUsedRole.append("select user_id,role_id ");
		delUsedRole.append("from SYS_USER_ROLE ");
		delUsedRole.append("where 1=1 and role_id in (" + delete_role_id + ")");
		
		StringBuffer delSQL = new StringBuffer();
		delSQL.setLength(0);
		delSQL.append("Delete From Sys_Role");
		delSQL.append(" where 1 = 1");
		delSQL.append(" and role_id in (" + delete_role_id + ")");

		StringBuffer delSQL2 = new StringBuffer();
		delSQL2.setLength(0);
		delSQL2.append("Delete From Sys_Role_menu");
		delSQL2.append(" where 1 = 1");
		delSQL2.append(" and role_id in (" + delete_role_id + ")");
		
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			row =  stmt.executeUpdate(delUsedRole.toString());
		    if( row == 0)
		    {
		    	stmt.executeUpdate(delSQL.toString());
		    	stmt.executeUpdate(delSQL2.toString());
		    	dataMap.put("flag", true);
		    }else{
		    	dataMap.put("flag", false);
		    	dataMap.put("errormsg", "角色使用中不能被删除");
		    }
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("errormsg", e.getMessage());
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return dataMap;
	}

	public List<Map<String, Object>> loadMenuTree(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");

		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		Map<String, Object> treeNode = null;
		Map<String, Map<String, Object>> id_nodeObjMap = new HashMap<String, Map<String, Object>>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer querySQL = new StringBuffer();
		if (StringUtils.isEmpty(role_id)) {
			querySQL.append("select ");
			querySQL.append(" menu_id,menu_name,menu_href,");
			querySQL.append(" parentid,grade,isleaf \n");
			querySQL.append(" from Sys_Menu");
			querySQL.append(" order by  to_char(menu_id) asc \n");
		} else {
			querySQL.append("select a.*, ");
			querySQL.append(" b.menu_id as role_owner_menu_id \n");
			querySQL.append(" 		from sys_Menu a \n");
			querySQL.append(" 		left outer join ( \n");
			querySQL.append(" 			select menu_id");
			querySQL.append("                   from sys_role_menu t");
			querySQL
					.append("                where role_id = '" + role_id + "'");
			querySQL.append("                and menu_id in \n");
			querySQL
					.append("                    (select menu_id From Sys_Menu where isLeaf = 1)) b \n");
			querySQL.append("  on a.menu_id =     b.menu_id");
			querySQL.append("  order by to_char(a.menu_id) asc");
		}

		String menu_id = null;
		String menu_name = null;
		String parentid = null;
		int grade = 0;
		int isleaf = 0;
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				menu_id = rs.getString("menu_id");
				menu_name = rs.getString("menu_name");
				parentid = StringUtils.trimToNull(rs.getString("parentid"));
				grade = rs.getInt("grade");
				isleaf = rs.getInt("isleaf");

				/**
				 * 构建一个树形结点
				 */
				treeNode = new LinkedHashMap<String, Object>();
				treeNode.put("id", menu_id);
				treeNode.put("text", menu_name);
				if (grade >= 2 && isleaf == 0) {
					treeNode.put("state", "closed");
				}
				if (StringUtils.isNotEmpty(role_id)) {
					/**
					 * 表示是修改角色的页面的树状读取
					 */
					String role_owner_menu_id = rs
							.getString("role_owner_menu_id");
					if (StringUtils.isNotEmpty(role_owner_menu_id)) {
						treeNode.put("checked", true);
					}
				}
				/**
				 * 将节点放到Map对象中
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

	public Map<String, Object> saveRole(HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String role_id 			= request.getParameter("role_id");
		String role_name 		= request.getParameter("role_name");
		String role_remark 		= request.getParameter("role_remark");
		String total_menu_id 	= request.getParameter("total_menu_id");

		Connection conn = null;
		Statement stmt = null;
		StringBuffer insertSQL = new StringBuffer();
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			/**
			 * 1：写入到角色表
			 */
			insertSQL.append("Insert into Sys_Role(");
			insertSQL.append(" role_id,role_name,role_remark");
			insertSQL.append(" ) values(");
			insertSQL.append("'" + role_id + "',");
			insertSQL.append("'" + role_name + "',");
			insertSQL.append("'" + role_remark + "'");
			insertSQL.append(")");
			stmt.executeUpdate(insertSQL.toString());

			/**
			 * 2：写入到角色/菜单表
			 */
			if (StringUtils.isNotEmpty(total_menu_id)) {
				String[] menu_idArray = total_menu_id.split(",");
				for (String menu_id : menu_idArray) {
					insertSQL.setLength(0);
					insertSQL.append("Insert Into Sys_Role_Menu(");
					insertSQL.append(" role_id,menu_id");
					insertSQL.append(" ) values(");
					insertSQL.append("'" + role_id + "',");
					insertSQL.append("'" + menu_id + "'");
					insertSQL.append(" )");

					stmt.addBatch(insertSQL.toString());
				}
				stmt.executeBatch();
			}

			conn.commit();
			jsonMap.put("flag", true);
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

	public Map<String, Object> edit(HttpServletRequest request) {
		String role_id = request.getParameter("role_id");
		Map<String, Object> roleMap = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);
		querySQL.append("Select *  From Sys_Role");
		querySQL.append(" where 1 = 1");
		querySQL.append(" and role_id = '" + role_id + "'");

		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			if (rs.next()) {
				roleMap = new HashMap<String, Object>();
				roleMap.put("role_id", rs.getString("role_id"));
				roleMap.put("role_name", rs.getString("role_name"));
				roleMap.put("role_remark", rs.getString("role_remark"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return roleMap;
	}

	public Map<String, Object> modifyRole(HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String role_id = request.getParameter("role_id");
		String role_name = request.getParameter("role_name");
		String role_remark = request.getParameter("role_remark");
		String total_menu_id = request.getParameter("total_menu_id");

		Connection conn = null;
		Statement stmt = null;
		StringBuffer insertSQL = new StringBuffer();
		StringBuffer udpateSQL = new StringBuffer();
		StringBuffer delSQL = new StringBuffer();
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			/**
			 * 1：更新到角色表
			 */
			udpateSQL.append("Update Sys_Role set ");
			udpateSQL.append("role_name = '" + role_name + "',");
			udpateSQL.append("role_remark = '" + role_remark + "'");
			udpateSQL.append(" where  1 = 1");
			udpateSQL.append(" and role_id = '"+role_id+"'");

			stmt.executeUpdate(udpateSQL.toString());

				
			/**
			 * 2：删除角色原有的菜单信息
			 * 
			 */
			delSQL.append("Delete From Sys_Role_Menu ");
			delSQL.append(" where  1 = 1");
			delSQL.append(" and role_id = '"+role_id+"'");
			stmt.executeUpdate(delSQL.toString());
			
			/**
			 * 3：写入到角色/菜单表
			 */
			if (StringUtils.isNotEmpty(total_menu_id)) {
				String[] menu_idArray = total_menu_id.split(",");
				for (String menu_id : menu_idArray) {
					insertSQL.setLength(0);
					insertSQL.append("Insert Into Sys_Role_Menu(");
					insertSQL.append(" role_id,menu_id");
					insertSQL.append(" ) values(");
					insertSQL.append("'" + role_id + "',");
					insertSQL.append("'" + menu_id + "'");
					insertSQL.append(" )");
					stmt.addBatch(insertSQL.toString());
				}
				stmt.executeBatch();
			}

			conn.commit();
			jsonMap.put("flag", true);
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

	public List<Map<String, Object>> getRoleList() {
		List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();

		StringBuffer querySQL = new StringBuffer();
		querySQL.append("Select role_id,role_name,");
		querySQL.append(" role_remark");
		querySQL.append(" From Sys_Role");
		querySQL.append(" order by role_id asc");
		Connection conn = null;
		try {
			conn = JdbcUtil.getConn();
			QueryRunner queryRunner = new QueryRunner();
			roleList = queryRunner.query(conn, querySQL.toString(),
					new MapListHandler());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return roleList;
	}
}
