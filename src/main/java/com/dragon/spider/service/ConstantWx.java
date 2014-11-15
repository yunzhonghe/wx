package com.dragon.spider.service;

public class ConstantWx {
	
	public static final int subscribe =1 ;
	public static final int unSubscribe =0;
	
	public static String loginUrl = "https://mp.weixin.qq.com/cgi-bin/loginpage?t=wxm2-login&lang=zh_CN";

	public static class CsManager {
		public static String getSendMsgToUserUrl(String token) {
			return "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token;
		}
		
		public static String getTxtMsg(String uId,String content){
			return "{    \"touser\":\""+uId+"\",    \"msgtype\":\"text\",    \"text\":    {         \"content\":\""+content+"\"    }}";
		}
	}

	// JSON相关的常量

}
