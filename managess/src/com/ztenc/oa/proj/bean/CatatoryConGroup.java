package com.ztenc.oa.proj.bean;

/**
 * CatatoryConGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CatatoryConGroup implements java.io.Serializable {

	// Fields

	private String id;
	private Integer groupid;
	private String catagoryId;
	private String subCatagoryId;

	// Constructors

	/** default constructor */
	public CatatoryConGroup() {
	}

	/** full constructor */
	public CatatoryConGroup(String id, Integer groupid, String catagoryId,
			String subCatagoryId) {
		this.id = id;
		this.groupid = groupid;
		this.catagoryId = catagoryId;
		this.subCatagoryId = subCatagoryId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getCatagoryId() {
		return this.catagoryId;
	}

	public void setCatagoryId(String catagoryId) {
		this.catagoryId = catagoryId;
	}

	public String getSubCatagoryId() {
		return this.subCatagoryId;
	}

	public void setSubCatagoryId(String subCatagoryId) {
		this.subCatagoryId = subCatagoryId;
	}

}