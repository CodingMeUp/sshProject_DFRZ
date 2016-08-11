package com.cyn.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SysCity entity. @author MyEclipse Persistence Tools
 */

public class SysCity implements java.io.Serializable {

	// Fields

	private BigDecimal cityId;
	private String cityName;


	private BigDecimal provinceId;

	
	
	private Set<SysArea> areaSet = new HashSet<SysArea>();
	// Constructors

	
	/** default constructor */
	public SysCity() {
	}

	/** full constructor */
	public SysCity(BigDecimal cityId, String cityName, BigDecimal provinceId) {
		this.cityId = cityId;
		this.cityName = cityName;
		this.provinceId = provinceId;
	}

	// Property accessors

	public BigDecimal getCityId() {
		return this.cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public BigDecimal getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(BigDecimal provinceId) {
		this.provinceId = provinceId;
	}

	public Set<SysArea> getAreaSet() {
		return areaSet;
	}

	public void setAreaSet(Set<SysArea> areaSet) {
		this.areaSet = areaSet;
	}
}