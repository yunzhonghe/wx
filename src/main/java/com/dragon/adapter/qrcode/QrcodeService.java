package com.dragon.adapter.qrcode;

import com.dragon.adapter.NeedFix;
import com.dragon.apps.service.QrcodeHandleService;
import com.dragon.spider.api.response.QrcodeResponse;

public class QrcodeService {
//	private QrcodeHandleService service = null;
	
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=生成带参数的二维码
	 * POST数据例子：临时{"expire_seconds": 1800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * POST数据例子：永久{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * @param actionName二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 * @param actionInfo二维码详细信息
	 * scene_id:场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @param expireSeconds该二维码有效时间，以秒为单位。 最大不超过1800。
	 * @return
	 */
	public Object createQrcode(String actionName, int scene_id, Integer expireSeconds,String originalId){
		Object result = true;
		QrcodeHandleService service = new QrcodeHandleService(NeedFix.getApiConfig(originalId));
		//FIXME 1, service.createQrcode has not been finished.
		QrcodeResponse response = service.createQrcode(actionName, scene_id+"", expireSeconds);
		result = QrcodeAdapter.getModelByResponse(response);
		return result;
	}
	
	private static QrcodeService instance = null;
	public static QrcodeService getInstance(){
		if(instance==null){
			synchronized (QrcodeService.class) {
				if(instance==null)
					instance = new QrcodeService();
			}
		}
		return instance;
	}
	private QrcodeService(){
//		service = new QrcodeHandleService(NeedFix.getApiConfig());
	}
}
