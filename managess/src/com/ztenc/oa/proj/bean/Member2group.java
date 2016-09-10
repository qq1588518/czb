package com.ztenc.oa.proj.bean;

/**
 * Member2group entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Member2group implements java.io.Serializable {

	// Fields

	private String memberno;
	private String groupno;

	// Constructors

	/** default constructor */
	public Member2group() {
	}

	/** full constructor */
	public Member2group(String memberno, String groupno) {
		this.memberno = memberno;
		this.groupno = groupno;
	}

	// Property accessors

	public String getMemberno() {
		return this.memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}

	public String getGroupno() {
		return this.groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

}