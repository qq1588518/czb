package com.ztenc.oa.proj.bean;

/**
 * ProAnswer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProAnswer implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String conUri;
	private String outUri;
	private String dates;
	private Integer typeno;

	// Constructors

	/** default constructor */
	public ProAnswer() {
	}

	/** minimal constructor */
	public ProAnswer(String title, String conUri, String dates) {
		this.title = title;
		this.conUri = conUri;
		this.dates = dates;
	}

	/** full constructor */
	public ProAnswer(String title, String conUri, String outUri, String dates,
			Integer typeno) {
		this.title = title;
		this.conUri = conUri;
		this.outUri = outUri;
		this.dates = dates;
		this.typeno = typeno;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getConUri() {
		return this.conUri;
	}

	public void setConUri(String conUri) {
		this.conUri = conUri;
	}

	public String getOutUri() {
		return this.outUri;
	}

	public void setOutUri(String outUri) {
		this.outUri = outUri;
	}

	public String getDates() {
		return this.dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public Integer getTypeno() {
		return this.typeno;
	}

	public void setTypeno(Integer typeno) {
		this.typeno = typeno;
	}

}