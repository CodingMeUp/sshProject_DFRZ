package com.cyn.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyn.entity.NoticeBean;
import com.cyn.service.NoticeService;
import com.cyn.utils.BaseAction;
import com.cyn.utils.DateUtil;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 使用自动装配的方式与Spring结合
 * 
 * 使用Spring中的DI的方式注入Spring的业务对象
 * 
 * @author ctd
 * 
 */
public class NoticeAction extends BaseAction implements ModelDriven<NoticeBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NoticeBean noticeBean;

	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public NoticeBean getModel() {
		noticeBean = new NoticeBean();
		return noticeBean;
	}

	public String add() throws Exception {
		/**
		 * 1：获取当前的系统时间
		 * 
		 * 
		 */
		String currentDate = DateUtil.getCurrentDate();
		String firstDay = DateUtil.getCurrentMonthFirst();
		String lastDay = DateUtil.getCurrentMonthLast();

		/**
		 * 2:
		 */
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		Map<String, String> dataMap = new HashMap<String, String>();

		dataMap.put("notice_addtime", currentDate);
		dataMap.put("firstDay", firstDay);
		dataMap.put("lastDay", lastDay);

		valueStack.push(dataMap);

		return "add";
	}

	public String save() throws Exception {
		Map<String, Object> jsonMap = this.noticeService
				.saveNotice(this.noticeBean);

		return NONE;
	}

	public String update() throws Exception {
		Map<String, Object> jsonMap = this.noticeService
				.updateNotice(this.noticeBean);

		return NONE;
	}
	
	@Override
	public String execute() throws Exception {
		return null;
	}

	public String edit() throws Exception {
		this.noticeBean = this.noticeService.loadBeanByID(request);
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		String firstDay = DateUtil.getCurrentMonthFirst();
		String lastDay = DateUtil.getCurrentMonthLast();
		
		if (this.noticeBean != null) {
			valueStack.push(this.noticeBean);
			valueStack.set("firstDay", firstDay);
			valueStack.set("lastDay", lastDay);
		}
		return "edit";
	}
	
	public String showOnHome() throws Exception {
		Map<String, Object> dataMap = noticeService.show(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	
	
}
