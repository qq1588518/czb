package com.ztenc.oa.proj.bean;

/**
 * WlReply entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WlReply implements java.io.Serializable {

	// Fields

	private String resId;
	private String resadmin;
	private String reMsg;
	private String resdate;

	// Constructors

	/** default constructor */
	public WlReply() {
	}

	/** full constructor */
	public WlReply(String resadmin, String reMsg, String resdate) {
		this.resadmin = resadmin;
		this.reMsg = reMsg;
		this.resdate = resdate;
	}

	// Property accessors

	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getResadmin() {
		return this.resadmin;
	}

	public void setResadmin(String resadmin) {
		this.resadmin = resadmin;
	}

	public String getReMsg() {
		return this.reMsg;
	}

	public void setReMsg(String reMsg) {
		this.reMsg = reMsg;
	}

	public String getResdate() {
		return this.resdate;
	}

	public void setResdate(String resdate) {
		this.resdate = resdate;
	}

}