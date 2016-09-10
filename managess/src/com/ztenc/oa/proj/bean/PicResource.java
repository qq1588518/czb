package com.ztenc.oa.proj.bean;

/**
 * PicResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class PicResource implements java.io.Serializable {

	// Fields

	private String recordno;
	private String picAddr;

	// Constructors

	/** default constructor */
	public PicResource() {
	}

	/** minimal constructor */
	public PicResource(String recordno) {
		this.recordno = recordno;
	}

	/** full constructor */
	public PicResource(String recordno, String picAddr) {
		this.recordno = recordno;
		this.picAddr = picAddr;
	}

	// Property accessors

	public String getRecordno() {
		return this.recordno;
	}

	public void setRecordno(String recordno) {
		this.recordno = recordno;
	}

	public String getPicAddr() {
		return this.picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

}