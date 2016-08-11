package com.cyn.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.cyn.utils.JdbcUtil;


public class LoginService {
	
	   /**
	    * * 登录验证的
	    * @param username
	    * @param password
	    * @return
	    */
	 
	    public Map<String, Object> loginResult(String username,String password){
	    	   
	    	
	    	Connection  conn  = null; 
	    	Map<String, Object> loginMap = new HashMap<String, Object>();
 			StringBuffer querySQL = new StringBuffer();
 			username = username.trim().toLowerCase(Locale.getDefault());
			querySQL.append("Select userid,username,password");
			querySQL.append(" From USERINFO");
			querySQL.append(" where  1 = 1 ");
			querySQL.append(" and username = '" + username +"' ");
			querySQL.append("  and password = '" + password +"'");
			try {
				conn = JdbcUtil.getConn();
				QueryRunner queryRunner = new QueryRunner();
                loginMap = queryRunner.query(conn, querySQL.toString(), new MapHandler()); 
                if(loginMap==null)
                	return null;
                else 
                	return loginMap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				DbUtils.closeQuietly(conn);
			}
	    }
	}
