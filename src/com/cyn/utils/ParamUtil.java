package com.cyn.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ParamUtil {
	public Map<String, String> getUserParamMap(String userid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		StringBuffer querySQL = new StringBuffer();

		querySQL.append("Select param_key,param_value");
		querySQL.append(" From t_sys_user_param");
		querySQL.append(" where 1 = 1 ");
		querySQL.append(" and userid = '" + userid + "'");
		querySQL.append("  order by param_key asc");
		String param_key = null;
		String param_value = null;
		try {
			conn = JdbcUtil.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(querySQL.toString());
			while (rs.next()) {
				param_key = rs.getString("param_key");
				param_value = rs.getString("param_value");

				paramMap.put(param_key, param_value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.closeResource(rs, stmt, conn);
		}
		return paramMap;
	}
}
