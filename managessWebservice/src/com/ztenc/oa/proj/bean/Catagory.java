package com.ztenc.oa.proj.bean;

/**
 * Catagory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Catagory implements java.io.Serializable {

	// Fields

	private Integer proId;
	private String proName;
	private Integer topproId;
	private String remark;
	private String images;
	private Integer permission;

	// Constructors

	/** default constructor */
	public Catagory() {
	}

	/** minimal constructor */
	public Catagory(String proName, Integer topproId, String images,
			Integer permission) {
		this.proName = proName;
		this.topproId = topproId;
		this.images = images;
		this.permission = permission;
	}

	/** full constructor */
	public Catagory(String proName, Integer topproId, String remark,
			String images, Integer permission) {
		this.proName = proName;
		this.topproId = topproId;
		this.remark = remark;
		this.images = images;
		this.permission = permission;
	}

	// Property accessors

	public Integer getProId() {
		return this.proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Integer getTopproId() {
		return this.topproId;
	}

	public void setTopproId(Integer topproId) {
		this.topproId = topproId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImages() {
		return this.images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

}