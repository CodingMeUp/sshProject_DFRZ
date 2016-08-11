package com.cyn.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cyn.entity.SysArea;
import com.cyn.entity.SysCity;
import com.cyn.entity.SysProvince;

public class DataService {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ArrayList<Map<String, Object>> loadProv() {
		Session session = null;
		Query query = null;
		List<SysProvince>  list = null;
		Map<String, Object> map = null;
		ArrayList<Map<String, Object>> provinceList = new ArrayList<Map<String,Object>>();
		try {
			session = this.sessionFactory.getCurrentSession();
			String hql = "Select a From SysProvince a  order by province_id asc";
			query = session.createQuery(hql);
			list = query.list();
			for (SysProvince  province : list) {
				map = new HashMap<String, Object>();
				map.put("province_id", province.getProvinceId());
				map.put("province_name", province.getProvinceName());
				provinceList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provinceList;
	}
    
	
	public ArrayList<Map<String, Object>> loadCity(HttpServletRequest request) {
		Session session = null;
		Query query = null;
		List<SysCity>  list = null;
		Map<String, Object> map = null;
		ArrayList<Map<String, Object>> cityList = new ArrayList<Map<String,Object>>();
		String pid = request.getParameter("pid");
		BigDecimal big = new BigDecimal(pid);
		try {
			session = this.sessionFactory.getCurrentSession();
			String hql = "Select a From SysCity a  where   a.provinceId = ?  order by city_id asc";
			query = session.createQuery(hql).setBigDecimal(0, big);
			list = query.list();
			for (SysCity  city : list) {
				map = new HashMap<String, Object>();
				map.put("city_id", city.getCityId());
				map.put("city_name", city.getCityName());
				cityList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cityList;
	}
    
	
	
	public ArrayList<Map<String, Object>> loadArea(HttpServletRequest request) {
		Session session = null;
		Query query = null;
		List<SysArea>  list = null;
		Map<String, Object> map = null;
		ArrayList<Map<String, Object>> areaList = new ArrayList<Map<String,Object>>();
		String cid = request.getParameter("cid");
		BigDecimal big = new BigDecimal(cid);
		try {
			session = this.sessionFactory.getCurrentSession();
			String hql = "Select a From SysArea a  where   a.cityId = ?  order by area_id asc";
			query = session.createQuery(hql).setBigDecimal(0, big);
			list = query.list();
			for (SysArea  area : list) {
				map = new HashMap<String, Object>();
				map.put("area_id", area.getAreaId());
				map.put("area_name", area.getAreaName());
				areaList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return areaList;
	}

}
