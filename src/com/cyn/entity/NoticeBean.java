package com.cyn.entity;

import java.io.File;
import java.io.Serializable;
import java.sql.Clob;

public class NoticeBean implements Serializable {
	private int notice_id;
	private String notice_title;
	private String notice_adduser;
	private String notice_addtime;
	private String notice_content;
	private File notice_file;
	private String notice_fileFileName;
	private String filename;

	private String is_approve;
	private String approve_time;
	private String approve_user;

	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public String getNotice_title() {
		return notice_title;
	}

	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}

	public String getNotice_adduser() {
		return notice_adduser;
	}

	public void setNotice_adduser(String notice_adduser) {
		this.notice_adduser = notice_adduser;
	}

	public String getNotice_addtime() {
		return notice_addtime;
	}

	public void setNotice_addtime(String notice_addtime) {
		this.notice_addtime = notice_addtime;
	}

	public String getNotice_content() {
		return notice_content;
	}

	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}

	public File getNotice_file() {
		return notice_file;
	}

	public void setNotice_file(File notice_file) {
		this.notice_file = notice_file;
	}

	public String getNotice_fileFileName() {
		return notice_fileFileName;
	}

	public void setNotice_fileFileName(String notice_fileFileName) {
		this.notice_fileFileName = notice_fileFileName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getIs_approve() {
		return is_approve;
	}

	public void setIs_approve(String is_approve) {
		this.is_approve = is_approve;
	}

	public String getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(String approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

}
