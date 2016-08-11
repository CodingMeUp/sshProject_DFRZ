package com.cyn.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cyn.entity.SysProvince;
import com.cyn.service.DataService;
import com.cyn.utils.BaseAction;
import com.google.gson.Gson;

public class DataAction extends BaseAction {

	private DataService dataService;
	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	
	private static final long serialVersionUID = 6431634295825939985L;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
  //  加载省份的
	public String loadProvCombobox() throws Exception {
		ArrayList<Map<String, Object>>  list = dataService.loadProv();
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		System.out.println("province"+jsonStr);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	//加载县市地区的
	public String loadCityCombobox() throws Exception {
		ArrayList<Map<String, Object>>  list = dataService.loadCity(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		System.out.println("city"+jsonStr);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}
	//加载乡镇的
	public String loadAreaCombobox() throws Exception {
		ArrayList<Map<String, Object>>  list = dataService.loadArea(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(list);
		System.out.println("araea"+jsonStr);
		out.println(jsonStr);
		out.flush();
		out.close();
		return NONE;
	}

}
