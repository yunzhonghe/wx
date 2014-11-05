package com.dragon.apps.model;

import java.util.List;

import com.dragon.apps.exception.ErrorCode;
import com.dragon.apps.exception.ServiceException;
import com.jfinal.plugin.activerecord.Model;

/**
 * 实现微信入口的所有的操作
 * @author chenlong
 *
 */
public class WeChatModel extends Model<WeChatModel>{
	
	private static final long serialVersionUID = 1L;
	private static final WeChatModel dao = new WeChatModel();
	
	private WeChatModel(){
		
	}
	
	public static WeChatModel getInstance(){
		return dao;
	}
	
	public WeChatModel getByToken(String token) throws ServiceException{
		 List<WeChatModel> lists  =  dao.find("select * from wx_account where token = " + token);
		 if(null==lists ||lists.size()>1){
			 throw new ServiceException(ErrorCode.TOKENERROR);
		 }else{
			 return lists.get(0);
		 }
	}
}
