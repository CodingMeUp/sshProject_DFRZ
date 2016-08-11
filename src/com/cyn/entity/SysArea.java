package com.cyn.entity;

import java.math.BigDecimal;

/**
 * SysArea entity. @author MyEclipse Persistence Tools
 */

public class SysArea implements java.io.Serializable {

	// Fields

	private BigDecimal areaId;
	private String areaName;
	private BigDecimal cityId;

	// Constructors

	/** default constructor */
	public SysArea() {
	}

	/** full constructor */
	public SysArea(BigDecimal areaId, String areaName, BigDecimal cityId) {
		this.areaId = areaId;
		this.areaName = areaName;
		this.cityId = cityId;
	}

	// Property accessors

	public BigDecimal getAreaId() {
		return this.areaId;
	}

	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public BigDecimal getCityId() {
		return this.cityId;
	}

	public void setCityId(BigDecimal cityId) {
		this.cityId = cityId;
	}

}