package com.ztenc.oa.proj.bean;

/**
 * AnswerType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AnswerType implements java.io.Serializable {

	// Fields

	private Integer typeno;
	private String typename;
	private String images;
	private String remark;
	private Integer level;

	// Constructors

	/** default constructor */
	public AnswerType() {
	}

	/** minimal constructor */
	public AnswerType(String typename, String images) {
		this.typename = typename;
		this.images = images;
	}

	/** full constructor */
	public AnswerType(String typename, String images, String remark,
			Integer level) {
		this.typename = typename;
		this.images = images;
		this.remark = remark;
		this.level = level;
	}

	// Property accessors

	public Integer getTypeno() {
		return this.typeno;
	}

	public void setTypeno(Integer typeno) {
		this.typeno = typeno;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getImages() {
		return this.images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}