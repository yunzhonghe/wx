package com.dragon.apps.model;

import java.util.List;
import com.dragon.apps.exception.ErrorCode;
import com.dragon.apps.exception.ServiceException;
import com.jfinal.plugin.activerecord.Model;

/**
 * 实现微信用户的聊天的功能
 * @author chenlong
 */
public class WxMessageModel extends Model<WxMessageModel> {
	public static final WxMessageModel dao = new WxMessageModel();

	private static final long serialVersionUID = 1L;

	private String id = "id";//long
	private String messageId = "message_id";//string
	private String from = "from";//string
	private String to = "to";//string
	private String contentId = "content_id";//long
	private String type = "type";//int
	private String createTime = "create_time";//long
	private String rspContent = "rsp_content_id";//long
	private String rspType = "rsp_type";//int
	private String rspTime = "rsp_time";//long


	public WxMessageModel getByToken(String token) throws ServiceException {
		List<WxMessageModel> lists = this.find("select * from wx_account where token = " + token);
		if (null == lists || lists.size() != 1 ) {
			throw new ServiceException(ErrorCode.TOKENERROR);
		} else {
			return lists.get(0);
		}
	}

	private WxMsgImageModel wxMsgImageModel = null;
	private WxMsgLinkModel wxMsgLinkModel = null;
	private WxMsgLocationModel wxMsgLocationModel = null;
	private WxMsgTextModel wxMsgTextModel = null;
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
	public WxMsgTextModel getWxMsgTextModel() {
		return wxMsgTextModel;
	}
	public void setWxMsgTextModel(WxMsgTextModel wxMsgTextModel) {
		this.wxMsgTextModel = wxMsgTextModel;
	}
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
	public WxMessageModel setId(Long id) {
		return set(this.id,id);
	}
	public String getMessageId() {
		return getStr(messageId);
	}
	public WxMessageModel setMessageId(String messageId) {
		return set(this.messageId,messageId);		
	}
	public String getFrom() {
		return getStr(from);
	}
	public WxMessageModel setFrom(String from) {
		return set(this.from,from);
	}
	public String getTo() {
		return getStr(to);
	}
	public WxMessageModel setTo(String to) {
		return set(this.to,to);
	}
	public String getContentId() {
		return getStr(contentId);
	}
	public WxMessageModel setContentId(Long contentId) {
		return set(this.contentId,contentId);
	}
	public Integer getType() {
		return getInt(type);
	}
	public WxMessageModel setType(Integer type) {
		return set(this.type,type);
	}
	public Long getCreateTime() {
		return getLong(createTime);
	}
	public WxMessageModel setCreateTime(Long createTime) {
		return set(this.createTime,createTime);
	}
	public String getRspContent() {
		return getStr(rspContent);
	}
	public WxMessageModel setRspContent(String rspContent) {
		return set(this.rspContent,rspContent);
	}
	public Integer getRspType() {
		return getInt(rspType);
	}
	public WxMessageModel setRspType(Integer rspType) {
		return set(this.rspType,rspType);
	}
	public Long getRspTime() {
		return getLong(rspTime);
	}
	public WxMessageModel setRspTime(Long rspTime) {
		return set(this.rspTime,rspTime);
	}
}
