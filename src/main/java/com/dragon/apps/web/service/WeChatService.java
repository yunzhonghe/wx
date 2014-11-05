package com.dragon.apps.web.service;

import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.model.WeChatModel;



public class WeChatService {
	/**
	 * 用户签名验证， token级别，需要判断token是否存在数据库中
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public boolean doSigure(String token) throws ServiceException {
		WeChatModel model = WeChatModel.getInstance().getByToken(token);
		if (null != model) {
			return true;
		} else {
			return false;
		}
	}
}
