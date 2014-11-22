package com.dragon.apps.web.module.base;

import java.io.Serializable;

import com.dragon.apps.utils.StrUtils;
import com.jfinal.plugin.activerecord.Model;

public class BaseService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	
	public static final String DELETE_SUCCESS = "删除成功";
	public static final String DELETE_FAIL = "删除失败";
	
	public String deleteBean(String id,Model<?> dao){
		return deleteBean(id, dao, DELETE_SUCCESS, DELETE_FAIL);
	}
	public String deleteBean(String id,Model<?> dao, String defaultSuccessResult,String defaultFailResult){
		if(StrUtils.isNotEmpty(id)){
			Model<?> bean = dao.findById(id);
			if(bean!=null){
				if(bean.delete()){
					return defaultSuccessResult;
				}else{
					return defaultFailResult;
				}
			}
		}
		return IN_PRARM_ERROR;
	}
}
