package com.dragon.apps.model;

//import java.sql.Timestamp;
//import java.util.List;

//import com.dragon.apps.exception.ErrorCode;
//import com.dragon.apps.exception.ServiceException;
import com.dragon.apps.web.config.ClConfig;
import com.jfinal.plugin.activerecord.Model;

/**
 * 实现微信用户的关注和非关注以及对应的分组model
 * @author chenlong
 * @param <WeChatModel>
 */
public class WxFansModel extends Model<WxFansModel> {

	private static final long serialVersionUID = 1L;
	public static final WxFansModel dao = new WxFansModel();

	private String id = "id";//long
	private String openId = "open_id";//varchar
	private String wxAccountId = "wx_account_id";//long,关联微信号的主键
	private String name = "name";//varchar
	private String markName = "mark_name";//varchar
	private String location = "location";//varchar
	private String signature = "signature";//签名varchar
	private String subscribe = "subscribe";//int
	private String createTime = "create_time";//long
	private String sourceFrom = "source_from";//varchar
	private String groupId ="group_id";
	private String gender ="gender";
	
	public WxFansModel getByOpenId(String openId){
		return findFirst("select * from " +ClConfig.WX_FANS_TABLE + " where open_id = '" + openId+"'");
	}
	public WxFansInfo getWxFansInfoWithCheckDb(){
		if(wxFansInfo==null){
			if(getOpenId()!=null){
				wxFansInfo = WxFansInfo.dao.findByOpenId(getOpenId());
			}
		}
		return wxFansInfo;
	}
	//关联的详细信息
	private WxFansInfo wxFansInfo = null;
	public WxFansInfo getWxFansInfo() {
		return wxFansInfo;
	}
	public void setWxFansInfo(WxFansInfo wxFansInfo) {
		this.wxFansInfo = wxFansInfo;
	}
	public long getId() {
		return getLong(id);		
	}
	public WxFansModel setId(long id) {
		return set(this.id,id);
	}
	public String getOpenId() {
		return getStr(openId);
	}
	public WxFansModel setOpenId(String openId) {
		return set(this.openId,openId);		
	}
	public String getName() {
		return getStr(name);
	}
	public WxFansModel setName(String name) {
		return set(this.name,name);
	}
	public String getMarkName() {
		return getStr(markName);
	}
	public WxFansModel setMarkName(String markName) {
		return set(this.markName,markName);
	}
	public String getLocation() {
		return getStr(location);
	}
	public WxFansModel setLocation(String location) {
		return set(this.location,location);
	}
	public String getSignature() {
		return getStr(signature);
	}
	public WxFansModel setSignature(String signature) {
		return set(this.signature,signature);
	}
	public Long getCreateTime() {
		return getLong(createTime);
	}
	public WxFansModel setCreateTime(long createTime) {
		return set(this.createTime,createTime);
	}
	public int getSubscribe() {
		return getInt(subscribe);
	}
	public WxFansModel setSubscribe(int subscribe) {
		return set(this.subscribe,subscribe);
	}
	public String getSourceFrom() {
		return getStr(sourceFrom);
	}
	public WxFansModel setSourceFrom(String sourceFrom) {
		return set(this.sourceFrom,sourceFrom);
	}
	public Long getWxAccountId() {
		return getLong(wxAccountId);
	}
	public WxFansModel setWxAccountId(Long wxAccountId) {
		return set(this.wxAccountId,wxAccountId);
	}
	
	
	public int getGroupId() {
		return getInt(groupId);
	}
	public WxFansModel setGroupId(int groupId) {
		return set(this.groupId,groupId);
	}
	
	
	public int getGender() {
		return getInt(gender);
	}
	public WxFansModel setGender(int gender) {
		return set(this.gender,gender);
	}
}
