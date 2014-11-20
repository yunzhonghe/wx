package com.dragon.apps.model;

import com.dragon.apps.utils.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
/**
 * wx_account微信帐号
 * @author LiuJian
 */
public class WxAccount extends Model<WxAccount>{
	private static final long serialVersionUID = 1L;
	public static WxAccount dao = new WxAccount();
	public static final String tableName = "wx_account";
	
	public WxAccType getWxAccType(){
		if(getTypeid()!=null){
			return WxAccType.dao.getWxAccTypeByCache(getTypeid());
		}
		return null;
	}
	public Long getIdByOriginalId(String originalId){
		if(StrUtils.isEmpty(originalId)){
			return null;
		}
		return Db.queryLong("select "+ID+" from "+tableName+" where "+ORIGINALID+"='"+originalId+"' limit 0,1");
	}
	
	public static final String ID = "id";//bigint,pk
	public static final String ACCOUNT = "account";
	public static final String NAME = "name";
	private static final String PASSWORD = "password";
	private static final String TYPEID = "typeid";//int
	private static final String ORIGINALID = "originalid";
	private static final String ISDEVMODE = "isdevmode";
	private static final String URL = "url";
	private static final String TOKEN = "token";
	private static final String STATUS = "status";
	private static final String AVATAR = "avatar";
	private static final String SECRETSET = "secretset";
	private static final String AUTHSTATUS = "authstatus";
	private static final String REGION = "region";
	private static final String FUNCTIONS = "functions";
	private static final String QRCODE = "qrcode";
	
	public Long getId() {
		return getLong(ID);
	}
	public WxAccount setId(Long id) {
		return set(ID,id);
	}
	public String getAccount() {
		return getStr(ACCOUNT);
	}
	public WxAccount setAccount(String account) {
		return set(ACCOUNT,account);
	}
	public String getName() {
		return getStr(NAME);
	}
	public WxAccount setName(String name) {
		return set(NAME,name);
	}
	public String getPassword() {
		return getStr(PASSWORD);
	}
	public WxAccount setPassword(String password) {
		return set(PASSWORD,password);
	}
	public Integer getTypeid() {
		return getInt(TYPEID);
	}
	public WxAccount setTypeid(int typeid) {
		return set(TYPEID,typeid);
	}
	public String getOriginalid() {
		return getStr(ORIGINALID);
	}
	public WxAccount setOriginalid(String originalid) {
		return set(ORIGINALID,originalid);
	}
	public String getIsdevmode() {
		return getStr(ISDEVMODE);
	}
	public WxAccount setIsdevmode(String isdevmode) {
		return set(ISDEVMODE,isdevmode);
	}
	public String getUrl() {
		return getStr(URL);
	}
	public WxAccount setUrl(String url) {
		return set(URL,url);
	}
	public String getToken() {
		return getStr(TOKEN);
	}
	public WxAccount setToken(String token) {
		return set(TOKEN,token);
	}
	public String getStatus() {
		return getStr(STATUS);
	}
	public WxAccount setStatus(String status) {
		return set(STATUS,status);
	}
	public String getAvatar() {
		return getStr(AVATAR);
	}
	public WxAccount setAvatar(String avatar) {
		return set(AVATAR,avatar);
	}
	public String getSecretset() {
		return getStr(SECRETSET);
	}
	public WxAccount setSecretset(String secretset) {
		return set(SECRETSET,secretset);
	}
	public String getAuthstatus() {
		return getStr(AUTHSTATUS);
	}
	public WxAccount setAuthstatus(String authstatus) {
		return set(AUTHSTATUS,authstatus);
	}
	public String getRegion() {
		return getStr(REGION);
	}
	public WxAccount setRegion(String region) {
		return set(REGION,region);
	}
	public String getFunctions() {
		return getStr(FUNCTIONS);
	}
	public WxAccount setFunctions(String functions) {
		return set(FUNCTIONS,functions);
	}
	public String getQrcode() {
		return getStr(QRCODE);
	}
	public WxAccount setQrcode(String qrcode) {
		return set(QRCODE,qrcode);
	}
}
