package com.ztenc.oa.proj.bean;

/**
 * Courseware2subject entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Courseware2subject implements java.io.Serializable {

	// Fields

	private String coursewareno;
	private Integer subjectno;

	// Constructors

	/** default constructor */
	public Courseware2subject() {
	}

	/** full constructor */
	public Courseware2subject(String coursewareno, Integer subjectno) {
		this.coursewareno = coursewareno;
		this.subjectno = subjectno;
	}

	// Property accessors

	public String getCoursewareno() {
		return this.coursewareno;
	}

	public void setCoursewareno(String coursewareno) {
		this.coursewareno = coursewareno;
	}

	public Integer getSubjectno() {
		return this.subjectno;
	}

	public void setSubjectno(Integer subjectno) {
		this.subjectno = subjectno;
	}

}