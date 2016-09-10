package com.ztenc.oa.proj.bean;

/**
 * WlReplymessage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WlReplymessage implements java.io.Serializable {

	// Fields

	private Integer fbId;
	private String resId;

	// Constructors

	/** default constructor */
	public WlReplymessage() {
	}

	/** full constructor */
	public WlReplymessage(String resId) {
		this.resId = resId;
	}

	// Property accessors

	public Integer getFbId() {
		return this.fbId;
	}

	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}

	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

}