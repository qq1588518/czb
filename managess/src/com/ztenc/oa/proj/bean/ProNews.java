package com.ztenc.oa.proj.bean;

/**
 * ProNews entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProNews implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String conUri;
	private String outUri;
	private String dates;

	// Constructors

	/** default constructor */
	public ProNews() {
	}

	/** minimal constructor */
	public ProNews(String title, String conUri, String dates) {
		this.title = title;
		this.conUri = conUri;
		this.dates = dates;
	}

	/** full constructor */
	public ProNews(String title, String conUri, String outUri, String dates) {
		this.title = title;
		this.conUri = conUri;
		this.outUri = outUri;
		this.dates = dates;
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

}