package com.ztenc.oa.proj.bean;

/**
 * Subject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Subject implements java.io.Serializable {

	// Fields

	private Integer subjectno;
	private String subjectname;
	private String imgurl;
	private Integer level;
	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** full constructor */
	public Subject(String subjectname, String imgurl) {
		this.subjectname = subjectname;
		this.imgurl = imgurl;
	}

	// Property accessors

	public Integer getSubjectno() {
		return this.subjectno;
	}

	public void setSubjectno(Integer subjectno) {
		this.subjectno = subjectno;
	}

	public String getSubjectname() {
		return this.subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
	}

}