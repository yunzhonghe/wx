package com.dragon.apps.service;

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
import com.dragon.apps.model.bean.WxFansListCon;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.StrUtils;
import com.dragon.apps.web.controller.BaseController;
import com.dragon.spider.message.req.ReqType;
import com.dragon.spider.service.ConstantWx;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
/**
 * 微信粉丝用户的业务操作，供wxfanscontroller调用
 * @author LiuJian
 *
 */
public class WxFansService implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String IN_PRARM_ERROR = "传入参数错误";
	
	public PageSet getFansList(WxFansListCon con ,PageSet pageSet){
		Long accountId = BaseController.gerCurAccountId();
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
	private void initFans(){
		new Thread(){
			public void run() {
				super.run();
				FansService.getInstance().initAllFansData();
			}
		}.start();
	}
	public List<WxTag> getWxTagList(){
		Long accountId = BaseController.gerCurAccountId();
		if(accountId!=null){
			return WxTag.dao.getList(accountId);
		}
		return null;
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
			bean.set(WxTag.accountId, BaseController.gerCurAccountId());
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
		if(bean!=null && bean.get("BoxTypeID")!=null){
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
		WxAccount account = BaseController.getCurAccount();
		if(account!=null && account.getOriginalid()!=null){
			String select = "select distinct m.from";
			String center = " from wx_message m"
					+" where timestampdiff(day,FROM_UNIXTIME(m.create_time),now())<2 and m.to='"+account.getOriginalid()+"'";
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
						//只拿出了可能的text消息的content，如果m.type不是文本，则mt.content不可使用.
						String subSql = " select fi.headimgurl,fi.nickname,m.type,mt.content from wx_message m"
								+ " left join wx_message_text mt on mt.id=m.content_id left join wx_fans_info fi on fi.openid=m.from"
								+ " where m.to='"+account.getOriginalid()+"' and m.from='"+openid+"' order by m.create_time desc limit 0,1";
						Record rec = Db.findFirst(subSql);
						if(rec!=null){
							Integer type = rec.getInt("type");
							rec.set("type", getMessageTypeName(type));
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
		WxAccount account = BaseController.getCurAccount();
		if(account!=null && account.getOriginalid()!=null){
			String sql = " select m.from,m.to,m.content_id,m.type,m.FROM_UNIXTIME(m.create_time) as create_time from wx_message m "
					+ " where m.from in ('"+openid+"','"+account.getOriginalid()+"') and m.to in ('"+openid+"','"+account.getOriginalid()+"')"
					+ " order by m.create_time desc limi 0,10";
			List<Record> ls = Db.find(sql);
			if(ls!=null && ls.size()>0){
				WxFansModel fans = WxFansModel.dao.getByOpenId(openid);
				WxFansInfo info = fans.getInfo();
				for(Record r : ls){
					//只拿出了可能的text消息的content，如果m.type不是文本，则mt.content不可使用.
					String from = r.getStr("from");
					String name = null, headimgurl = null,fromfans=null;
					if(info==null){
						name = fans.getName();
					}else{
						name = info.get(WxFansInfo.nickname);
						headimgurl = info.get(WxFansInfo.headimgurl);
					}
					if(from.equals(openid)){
						fromfans = "1";//粉丝发的消息
					}else{
						fromfans = "0";//自己发的消息
					}
					r.set("fromfans", fromfans);
					r.set("fansname", name);
					r.set("headimgurl",headimgurl);
					Integer type = r.getInt("type");
					if(type==null){
						type = -1;
					}
					String content = null;
					String contentId = r.getStr("content_id");
					if(type==ReqType.TEXTID){
						WxMsgTextModel tm = WxMsgTextModel.dao.getMsgById(contentId);
						if(tm!=null){
							content = tm.getContent();
						}
					}else if(type==ReqType.IMAGEID){
						//FIXME
						content = "图片";
					}else if(type==ReqType.LINKID){
						content = "链接";
					}else if(type==ReqType.LOCATIONID){
						content = "位置";
					}else if(type==ReqType.VOICEID){
						content = "音频";
					}else if(type==ReqType.VIDEOID){
						content = "视频";
					}
					r.set("content", content);
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
		String originalId = BaseController.getCurAccountOriginalId();
		if(originalId!=null && openid!=null){
			WxMsgTextModel wmtm = new WxMsgTextModel();
			wmtm.setContent(msg);
			wmtm.save();
			WxMessageModel wmm = new WxMessageModel();
			wmm.setFrom(originalId);
			wmm.setContentId(wmtm.getId());
			wmm.setType(ReqType.TEXTID);
			wmm.setTo(openid);
			wmm.setCreateTime(System.currentTimeMillis());
			wmm.save();
			wmm.setWxMsgTextModel(wmtm);
			if(!MessageService.getInstance().sendMessage(openid, wmm)){
				return "发送失败";
			}
		}
		return null;
	}
	private String getMessageTypeName(Integer type){
		if(type==null){
			type = -1;
		}
		if(type==ReqType.TEXTID){
			return "文本";
		}else if(type==ReqType.IMAGEID){
			return "图片";
		}else if(type==ReqType.LINKID){
			return "链接";
		}else if(type==ReqType.LOCATIONID){
			return "位置";
		}else if(type==ReqType.VOICEID){
			return "音频";
		}else if(type==ReqType.VIDEOID){
			return "视频";
		}else if(type==ReqType.EVENTID){
			return "事件";
		}
		return "未知";
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
