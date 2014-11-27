package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

/**
 * 微信公众号给粉丝手动发送的信息记录
 * @author LiuJian
 */
public class WxAccountMessage extends Model<WxAccountMessage> {
	private static final long serialVersionUID = 1L;
	public static final WxAccountMessage dao = new WxAccountMessage();
	public static final String tableName = "wx_account_message";


	public static final String id = "id";//long
	private String messageId = "message_id";//string
	private String from = "from";//string,wx_account_originalid
	private String to = "to";//string,openid
	private String content = "content";//varchar(1024),如果是文本，则直接写入该字段；非文本则写入其它表主键id.
	private String type = "type";//int
	private String createTime = "create_time";//long

	private WxMsgImageModel wxMsgImageModel = null;
	private WxMsgLinkModel wxMsgLinkModel = null;
	private WxMsgLocationModel wxMsgLocationModel = null;
//	private WxMsgTextModel wxMsgTextModel = null;
	private WxMsgVideoModel wxMsgVideoModel = null;
	private WxMsgVoiceModel wxMsgVoiceModel = null;
	public WxMsgImageModel getWxMsgImageModel() {
		return wxMsgImageModel;
	}
	public void setWxMsgImageModel(WxMsgImageModel wxMsgImageModel) {
		this.wxMsgImageModel = wxMsgImageModel;
	}
	public WxMsgLinkModel getWxMsgLinkModel() {
		return wxMsgLinkModel;
	}
	public void setWxMsgLinkModel(WxMsgLinkModel wxMsgLinkModel) {
		this.wxMsgLinkModel = wxMsgLinkModel;
	}
	public WxMsgLocationModel getWxMsgLocationModel() {
		return wxMsgLocationModel;
	}
	public void setWxMsgLocationModel(WxMsgLocationModel wxMsgLocationModel) {
		this.wxMsgLocationModel = wxMsgLocationModel;
	}
//	public WxMsgTextModel getWxMsgTextModel() {
//		return wxMsgTextModel;
//	}
//	public void setWxMsgTextModel(WxMsgTextModel wxMsgTextModel) {
//		this.wxMsgTextModel = wxMsgTextModel;
//	}
	public WxMsgVideoModel getWxMsgVideoModel() {
		return wxMsgVideoModel;
	}
	public void setWxMsgVideoModel(WxMsgVideoModel wxMsgVideoModel) {
		this.wxMsgVideoModel = wxMsgVideoModel;
	}
	public WxMsgVoiceModel getWxMsgVoiceModel() {
		return wxMsgVoiceModel;
	}
	public void setWxMsgVoiceModel(WxMsgVoiceModel wxMsgVoiceModel) {
		this.wxMsgVoiceModel = wxMsgVoiceModel;
	}
	public Long getId() {
		return getLong(id);		
	}
	public WxAccountMessage setId(Long id) {
		return set(WxAccountMessage.id,id);
	}
	public String getMessageId() {
		return getStr(messageId);
	}
	public WxAccountMessage setMessageId(String messageId) {
		return set(this.messageId,messageId);		
	}
	public String getFrom() {
		return getStr(from);
	}
	public WxAccountMessage setFrom(String from) {
		return set(this.from,from);
	}
	public String getTo() {
		return getStr(to);
	}
	public WxAccountMessage setTo(String to) {
		return set(this.to,to);
	}
	/**
	 * varchar(1024),如果是文本，则直接写入该字段；非文本则写入其它表主键id.
	 * @return
	 */
	public String getContent() {
		return getStr(content);
	}
	public WxAccountMessage setContent(String content) {
		return set(this.content,content);
	}
	public Integer getType() {
		return getInt(type);
	}
	public WxAccountMessage setType(Integer type) {
		return set(this.type,type);
	}
	public Long getCreateTime() {
		return getLong(createTime);
	}
	public WxAccountMessage setCreateTime(Long createTime) {
		return set(this.createTime,createTime);
	}
}
