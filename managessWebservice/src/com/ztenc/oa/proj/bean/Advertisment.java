package com.ztenc.oa.proj.bean;

/**
 * Advertisment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Advertisment implements java.io.Serializable {

	// Fields

	private Integer id;
	private String advertisName;
	private String picAdr;
	private String nome;

	// Constructors

	/** default constructor */
	public Advertisment() {
	}

	/** minimal constructor */
	public Advertisment(String advertisName, String picAdr) {
		this.advertisName = advertisName;
		this.picAdr = picAdr;
	}

	/** full constructor */
	public Advertisment(String advertisName, String picAdr, String nome) {
		this.advertisName = advertisName;
		this.picAdr = picAdr;
		this.nome = nome;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdvertisName() {
		return this.advertisName;
	}

	public void setAdvertisName(String advertisName) {
		this.advertisName = advertisName;
	}

	public String getPicAdr() {
		return this.picAdr;
	}

	public void setPicAdr(String picAdr) {
		this.picAdr = picAdr;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}