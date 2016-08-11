package com.cyn.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * SysProvince entity. @author MyEclipse Persistence Tools
 */

public class SysProvince implements java.io.Serializable {

	// Fields

	private BigDecimal provinceId;
	private String provinceName;
	private Set<SysCity> citySet = new HashSet<SysCity>();
	// Constructors

	/** default constructor */
	public SysProvince() {
	}


	public Set<SysCity> getCitySet() {
		return citySet;
	}


	public void setCitySet(Set<SysCity> citySet) {
		this.citySet = citySet;
	}


	/** full constructor */
	public SysProvince(BigDecimal provinceId, String provinceName) {
		this.provinceId = provinceId;
		this.provinceName = provinceName;
	}

	// Property accessors

	public BigDecimal getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(BigDecimal provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}