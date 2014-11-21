package com.dragon.apps.web.module.base;

import java.io.Serializable;

public class IdValueBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private Object id;
	private Object value;
	public IdValueBean(){}
	public IdValueBean(Object id,Object value){
		this.id = id;
		this.value = value;
	}
	public Object getId() {
		return id;
	}
	public void setId(Object id) {
		this.id = id;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
