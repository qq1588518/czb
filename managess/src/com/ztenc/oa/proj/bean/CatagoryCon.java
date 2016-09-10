package com.ztenc.oa.proj.bean;

/**
 * CatagoryCon entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class CatagoryCon implements java.io.Serializable {

	// Fields

	private String id;
	private String title;
	private String con;
	private String pic;
	private String dates;
	private String conpic;

	// Constructors

	

	/** default constructor */
	public CatagoryCon() {
	}

	/** minimal constructor */
	public CatagoryCon(String id, String title, String con, String dates) {
		this.id = id;
		this.title = title;
		this.con = con;
		this.dates = dates;		 
	}

	/** full constructor */
	public CatagoryCon(String id, String title, String con, String pic,
			String dates, String conpic) {
		this.id = id;
		this.title = title;
		this.con = con;
		this.pic = pic;
		this.dates = dates;
		this.conpic = conpic;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCon() {
		return this.con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getDates() {
		return this.dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}
	
	public String getConpic() {
		return conpic;
	}

	public void setConpic(String conpic) {
		this.conpic = conpic;
	}

}