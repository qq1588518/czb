package com.ztenc.oa.proj.bean;

import java.util.Date;

/**
 * Product entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Product implements java.io.Serializable {

	// Fields

	private String productno;
	private String productname;
	private String imageurl;
	private String content;
	private Integer typeno;
	private String code;
	private String price;
	private String dvalue;
	private String pvvalue;
	private String introduceurl;
	private String experturl;
	private String useurl;
	private Integer level;
	private String standard;
	private Date createdate;

	// Constructors

	/** default constructor */
	public Product() {
	}

	/** minimal constructor */
	public Product(String productno, String productname, String content,
			Integer typeno) {
		this.productno = productno;
		this.productname = productname;
		this.content = content;
		this.typeno = typeno;
	}

	/** full constructor */
	public Product(String productno, String productname, String imageurl,String content,
			Integer typeno, String code, String price, String dvalue,
			String pvvalue, String introduceurl, String experturl,
			String useurl, Integer level, String standard,Date createdate) {
		this.productno = productno;
		this.productname = productname;
		this.imageurl = imageurl;
		this.content = content;
		this.typeno = typeno;
		this.code = code;
		this.price = price;
		this.dvalue = dvalue;
		this.pvvalue = pvvalue;
		this.introduceurl = introduceurl;
		this.experturl = experturl;
		this.useurl = useurl;
		this.level = level;
		this.standard = standard;
		this.createdate = createdate;
	}

	// Property accessors

	public String getProductno() {
		return this.productno;
	}

	public void setProductno(String productno) {
		this.productno = productno;
	}

	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	public String getImageurl() {
		return this.imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTypeno() {
		return this.typeno;
	}

	public void setTypeno(Integer typeno) {
		this.typeno = typeno;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDvalue() {
		return this.dvalue;
	}

	public void setDvalue(String dvalue) {
		this.dvalue = dvalue;
	}

	public String getPvvalue() {
		return this.pvvalue;
	}

	public void setPvvalue(String pvvalue) {
		this.pvvalue = pvvalue;
	}

	public String getIntroduceurl() {
		return this.introduceurl;
	}

	public void setIntroduceurl(String introduceurl) {
		this.introduceurl = introduceurl;
	}

	public String getExperturl() {
		return this.experturl;
	}

	public void setExperturl(String experturl) {
		this.experturl = experturl;
	}

	public String getUseurl() {
		return this.useurl;
	}

	public void setUseurl(String useurl) {
		this.useurl = useurl;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public String getStandard() {
		return this.standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

}