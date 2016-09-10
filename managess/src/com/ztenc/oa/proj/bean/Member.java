package com.ztenc.oa.proj.bean;

import java.util.Date;

/**
 * Member entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Member implements java.io.Serializable {

	// Fields

	private String memberno;
	private String membername;
	private String telno;
	private String code;
	private String machineid;
	private Date loginDate;
	private String imeinum;
	private String userAcc;
	private String pwd;
	private String vpdn;
	private Date enableDate;
	private Date disableDate;
	private String other;
	// Constructors

	/** default constructor */
	public Member() {
	}

	/** minimal constructor */
	public Member(String memberno, String membername, String telno) {
		this.memberno = memberno;
		this.membername = membername;
		this.telno = telno;
	}

	/** full constructor */
	public Member(String memberno, String membername, String telno,
			String code, String machineid, Date loginDate, String imeinum,
			String userAcc, String pwd,String vpdn,Date enableDate,Date disableDate,String other) {
		this.memberno = memberno;
		this.membername = membername;
		this.telno = telno;
		this.code = code;
		this.machineid = machineid;
		this.loginDate = loginDate;
		this.imeinum = imeinum;
		this.userAcc = userAcc;
		this.pwd = pwd;
		this.vpdn = vpdn;
		this.enableDate = enableDate;
		this.disableDate = disableDate;
		this.other = other;
	}

	// Property accessors

	public String getMemberno() {
		return this.memberno;
	}

	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}

	public String getMembername() {
		return this.membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getTelno() {
		return this.telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMachineid() {
		return this.machineid;
	}

	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getImeinum() {
		return this.imeinum;
	}

	public void setImeinum(String imeinum) {
		this.imeinum = imeinum;
	}

	public String getUserAcc() {
		return this.userAcc;
	}

	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getVpdn() {
		return this.vpdn;
	}

	public void setVpdn(String vpdn) {
		this.vpdn = vpdn;
	}
	
	public Date getEnableDate() {
		return this.enableDate;
	}

	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
	}
	
	public Date getDisableDate() {
		return this.disableDate;
	}

	public void setDisableDate(Date disableDate) {
		this.disableDate = disableDate;
	}
	
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}
}