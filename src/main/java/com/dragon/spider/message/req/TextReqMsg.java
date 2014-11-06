package com.dragon.spider.message.req;

public final class TextReqMsg extends BaseReqMsg {

	private String content;

	public String getContent() {
		return content;
	}

	public TextReqMsg(String content) {
		super();
		this.content = content;
		setMsgType(ReqType.TEXT);
	}

	@Override
	public String toString() {
		return "TextReqMsg [content=" + content + ", toUserName=" + toUserName
				+ ", fromUserName=" + fromUserName + ", createTime="
				+ createTime + ", msgType=" + msgType + ", msgId=" + msgId
				+ "]";
	}

}
