package com.ztenc.oa.proj.bean;

/**
 * CoursewareSubject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CoursewareSubject implements java.io.Serializable {

	// Fields

	private Integer coursewareno;
	private String coursewarename;
	private String imgurl;

	// Constructors

	/** default constructor */
	public CoursewareSubject() {
	}

	/** full constructor */
	public CoursewareSubject(String coursewarename, String imgurl) {
		this.coursewarename = coursewarename;
		this.imgurl = imgurl;
	}

	// Property accessors

	public Integer getCoursewareno() {
		return this.coursewareno;
	}

	public void setCoursewareno(Integer coursewareno) {
		this.coursewareno = coursewareno;
	}

	public String getCoursewarename() {
		return this.coursewarename;
	}

	public void setCoursewarename(String coursewarename) {
		this.coursewarename = coursewarename;
	}

	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}