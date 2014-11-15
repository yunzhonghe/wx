package com.dragon.adapter.fans;

import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxFansModel;
import com.dragon.spider.api.response.GetUserInfoResponse;

public class FansAdapter {
	public static WxFansModel getModelByResponse(GetUserInfoResponse response){
		WxFansModel model = null;
		if(response!=null){
			if(response.getErrcode()==null){
				model = new WxFansModel();
				model.setSubscribe(response.getSubscribe());
				model.setOpenId(response.getOpenid());
				WxFansInfo info = new WxFansInfo();
				model.setInfo(info);
				info.set(WxFansInfo.openId,response.getOpenid());
				info.set(WxFansInfo.nickname,response.getNickname());
				info.set(WxFansInfo.sex,response.getSex());
				info.set(WxFansInfo.city,response.getCity());
				info.set(WxFansInfo.country,response.getCountry());
				info.set(WxFansInfo.province,response.getProvince());
				info.set(WxFansInfo.language,response.getLanguage());
				info.set(WxFansInfo.headimgurl,response.getHeadimgurl());
				info.set(WxFansInfo.subscribe_time,response.getSubscribe_time());
				info.set(WxFansInfo.unionid,response.getUnionid());
			}
		}
		return model;
	}
}
