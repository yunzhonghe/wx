package com.dragon.apps.model;

import java.sql.Timestamp;
import java.util.List;

import com.dragon.apps.exception.ErrorCode;
import com.dragon.apps.exception.ServiceException;
import com.jfinal.plugin.activerecord.Model;

/**
 * 实现微信用户的聊天的功能
 * 
 * @author chenlong
 * @param <WeChatModel>
 * 
 */
public class WxMessageModel extends Model<WxMessageModel> {

	private static final long serialVersionUID = 1L;
	private static final WxMessageModel dao = new WxMessageModel();

	private String id = "id";
	private String messageId = "message_id";
	private String from = "from";
	private String to = "to";
	private String content = "content";
	private String type = "type";
	private String createTime = "create_time";
	private String rspContent = "rsp_content";
	private String rspType = "rsp_type";
	private String rspTime = "rsp_time";

	private WxMessageModel() {

	}

	public static WxMessageModel getInstance() {
		return dao;
	}

	public WxMessageModel getByToken(String token) throws ServiceException {
		List<WxMessageModel> lists = dao.find("select * from wx_account where token = " + token);
		if (null == lists || lists.size() > 1) {
			throw new ServiceException(ErrorCode.TOKENERROR);
		} else {
			return lists.get(0);
		}
	}

	public long getId() {
		return getLong(id);		
	}

	public WxMessageModel setId(long id) {
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

	public String getContent() {
		return getStr(content);
	}

	public WxMessageModel setContent(String content) {
		return set(this.content,content);
	}

	public int getType() {
		return getInt(type);
	}

	public WxMessageModel setType(int type) {
		return set(this.type,type);
	}

	public Long getCreateTime() {
		return getLong(createTime);
	}

	public WxMessageModel setCreateTime(long createTime) {
		return set(this.createTime,createTime);
	}

	public String getRspContent() {
		return getStr(rspContent);
	}

	public WxMessageModel setRspContent(String rspContent) {
		return set(this.rspContent,rspContent);
	}

	public int getRspType() {
		return getInt(rspType);
	}

	public WxMessageModel setRspType(int rspType) {
		return set(this.rspType,rspType);
	}

	public Long getRspTime() {
		return getLong(rspTime);
	}

	public WxMessageModel setRspTime(long rspTime) {
		return set(this.rspTime,rspTime);
	}
}
