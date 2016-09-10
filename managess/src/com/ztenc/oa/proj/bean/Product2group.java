package com.ztenc.oa.proj.bean;

/**
 * Product2group entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Product2group implements java.io.Serializable {

	// Fields

	private String productno;
	private String groupno;

	// Constructors

	/** default constructor */
	public Product2group() {
	}

	/** full constructor */
	public Product2group(String productno, String groupno) {
		this.productno = productno;
		this.groupno = groupno;
	}

	// Property accessors

	public String getProductno() {
		return this.productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getGroupno() {
		return this.groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno = groupno;
	}

}