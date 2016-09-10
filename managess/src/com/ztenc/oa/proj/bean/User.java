package com.ztenc.oa.proj.bean;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String userno;
	private String username;
	private String telno;
	private String machineid;
	private String code;
	private String memo;
	private String password;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String username, String telno, String machineid) {
		this.username = username;
		this.telno = telno;
		this.machineid = machineid;
	}

	/** full constructor */
	public User(String username, String telno, String machineid, String code,
			String memo, String password) {
		this.username = username;
		this.telno = telno;
		this.machineid = machineid;
		this.code = code;
		this.memo = memo;
		this.password = password;
	}

	// Property accessors

	public String getUserno() {
		return this.userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelno() {
		return this.telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getMachineid() {
		return this.machineid;
	}

	public void setMachineid(String machineid) {
		this.machineid = machineid;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}