package com.dragon.apps.service;

import java.util.Date;
import java.util.List;

import com.dragon.apps.exception.ErrorCode;
import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.utils.DateUtils;
import com.dragon.spider.api.HttpClientAccountApi;
import com.dragon.spider.api.HttpClientMsgApi;
import com.dragon.spider.api.HttpClientUserApi;
import com.dragon.spider.api.PublicWxManager;
import com.dragon.spider.api.config.HttpClientApiConfig;

/**
 * 实现微信公众号的初始化动作，包含有微信的内容，微信粉丝，微信消息， 群发消息列表等初始化
 * 
 * @author dfb365005
 * 
 */

public class PublicInitService {

	/**
	 * 实现公众号的初始化导入
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public boolean init(String name, String password) throws ServiceException {

		if (null == name || password == null) {
			throw new ServiceException(ErrorCode.EMPTYNAMEORPWD);
		}
		// 如果微信账号在数据库中
		WxAccount account = new WxAccount();
		account = account.getByNameAndPwd(name, password);

		// 如果数据库中没有该账号，则验证这个账号并且保存在数据库中
		if (null == account) {

			// 实现微信的基本信息的录入
			account = new WxAccount();
			account.setName(name);
			account.setPassword(password);
			HttpClientApiConfig config = PublicWxManager.getConfig(name, password);
			HttpClientAccountApi api = new HttpClientAccountApi(config);
			api.getDetailAccount(account);
			account.save();

			// 实现微信粉丝的基本信息的录入.包含微信的列表和详细信息的录入
			final HttpClientUserApi uApi = new HttpClientUserApi(config);
			final List<WxFansModel> uModels = uApi.getAllPageUsers();

			if (null != uModels) {
				for (int i = 0; i < uModels.size(); i++) {
					uModels.get(i).save();
				}
			}
			// 开启线程实现用户的详细信息的导入<--因为用户详细信息每个都要请求一次，所以增加每次的请求的间隔，防止被封禁-->
			new Thread(new Runnable() {
				public void run() {
					for (int i = 0; i < uModels.size(); i++) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						uApi.getUserDetail(uModels.get(i));
						uModels.get(i).update();
					}
				}
			}).start();

			// 实现微信个人交互的消息的导入
			HttpClientMsgApi mApi = new HttpClientMsgApi(config);
			List<WxMessageModel> msgModels = mApi.getFirstPageMsgs(10000);
			if (null != msgModels) {
				for (int i = 0; i < msgModels.size(); i++) {
					msgModels.get(i).save();
				}
			}

			// 实现微信的群发的消息的导入
			List<WxMessageModel> msgAllModels = mApi.getAllSendMsgs(10000);
			if (null != msgAllModels) {
				for (int i = 0; i < msgAllModels.size(); i++) {
					msgAllModels.get(i).save();
				}
			}

		} else {
			// 一周的之外的需要更新对应的内容
			if (account.getModifyTime().before(DateUtils.getNowBeforeOneWeekDay())) {
				HttpClientApiConfig config = PublicWxManager.getConfig(name, password);
				HttpClientAccountApi api = new HttpClientAccountApi(config);
				api.getDetailAccount(account);
				account.update();
			}
		}

		return false;
	}

}
