package com.cyn.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;

import com.cyn.entity.UserBean;
import com.cyn.utils.JdbcUtil;

public class UserService {

	public Map<String, Object> getUserListGrid(HttpServletRequest request) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<UserBean> userList = new ArrayList<UserBean>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int roleCount = 0;
		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);
		

		/*  取得页面的参数方便调试
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String param_name = enumeration.nextElement();
			String param_value = request.getParameter(param_name);
			System.out.println(param_name + "\t" + param_value);
	*/
		// 排序和分页的参数
		String orderField = request.getParameter("sort");
		String orderType = request.getParameter("order");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		if (StringUtils.isEmpty(rows)) 
			rows = "30";
		if (StringUtils.isEmpty(page)) 
			page = "1";
		

		int pageSize = Integer.parseInt(rows);
		int currentPage = Integer.parseInt(page);
		int startRow = (currentPage - 1) * pageSize + 1;
		int endRow = currentPage * pageSize;


		String begin_user_id = request.getParameter("begin_user_id");
		String end_user_id = request.getParameter("end_user_id");
		String user_name = request.getParameter("user_name");
		String user_truename = request.getParameter("user_truename");
		String user_phone = request.getParameter("user_phone");

		StringBuffer whereSQL = new StringBuffer();
		if (StringUtils.isNotEmpty(begin_user_id)) {
			if (StringUtils.isEmpty(end_user_id)) 
				end_user_id = begin_user_id;
			whereSQL.append(" and userid >=" + begin_user_id + "");
			whereSQL.append(" and userid <=" + end_user_id + "");
		}

		if (StringUtils.isNotEmpty(user_name)) 
			whereSQL.append(" and username like '%" + user_name + "%'");

		if (StringUtils.isNotEmpty(user_truename)) 
			whereSQL.append(" and truename like '%" + user_truename + "%'");
		
		if (StringUtils.isNotEmpty(user_phone)) 
			whereSQL.append(" and telphone like '%" + user_phone + "%'");
	
		querySQL.append("select * \n");
		querySQL.append("  From (Select rownum rn, a.*  \n");
		querySQL.append("         From ( \n");
		querySQL.append("Select * ");
		querySQL.append(" From Userinfo");
		querySQL.append(" where 1 = 1");
		querySQL.append(whereSQL);
		if (StringUtils.isNotEmpty(orderField)) {
			querySQL.append(" order by " + orderField + " " + orderType + "");
		} else {
			querySQL.append(" order by userid asc");
		}
		querySQL.append(") a \n");
		querySQL.append("        where rownum <= " + endRow + ") t \n");
		querySQL.append(" where t.rn >= " + startRow + "");

		StringBuffer countSQL = new StringBuffer();
		countSQL.append("Select count(1) as rsCount");
		countSQL.append(" From Userinfo");
		countSQL.append(" where 1 = 1");
		countSQL.append(whereSQL);
		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();

			rs = stmt.executeQuery(countSQL.toString());
			if (rs.next()) {
				roleCount = rs.getInt("rsCount");
			} else {
				roleCount = 0;
			}
			rs.close();
			rs = null;
			QueryRunner queryRunner = new QueryRunner();
			userList = queryRunner.query(conn, querySQL.toString(),
					new BeanListHandler<UserBean>(UserBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		dataMap.put("total", roleCount);
		dataMap.put("rows", userList);
		return dataMap;
}

	public Map<String, Object> saveUser(UserBean userBean) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		int userid = userBean.getUserid();
		String username = userBean.getUsername();
		String truename = userBean.getTruename();
		String usersex = userBean.getUsersex();
		String address = userBean.getAddress();
		List<String> roleListChk = userBean.getRoleListChk();

		Connection conn = null;
		Statement stmt = null;
		StringBuffer insertSQL = new StringBuffer();
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			/**
			 * 1��д�뵽�û���
			 */
			insertSQL.append("Insert into UserInfo(");
			insertSQL.append(" userid,username,password,truename,");
			insertSQL.append(" usersex,address");
			insertSQL.append(" ) values(");
			insertSQL.append("'" + userid + "',");
			insertSQL.append("'" + username + "',");
			insertSQL.append("888888,");
			insertSQL.append("'" + truename + "',");
			insertSQL.append("'" + usersex + "',");
			insertSQL.append("'" + address + "'");
			insertSQL.append(")");

			stmt.executeUpdate(insertSQL.toString());

			/**
			 * 2��д�뵽��ɫ/�˵���
			 */
			if (roleListChk != null && roleListChk.size() > 0) {
				for (String role_id : roleListChk) {
					insertSQL.setLength(0);
					insertSQL.append("Insert Into Sys_User_Role(");
					insertSQL.append(" user_id,role_id");
					insertSQL.append(" ) values(");
					insertSQL.append("'" + userid + "',");
					insertSQL.append("'" + role_id + "'");
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

	public UserBean edit(HttpServletRequest request) {

		String userid = request.getParameter("userid");
		UserBean userBean = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		querySQL.setLength(0);
		querySQL.append("Select *  From Userinfo");
		querySQL.append(" where 1 = 1");
		querySQL.append(" and userid = '" + userid + "'");

		try {
			conn = JdbcUtil.getConn();
			QueryRunner queryRunner = new QueryRunner();
			userBean = queryRunner.query(conn, querySQL.toString(),
					new BeanHandler<UserBean>(UserBean.class));
			if (userBean != null) {
				/**
				 * ���û�_��ɫ����ȡ��Ӧ�����
				 */
				querySQL.setLength(0);
				querySQL.append("Select role_id");
				querySQL.append(" From Sys_User_Role");
				querySQL.append(" where 1 = 1");
				querySQL.append(" and user_id = '" + userid + "'");

				List role_idList = queryRunner.query(conn, querySQL.toString(),
						new ColumnListHandler());
				List<String> roleListChk = new ArrayList<String>();
				for (int i = 0; i < role_idList.size(); i++) {
					roleListChk.add(String.valueOf(role_idList.get(i)));
				}
				userBean.setRoleListChk(roleListChk);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return userBean;
	}

	public Map<String, Object> deleteUser(String delete_user_id) {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		StringBuffer delSQL = new StringBuffer();
		delSQL.setLength(0);
		delSQL.append("Delete From userinfo");
		delSQL.append(" where 1 = 1");
		delSQL.append(" and userid in (" + delete_user_id + ")");

		StringBuffer delSQL2 = new StringBuffer();
		delSQL2.setLength(0);
		delSQL2.append("Delete From sys_user_role");
		delSQL2.append(" where 1 = 1");
		delSQL2.append(" and user_id in (" + delete_user_id + ")");
		try {

			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			stmt.executeUpdate(delSQL.toString());
			stmt.executeUpdate(delSQL2.toString());
			dataMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", false);
			dataMap.put("errormsg", e.getMessage());
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return dataMap;
	}

	public Map<String, Object> modifyUser(UserBean userBean) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		int userid = userBean.getUserid();
		String username = userBean.getUsername();
		String truename = userBean.getTruename();
		String usersex = userBean.getUsersex();
		String address = userBean.getAddress();
		List<String> roleListChk = userBean.getRoleListChk();

		Connection conn = null;
		Statement stmt = null;
		StringBuffer insertSQL = new StringBuffer();
		StringBuffer updateSQL = new StringBuffer();
		StringBuffer delSQL = new StringBuffer();
		try {
			conn = JdbcUtil.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			/**
			 * 1：更新到用户表
			 */
			

			updateSQL.append("update userinfo set ");
			updateSQL.append("username = '" + username +"',");
			updateSQL.append("truename ='" + truename +"',");
			updateSQL.append("usersex ='" + usersex +"',");
			updateSQL.append("address ='" + address + "'");
			updateSQL.append(" where  1 = 1");
			updateSQL.append(" and userid = '"+userid+"'");
			System.out.println(updateSQL.toString());
			stmt.executeUpdate(updateSQL.toString());

			/**
			 * 2：删除用户原有的角色权限信息
			 * 
			 */
			delSQL.append("Delete From Sys_User_role ");
			delSQL.append(" where  1 = 1");
			delSQL.append(" and user_id = '"+userid+"'");
			stmt.executeUpdate(delSQL.toString());
			

			/**
			 * 3：写入到角色/菜单表
			 */
			if (roleListChk != null && roleListChk.size() > 0) {
				for (String role_id : roleListChk) {
					insertSQL.setLength(0);
					insertSQL.append("Insert Into Sys_User_Role(");
					insertSQL.append(" user_id,role_id");
					insertSQL.append(" ) values(");
					insertSQL.append("'" + userid + "',");
					insertSQL.append("'" + role_id + "'");
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

}
