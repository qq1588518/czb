package com.ztenc.oa.proj.util;

public class ServiceException extends Exception {
  /** 字段内容说明 */
	private static final long serialVersionUID = 1L;

public ServiceException() {}
  
  public ServiceException(String message) {
    	super(message);
  }
}
