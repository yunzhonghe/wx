package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;
import java.util.Date;
/**
 * wx_account_statusinfo
 * may be not use this table.
 * @author LiuJian
 */
public class WxAccStat extends Model<WxAccStat>{
	private static final long serialVersionUID = 1L;
	public static WxAccStat dao = new WxAccStat();
	
	public static String getTableName(){
		return "wx_account_statusinfo";
	}
	
	public static final String ID = "id";//int
	private static final String WXID = "wxid";//bigint
	private static final String SYNCDATE = "syncdate";//datetime
	private static final String NEWMSGNUM = "newmsgnum";//int
	private static final String NEWFANSNUM = "newfansnum";//int
	private static final String TOTALFANSNUM = "totalfansnum";//int

	public int getId() {
		return getInt(ID);
	}
	public WxAccStat setId(int id) {
		return set(ID,id);
	}
	public Long getWxid() {
		return getLong(WXID);
	}
	public WxAccStat setWxaccount(Long wxid) {
		return set(WXID,wxid);
	}
	public Date getSyncdate() {
		return getDate(SYNCDATE);
	}
	public WxAccStat setSyncdate(Date syncdate) {
		return set(SYNCDATE,syncdate);
	}
	public int getNewmsgnum() {
		return getInt(NEWMSGNUM);
	}
	public WxAccStat setNewmsgnum(int newmsgnum) {
		return set(NEWMSGNUM,newmsgnum);
	}
	public int getNewfansnum() {
		return getInt(NEWFANSNUM);
	}
	public WxAccStat setNewfansnum(int newfansnum) {
		return set(NEWFANSNUM,newfansnum);
	}
	public int getTotalfansnum() {
		return getInt(TOTALFANSNUM);
	}
	public WxAccStat setTotalfansnum(int totalfansnum) {
		return set(TOTALFANSNUM,totalfansnum);
	}
}
