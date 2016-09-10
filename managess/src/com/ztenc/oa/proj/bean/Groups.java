package com.ztenc.oa.proj.bean;

/**
 * Groups entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Groups implements java.io.Serializable {

	// Fields

	private Integer groupid;
	private String groupname;
	private String memo;

	// Constructors

	/** default constructor */
	public Groups() {
	}

	/** minimal constructor */
	public Groups(Integer groupid, String groupname) {
		this.groupid = groupid;
		this.groupname = groupname;
	}

	/** full constructor */
	public Groups(Integer groupid, String groupname, String memo) {
		this.groupid = groupid;
		this.groupname = groupname;
		this.memo = memo;
	}

	// Property accessors

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}