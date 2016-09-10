package com.ztenc.oa.proj.bean;

/**
 * CataGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CataGroup implements java.io.Serializable {

	// Fields

	private String proid;
	private Integer groupid;

	// Constructors

	/** default constructor */
	public CataGroup() {
	}

	/** full constructor */
	public CataGroup(String proid, Integer groupid) {
		this.proid = proid;
		this.groupid = groupid;
	}

	// Property accessors

	public String getProid() {
		return this.proid;
	}

	public void setProid(String proid) {
		this.proid = proid;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

}