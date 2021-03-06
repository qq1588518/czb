package com.ztenc.oa.proj.bean;

import java.util.Date;


/**
 * Courseware generated by MyEclipse - Hibernate Tools
 */

public class Courseware  implements java.io.Serializable {


    // Fields    

     private String coursewareno;
     private String coursewarename;
     private String url;
     private Date createDate;
     private String otherUrl;
     private String titleimg;


    // Constructors

    /** default constructor */
    public Courseware() {
    }

	/** minimal constructor */
    public Courseware(String coursewareno, String coursewarename, String url) {
        this.coursewareno = coursewareno;
        this.coursewarename = coursewarename;
        this.url = url;
    }
    
    /** full constructor */
    public Courseware(String coursewareno, String coursewarename, String url, Date createDate, String otherUrl, String titleimg) {
        this.coursewareno = coursewareno;
        this.coursewarename = coursewarename;
        this.url = url;
        this.createDate = createDate;
        this.otherUrl = otherUrl;
        this.titleimg = titleimg;
    }

   
    // Property accessors

    public String getCoursewareno() {
        return this.coursewareno;
    }
    
    public void setCoursewareno(String coursewareno) {
        this.coursewareno = coursewareno;
    }

    public String getCoursewarename() {
        return this.coursewarename;
    }
    
    public void setCoursewarename(String coursewarename) {
        this.coursewarename = coursewarename;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOtherUrl() {
        return this.otherUrl;
    }
    
    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }

    public String getTitleimg() {
        return this.titleimg;
    }
    
    public void setTitleimg(String titleimg) {
        this.titleimg = titleimg;
    }
   








}