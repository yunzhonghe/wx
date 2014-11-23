package com.dragon.apps.web.module.wxfans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dragon.adapter.fans.FansService;
import com.dragon.adapter.message.MessageService;
import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.model.WxMessageModel;
import com.dragon.apps.model.WxMsgTextModel;
import com.dragon.apps.model.WxTag;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.RoleUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.spider.message.req.ReqType;
import com.dragon.spider.service.ConstantWx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * 微信粉丝用户的业务操作，供wxfanscontroller调用
 * @author LiuJian
 */
public class WxFansService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	/**
	 * //超级管理员、所有粉丝
	 * @param con
	 * @param pageSet
	 * @return
	 */
	public PageSet getFansList(WxFansListCon con ,PageSet pageSet){
		String select = "select wf.open_id,wfi.nickname,wfi.sex,wfi.city,wfi.country,wfi.province,wfi.headimgurl,wa.account,wa.name";
		String center1 =  " from wx_fans wf left join wx_fans_info wfi on wfi.openid=wf.open_id"
						+ " left join wx_account wa on wf.wx_account_id=wa.id";
		String center2 = " where wf.subscribe="+ConstantWx.subscribe;
		if(con!=null){
			if(StrUtils.isNotEmpty(con.getNickName())){
				center2 += " and wfi.nickname like '"+con.getNickName()+"%'";
			}
			if(StrUtils.isNotEmpty(con.getAccountId())){
				center2 += " and wa.id = "+con.getLaccountId()+"";
			}
		}
		String center = center1+center2;
		String orderby = " order by wfi.subscribe_time desc";
		int count = Db.queryLong("select count(1) "+center).intValue();
		if (count > 0) {
			int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
			String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
			List<Record> ls = Db.find(sql);
			pageSet.setResultList(ls);
		}
		return pageSet;
	}
	/**
	 * 子管理员粉丝列表
	 * @param con
	 * @param pageSet
	 * @return
	 */
	public PageSet getFansSubList(WxFansListCon con ,PageSet pageSet){
		Long accountId = RoleUtils.gerCurAccountId();
		if(accountId!=null){
			String select = "select wf.open_id,wf.mark_name,wfi.nickname,wfi.sex,wfi.city,wfi.country,wfi.province,wfi.headimgurl";
			String center1 = " from wx_fans wf left join wx_fans_info wfi on wfi.openid=wf.open_id";
			String center2 = " where wf.subscribe='"+ConstantWx.subscribe+"' and wf.wx_account_id="+accountId;
			if(con!=null){
				if(StrUtils.isNotEmpty(con.getMarkName())){
					center2 += " and wf.mark_name like '"+con.getMarkName()+"%'";
				}
				if(StrUtils.isNotEmpty(con.getNickName())){
					center2 += " and wfi.nickname like '"+con.getNickName()+"%'";
				}
				if(StrUtils.isNotEmpty(con.getTagid())){
					center1 += " left join wx_fans_tag wft on wft.openid=wf.open_id";
					center2 += " and wft.tagid='"+con.getTagid()+"'";
				}
			}
			String center = center1+center2;
			String orderby = " order by wfi.subscribe_time desc";
			int count = Db.queryLong("select count(1) "+center).intValue();
			if (count > 0) {
				int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
				String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
				List<Record> ls = Db.find(sql);
				pageSet.setResultList(ls);
			}else{
				initFans();//call service to get fans in new thread.
			}
		}
		return pageSet;
	}
	/**
	 * 内部调用，初始化当前微信号的粉丝信息
	 */
	private void initFans(){
		new Thread(){
			public void run() {
				super.run();
				FansService.getInstance().initAllFansData();
			}
		}.start();
	}
	/**
	 * //超级管理员、所有消息的列表
	 * @param con
	 * @param pageSet
	 * @return
	 */
	public PageSet getHismsgList(WxHismsgListCon con ,PageSet pageSet){
		String select = "select m.message_id,m.type,FROM_UNIXTIME(m.create_time) as message_time,fi.headimgurl,fi.nickname,a.account,a.name,mt.content";
		String center1 =  " from wx_message m"
						+ " left join wx_fans_info fi on fi.openid=m.from"
						+ " left join wx_account a on a.originalid=m.to"
						+ " left join wx_message_text mt on mt.id=m.content_id and m.type="+ReqType.TEXTID
						;
		String center2 = "";
		String orderby = " order by m.create_time desc";
		StringBuilder queryCon = new StringBuilder();
		String timeLimit = null;
		if(con!=null){
			if(StrUtils.isNotEmpty(con.getAccountId())){
				queryCon.append(" and a.id="+con.getLaccountId());
			}
			if(StrUtils.isNotEmpty(con.getMsg())){
				queryCon.append(" and mt.content like '%"+con.getMsg()+"%'");
			}
			if(StrUtils.isNotEmpty(con.getTimeLimit())){
				if(!con.getTimeLimit().equals(WxHismsgListCon.TIMELIMIT_UNLIMIT)){
					timeLimit = con.getTimeLimit();
				}
			}else{
				timeLimit = WxHismsgListCon.TIMELIMIT_DEFAULT;
			}
		}else{
			timeLimit = WxHismsgListCon.TIMELIMIT_DEFAULT;
		}
		if(timeLimit!=null){
			queryCon.append(" and timestampdiff(day,now(),FROM_UNIXTIME(m.create_time))<"+timeLimit);
		}
		if(queryCon.length()>0){
			center2 = " where " + queryCon.substring(4);
		}
		String center = center1+center2;
		
		int count = Db.queryLong("select count(1) "+center).intValue();
		if (count > 0) {
			int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
			String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
			List<Record> ls = Db.find(sql);
			if(ls!=null && ls.size()>0){
				for(Record r : ls){
					Integer type = r.getInt("type");
					//FIXME 处理其它类型的内容
					r.set("type", ReqType.getTypeNameByTypeId(type));
					if(type==null || type!=ReqType.TEXTID){
						r.set("content", null);
					}
				}
			}
			pageSet.setResultList(ls);
		}
		return pageSet;
	}
	/**
	 * 子管理员、所有消息的列表
	 * @param con
	 * @param pageSet
	 * @return
	 */
	public PageSet getHismsgSubList(WxHismsgListCon con ,PageSet pageSet){
		Long accountId = RoleUtils.gerCurAccountId();
		if(accountId!=null){
			String select = "select m.message_id,m.type,FROM_UNIXTIME(m.create_time) as message_time,fi.headimgurl,fi.nickname,a.account,a.name,mt.content";
			String center1 =  " from wx_message m"
							+ " left join wx_fans_info fi on fi.openid=m.from"
							+ " left join wx_account a on a.originalid=m.to"
							+ " left join wx_message_text mt on mt.id=m.content_id and m.type="+ReqType.TEXTID
							;
			String center2 = " where a.id="+accountId;
			String orderby = " order by m.create_time desc";
			StringBuilder queryCon = new StringBuilder();
			String timeLimit = null;
			if(con!=null){
				if(StrUtils.isNotEmpty(con.getMsg())){
					queryCon.append(" and mt.content like '%"+con.getMsg()+"%'");
				}
				if(StrUtils.isNotEmpty(con.getTimeLimit())){
					if(!con.getTimeLimit().equals(WxHismsgListCon.TIMELIMIT_UNLIMIT)){
						timeLimit = con.getTimeLimit();
					}
				}else{
					timeLimit = WxHismsgListCon.TIMELIMIT_DEFAULT;
				}
			}else{
				timeLimit = WxHismsgListCon.TIMELIMIT_DEFAULT;
			}
			if(timeLimit!=null){
				queryCon.append(" and timestampdiff(day,now(),FROM_UNIXTIME(m.create_time))<"+timeLimit);
			}
			center2 = center2 + queryCon.toString();
			String center = center1+center2;
			
			int count = Db.queryLong("select count(1) "+center).intValue();
			if (count > 0) {
				int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
				String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
				List<Record> ls = Db.find(sql);
				if(ls!=null && ls.size()>0){
					for(Record r : ls){
						Integer type = r.getInt("type");
						//FIXME 处理其它类型的内容
						r.set("type", ReqType.getTypeNameByTypeId(type));
						if(type==null || type!=ReqType.TEXTID){
							r.set("content", null);
						}
					}
				}
				pageSet.setResultList(ls);
			}
		}
		return pageSet;
	}
	public Object[] getFansTag(String openid){
		Object[] result = null;
		if(StrUtils.isNotEmpty(openid)){
//			String sql1 = "select wf.open_id,wf.mark_name,wfi.nickname,wfi.headimgurl"
//					+ " from wx_fans wf left join wx_fans_info wfi on wfi.openid=wf.open_id"
//					+ " where wf.open_id='"+openid+"'";
//			Record info = Db.findFirst(sql1);
			Record info = getFansBasicInfo(openid);
			String sql2 = "select wt.id,wt.name from wx_fans_tag ft join wx_tag wt on wt.id=ft.tagid and ft.openid='"+openid+"'";
			List<Record> fansTags = Db.find(sql2);
			result = new Object[2];
			result[0] = info;
			result[1] = fansTags;
		}
		return result;
	}
	/**
	 * 获取粉丝一些简单信息
	 * @param openid
	 * @return
	 */
	public Record getFansBasicInfo(String openid){
		if(StrUtils.isEmpty(openid)){
			return null;
		}
		String sql1 = "select wf.open_id,wf.mark_name,wfi.nickname,wfi.headimgurl"
				+ " from wx_fans wf left join wx_fans_info wfi on wfi.openid=wf.open_id"
				+ " where wf.open_id='"+openid+"'";
		Record info = Db.findFirst(sql1);
		return info;
	}
	/**
	 * 更新粉丝的标签信息
	 * @param openid
	 * @param tagids
	 */
	public void updateFansTag(String openid,String[] tagids){
		if(StrUtils.isNotEmpty(openid) && tagids!=null && tagids.length>0){
			List<String> sqlList = new ArrayList<String>();
			sqlList.add("delete from wx_fans_tag where openid='"+openid+"'");
			for(int i=0;i<tagids.length;i++){
				String tagid = tagids[i];
				if(StrUtils.isNotEmpty(tagid)){
					sqlList.add("insert into wx_fans_tag(openid,tagid) values('"+openid+"',"+tagid+")");
				}
			}
			Db.batch(sqlList, 100);
		}
	}
	/**
	 * 更新粉丝的备注
	 * @param openid
	 * @param fansMark
	 */
	public void updateFansMark(String openid,String fansMark){
		if(StrUtils.isNotEmpty(openid)){
			WxFansModel exists = WxFansModel.dao.getByOpenId(openid);
			if(exists!=null){
				exists.setMarkName(fansMark);
				exists.update();
			}
		}
	}
	public List<WxTag> getWxTagList(){
		Long accountId = RoleUtils.gerCurAccountId();
		if(accountId!=null){
			return WxTag.dao.getList(accountId);
		}
		return null;
	}
	public List<WxAccount> geAccountList(){
		return WxAccount.dao.getAllAccountList();
	}
	public WxTag getWxTagById(String id){
		if(id==null){
			return null;
		}
		return WxTag.dao.findById(id);
	}
	public String saveWxTag(WxTag bean){
		String result = null;
		if(bean!=null){
			bean.set(WxTag.accountId, RoleUtils.gerCurAccountId());
			if(bean.save()){
				return "添加成功";
			}else{
				return "添加失败";
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
	public String modifyWxTag(WxTag bean){
		String result = null;
		if(bean!=null && bean.get(WxTag.id)!=null){
			WxTag exists = WxTag.dao.findById(bean.get(WxTag.id));
			if(exists!=null){
				exists.set(WxTag.name, bean.get(WxTag.name));
				if(exists.update()){
					result = "修改成功";
				}else{
					result = "修改失败";
				}
			}
		}else{
			result = IN_PRARM_ERROR;
		}
		return result;
	}
	public PageSet getFansMsgList(PageSet pageSet){
		WxAccount account = RoleUtils.getCurAccount();
		if(account!=null && account.getOriginalid()!=null){
			String select = "select distinct m.from";
			String center = " from wx_message m"
					+" where timestampdiff(day,now(),FROM_UNIXTIME(m.create_time))<2 and m.to='"+account.getOriginalid()+"'";
			String orderby = " order by m.create_time desc";
			int count = Db.queryLong("select count(distinct m.from) "+center).intValue();
			if (count > 0) {
				int startSize = (pageSet.getCurrPage() - 1) * pageSet.getPageSize();
				String sql = select + center + orderby + " limit " + startSize + "," + pageSet.getPageSize();;
				List<Record> ls = Db.find(sql);
				if(ls!=null && ls.size()>0){
					List<Record> rsls = new ArrayList<Record>();
					for(Record r : ls){
						String openid = r.get("from");
						//FIXME
						//只拿出了可能的text消息的content，如果m.type不是文本，则mt.content不可使用.
						String subSql = " select fi.headimgurl,fi.nickname,m.type,mt.content,FROM_UNIXTIME(m.create_time) as message_time"
								+ " from wx_message m"
								+ " left join wx_message_text mt on mt.id=m.content_id and m.type="+ReqType.TEXTID
								+ " left join wx_fans_info fi on fi.openid=m.from"
								+ " where m.to='"+account.getOriginalid()+"' and m.from='"+openid+"' order by m.create_time desc limit 0,1";
						Record rec = Db.findFirst(subSql);
						if(rec!=null){
							Integer type = rec.getInt("type");
							rec.set("type", ReqType.getTypeNameByTypeId(type));
							if(type==null || type!=ReqType.TEXTID){
								rec.set("content", null);
							}
							rec.set("openid", openid);
						}
						rsls.add(rec);
					}
					pageSet.setResultList(rsls);
				}
			}
		}
		return pageSet;
	}
	public List<Record> getFansMsgs(String openid){
		if(StrUtils.isEmpty(openid)){
			return null;
		}
		WxAccount account = RoleUtils.getCurAccount();
		if(account!=null && account.getOriginalid()!=null){
			String sql = " select m.content_id,m.type,FROM_UNIXTIME(m.create_time) as create_time "
					+ ",m.rsp_content_id,m.rsp_type,FROM_UNIXTIME(m.rsp_time) as rsp_time"
					+ " from wx_message m"
					+ " where m.from='"+openid+"' and m.to='"+account.getOriginalid()+"'"
					+ " order by m.create_time desc limi 0,10";
			List<Record> ls = Db.find(sql);
			if(ls!=null && ls.size()>0){
				WxFansInfo info = WxFansInfo.dao.findByOpenId(openid);
				for(Record r : ls){
					String name = null, headimgurl = null;
					if(info!=null){
						name = info.get(WxFansInfo.nickname);
						headimgurl = info.get(WxFansInfo.headimgurl);
					}
					r.set("from", name);
					r.set("headimgurl",headimgurl);
					
					//FIXME 非文本消息的展示内容.只拿出了可能的text消息的content，如果m.type不是文本，则mt.content不可使用.
					//粉丝消息
					Integer type = r.getInt("type");
					if(type==null){
						type = -1;
					}
					String content = null;
					String contentId = r.getStr("content_id");
					if(type==ReqType.TEXTID){
						WxMsgTextModel tm = WxMsgTextModel.dao.getMsgByContentId(contentId);
						if(tm!=null){
							content = tm.getContent();
						}
					}else{
						content = ReqType.getTypeNameByTypeId(type);
					}
					r.set("content", content);
					//回复消息
					type = r.getInt("rsp_type");
					if(type==null){
						type = -1;
					}
					content = null;
					contentId = r.getStr("rsp_content_id");
					if(type==ReqType.TEXTID){
						WxMsgTextModel tm = WxMsgTextModel.dao.getMsgByContentId(contentId);
						if(tm!=null){
							content = tm.getContent();
						}
					}else{
						content = ReqType.getTypeNameByTypeId(type);
					}
					r.set("rsp_content", content);
				}
				return ls;
			}
		}
		return null;
	}
	/**
	 * 管理员在页面发送消息给用户
	 * @param openid
	 * @param msg
	 * @return
	 */
	public String sendMsg(String openid,String msg){
		String originalId = RoleUtils.getCurAccountOriginalId();
		if(originalId!=null && openid!=null){
			WxMsgTextModel wmtm = new WxMsgTextModel();
			wmtm.setContent(msg);
//			wmtm.save();
			WxMessageModel wmm = new WxMessageModel();
			wmm.setFrom(originalId);
//			wmm.setContentId(wmtm.getId());
			wmm.setType(ReqType.TEXTID);
			wmm.setTo(openid);
			wmm.setCreateTime(System.currentTimeMillis());
//			wmm.save();
			wmm.setWxMsgTextModel(wmtm);
			//FIXME 消息保存到其它表单中
			if(!MessageService.getInstance().sendMessage(openid, wmm)){
				return "发送失败";
			}
		}
		return null;
	}
	
	
	public static WxFansService getInstance(){
		if(instance==null){
			synchronized (WxFansService.class) {
				if(instance==null)
					instance = new WxFansService();
			}
		}
		return instance;
	}
	private static WxFansService instance = null;
	private WxFansService(){}
}
