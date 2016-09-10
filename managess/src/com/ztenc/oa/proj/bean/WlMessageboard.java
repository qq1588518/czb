package com.ztenc.oa.proj.bean;

/**
 * WlMessageboard entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WlMessageboard implements java.io.Serializable {

	// Fields

	private Integer fbId;
	private String corporation;
	private String tel;
	private String faBmsg;
	private String faDate;
	private String type;
	private Integer sfhf;
	private String userName;

	// Constructors

	/** default constructor */
	public WlMessageboard() {
	}

	/** minimal constructor */
	public WlMessageboard(String corporation, String faBmsg, String faDate,
			String type, Integer sfhf, String userName) {
		this.corporation = corporation;
		this.faBmsg = faBmsg;
		this.faDate = faDate;
		this.type = type;
		this.sfhf = sfhf;
		this.userName = userName;
	}

	/** full constructor */
	public WlMessageboard(String corporation, String tel, String faBmsg,
			String faDate, String type, Integer sfhf, String userName) {
		this.corporation = corporation;
		this.tel = tel;
		this.faBmsg = faBmsg;
		this.faDate = faDate;
		this.type = type;
		this.sfhf = sfhf;
		this.userName = userName;
	}

	// Property accessors

	public Integer getFbId() {
		return this.fbId;
	}

	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}

	public String getCorporation() {
		return this.corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFaBmsg() {
		return this.faBmsg;
	}

	public void setFaBmsg(String faBmsg) {
		this.faBmsg = faBmsg;
	}

	public String getFaDate() {
		return this.faDate;
	}

	public void setFaDate(String faDate) {
		this.faDate = faDate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSfhf() {
		return this.sfhf;
	}

	public void setSfhf(Integer sfhf) {
		this.sfhf = sfhf;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}