package com.ztenc.oa.proj.bean;

import java.util.Date;

/**
 * Catagory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Catagory implements java.io.Serializable {

	// Fields

	private String proId;
	private String proName;
	private String topproId;
	private String remark;
	private String images;
	private Integer level;
	// Constructors

	/** default constructor */
	public Catagory() {
	}

	/** minimal constructor */
	public Catagory(String proId, String proName, String topproId,
			String images,Integer level) {
		this.proId = proId;
		this.proName = proName;
		this.topproId = topproId;
		this.images = images;
		this.level = level;
	}

	/** full constructor */
	public Catagory(String proId, String proName, String topproId,
			String remark, String images) {
		this.proId = proId;
		this.proName = proName;
		this.topproId = topproId;
		this.remark = remark;
		this.images = images;
	}

	// Property accessors

	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getProName() {
		return this.proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getTopproId() {
		return this.topproId;
	}

	public void setTopproId(String topproId) {
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
	
	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
	}

}