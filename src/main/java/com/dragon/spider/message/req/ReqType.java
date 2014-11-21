package com.dragon.spider.message.req;

public class ReqType {

	public static final String TEXT_NAME = "文本";
	public static final String TEXT = "text";
	public static final int TEXTID = 1;

	public static final String IMAGE_NAME = "图片";
	public static final String IMAGE = "image";
	public static final int IMAGEID = 2;

	public static final String LINK_NAME = "链接";
	public static final String LINK = "link";
	public static final int LINKID = 3;

	public static final String LOCATION_NAME = "位置";
	public static final String LOCATION = "location";
	public static final int LOCATIONID = 4;

	public static final String VOICE_NAME = "音频";
	public static final String VOICE = "voice";
	public static final int VOICEID = 5;

	public static final String VIDEO_NAME = "视频";
	public static final String VIDEO = "video";
	public static final int VIDEOID = 6;

	public static final String EVENT_NAME = "事件";
	public static final String EVENT = "event";
	public static final int EVENTID = 7;

	
	public static final String getTypeNameByTypeId(Integer typeId){
		if(typeId!=null)
			switch(typeId){
			case TEXTID:
				return TEXT_NAME;
			case IMAGEID:
				return IMAGE_NAME;
			case LINKID:
				return LINK_NAME;
			case LOCATIONID:
				return LOCATION_NAME;
			case VOICEID:
				return VOICE_NAME;
			case VIDEOID:
				return VIDEO_NAME;
			case EVENTID:
				return EVENT_NAME;
			}
		return "未知";
	}
}
