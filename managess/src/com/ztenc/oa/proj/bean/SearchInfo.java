package com.ztenc.oa.proj.bean;

/**
 * SearchInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SearchInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String pic;
	private String uri;
	private String colcom;

	// Constructors

	/** default constructor */
	public SearchInfo() {
	}

	/** minimal constructor */
	public SearchInfo(String name, String pic) {
		this.name = name;
		this.pic = pic;
	}

	/** full constructor */
	public SearchInfo(String name, String pic, String uri, String colcom) {
		this.name = name;
		this.pic = pic;
		this.uri = uri;
		this.colcom = colcom;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getColcom() {
		return this.colcom;
	}

	public void setColcom(String colcom) {
		this.colcom = colcom;
	}

}