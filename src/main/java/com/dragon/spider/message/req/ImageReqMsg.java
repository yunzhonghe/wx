package com.dragon.spider.message.req;

/**
 * @author peiyu
 */
public final class ImageReqMsg extends BaseReqMsg {

	private String picUrl;
	private String mediaId;

	public String getPicUrl() {
		return picUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public ImageReqMsg(String picUrl, String mediaId) {
		super();
		this.picUrl = picUrl;
		this.mediaId = mediaId;
		setMsgType(ReqType.IMAGE);
	}

	@Override
	public String toString() {
		return "ImageReqMsg [picUrl=" + picUrl + ", mediaId=" + mediaId
				+ ", toUserName=" + toUserName + ", fromUserName="
				+ fromUserName + ", createTime=" + createTime + ", msgType="
				+ msgType + ", msgId=" + msgId + "]";
	}

}
