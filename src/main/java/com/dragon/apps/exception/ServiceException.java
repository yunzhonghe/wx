package com.dragon.apps.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1114120958963983122L;
	private int code;
	// 动态拼装错误信息的占位符
	private String[] array;

	public ServiceException(int code, String... array) {
		this.code = code;
		this.array = array;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String[] getArray() {
		return array;
	}

	public void setArray(String[] array) {
		this.array = array;
	}

}
