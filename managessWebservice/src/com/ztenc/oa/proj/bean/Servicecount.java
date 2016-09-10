package com.ztenc.oa.proj.bean;

/**
 * Servicecount entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Servicecount implements java.io.Serializable {

	// Fields

	private Integer id;
	private String cataName;
	private Integer count;
	private Integer item;
	private String meno;

	// Constructors

	/** default constructor */
	public Servicecount() {
	}

	/** minimal constructor */
	public Servicecount(String cataName, Integer count, Integer item) {
		this.cataName = cataName;
		this.count = count;
		this.item = item;
	}

	/** full constructor */
	public Servicecount(String cataName, Integer count, Integer item,
			String meno) {
		this.cataName = cataName;
		this.count = count;
		this.item = item;
		this.meno = meno;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCataName() {
		return this.cataName;
	}

	public void setCataName(String cataName) {
		this.cataName = cataName;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getItem() {
		return this.item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public String getMeno() {
		return this.meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

}