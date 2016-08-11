package com.cyn.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.tree.RowMapper;
import javax.xml.registry.infomodel.User;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cyn.entity.NoticeBean;

public class NoticeService extends JdbcDaoSupport {
	private Reader reader = null;
	private InputStream inputStream = null;

	public Map<String, Object> saveNotice(NoticeBean noticeBean) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		/**
		 * 1:获取表单的参数
		 * 
		 */
		final String notice_title = StringUtils.trim(noticeBean
				.getNotice_title());
		final String notice_adduser = StringUtils.trim(noticeBean
				.getNotice_adduser());
		final String notice_addtime = StringUtils.trim(noticeBean
				.getNotice_addtime());
		final String notice_content = StringUtils.trim(noticeBean
				.getNotice_content());
		final String filename = StringUtils.trim(noticeBean
				.getNotice_fileFileName());
		File uploadFile = noticeBean.getNotice_file();
		final String is_approve = "0";
		/**
		 * 2:创建字符流与字节流
		 */

		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into Sys_Notice(");
		insertSQL.append(" notice_id,notice_title,");
		insertSQL.append(" notice_adduser,notice_addtime,");
		insertSQL.append(" notice_content,filename,");
		insertSQL.append(" filedata,is_approve");
		insertSQL.append(") values(");
		insertSQL.append(" SEQ_NOTICE.nextval,?,");
		insertSQL.append(" ?,?,");
		insertSQL.append(" ?,?,");
		insertSQL.append(" ?,?");
		insertSQL.append(")");
		// JdbcTemplate template = new JdbcTemplate();
		try {
			reader = new StringReader(notice_content);
			if (filename != null && !filename.equals("")) {
				inputStream = new FileInputStream(uploadFile);
			}

			Connection conn = this.getDataSource().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(insertSQL
					.toString());

			// template.update(insertSQL.toString(),
			// new PreparedStatementSetter() {

			// public void setValues(PreparedStatement pstmt)
			// throws SQLException {
			pstmt.setString(1, notice_title);
			pstmt.setString(2, notice_adduser);
			pstmt.setString(3, notice_addtime);
			pstmt.setClob(4, reader);
			pstmt.setString(5, filename);
			pstmt.setBlob(6, inputStream);
			pstmt.setString(7, is_approve);
			// }
			// });
			pstmt.executeUpdate();
			jsonMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("flag", false);
			jsonMap.put("message", e.getMessage());
			throw new RuntimeException(e);
		}
		return jsonMap;
	}

	public NoticeBean loadBeanByID(HttpServletRequest request) {

		NoticeBean noticeBean = null;
		StringBuffer whereSQL = new StringBuffer();
		String id = request.getParameter("noticeid");
		if (id == null) {
			whereSQL.append("rownum = 1");
		} else {
			int noticeid = Integer.parseInt(id);
			whereSQL.append("notice_id = " + noticeid);
		}

		JdbcTemplate template = this.getJdbcTemplate();
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("Select * From SYs_Notice");
		querySQL.append(" where 1 = 1");
		querySQL.append(" and " + whereSQL.toString() + " ");
		System.out.println(querySQL.toString());
		try {
			BeanPropertyRowMapper<NoticeBean> rowMapper = new BeanPropertyRowMapper<NoticeBean>(
					NoticeBean.class);
			List<NoticeBean> list = template.query(querySQL.toString(),
					rowMapper);
			if (list != null && list.size() > 0) {
				noticeBean = list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return noticeBean;
	}

	public Map<String, Object> updateNotice(NoticeBean noticeBean) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		/**
		 * 1:获取表单的参数
		 * 
		 */
		final String notice_title = StringUtils.trim(noticeBean
				.getNotice_title());
		final String notice_adduser = StringUtils.trim(noticeBean
				.getNotice_adduser());
		final String notice_addtime = StringUtils.trim(noticeBean
				.getNotice_addtime());
		final String notice_content = StringUtils.trim(noticeBean
				.getNotice_content());
		final String filename = StringUtils.trim(noticeBean
				.getNotice_fileFileName());
		File uploadFile = noticeBean.getNotice_file();
		final String is_approve = "0";
		/**
		 * 2:创建字符流与字节流
		 */

		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append(" update Sys_Notice set ");
		insertSQL.append(" notice_title = ?,");
		insertSQL.append(" notice_adduser = ?,notice_addtime = ?,");
		insertSQL.append(" notice_content = ?,filename = ?,");
		insertSQL.append(" filedata = ?,is_approve = ? where rownum = 1");
		try {
			reader = new StringReader(notice_content);
			if (filename != null && !filename.equals("")) {
				inputStream = new FileInputStream(uploadFile);
			}

			Connection conn = this.getDataSource().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(insertSQL
					.toString());

			pstmt.setString(1, notice_title);
			pstmt.setString(2, notice_adduser);
			pstmt.setString(3, notice_addtime);
			pstmt.setClob(4, reader);
			pstmt.setString(5, filename);
			pstmt.setBlob(6, inputStream);
			pstmt.setString(7, is_approve);
			pstmt.executeUpdate();
			jsonMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("flag", false);
			jsonMap.put("message", e.getMessage());
			throw new RuntimeException(e);
		}
		return jsonMap;
	}

	public Map<String, Object> show(HttpServletRequest request) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		List<String> titleList = new ArrayList<String>();
		List<Integer> idList = new ArrayList<Integer>();
		JdbcTemplate template = this.getJdbcTemplate();
		StringBuffer titleSQL = new StringBuffer();
		titleSQL
				.append("Select notice_title From SYs_Notice order by notice_id asc");
		StringBuffer idSQL = new StringBuffer();
		idSQL.append("Select notice_id From SYs_Notice order by notice_id asc");
		try {
			titleList = template
					.queryForList(titleSQL.toString(), String.class);
			idList = template.queryForList(idSQL.toString(), Integer.class);
			jsonMap.put("notice_list", titleList);
			jsonMap.put("notice_id", idList);
			jsonMap.put("flag", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("flag", false);
		}
		return jsonMap;
	}

}
