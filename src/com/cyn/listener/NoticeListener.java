package com.cyn.listener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import com.cyn.utils.*;
public class NoticeListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("工程销毁....");

	}

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("工程启动....");
		Timer timer = new Timer();
		final ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		
		TimerTask  task=new TimerTask() {
			@Override
			public void run() {

				System.out.println("执行任务...");
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				String sql = "Select notice_id,notice_addtime From sys_Notice where is_approve = 0";
				String updateSQL = null;
				String approive_time = DateUtil.getCurrentTime();
				try {
					DataSource dataSource = (DataSource) context
							.getBean("dataSource");
					conn = dataSource.getConnection();
					stmt = conn.createStatement();
					rs = stmt.executeQuery(sql);
					while (rs.next()) {
						String notice_id = rs.getString("notice_id");
						String notice_additme = rs.getString("notice_addtime");
						String current_day = DateUtil.getCurrentDate();
						int diffDay = DateUtil.daysBetween(notice_additme,
								current_day);
						if (diffDay >= 3) {
							updateSQL = "Update Sys_Notice set is_approve = 1,approve_user = '系统自动审核',approve_time = '"
									+ approive_time
									+ "' where notice_id = '"
									+ notice_id + "'";
							stmt.addBatch(updateSQL);
						}
					}
					stmt.executeBatch();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 关闭连接
				}
			}
		};
		
		
		timer.schedule(task, 3000*10, 10 * 1000*1000);
	}

}
