package com.dragon.apps.web.module.wxfans;

import java.util.List;

import com.dragon.apps.model.WxTag;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.OperationUtil;
import com.dragon.apps.web.module.base.BaseController;

/**
 * 微信粉丝用户
 * @author LiuJian
 */
public class WxFansController extends BaseController {
	public static String controlerKey = "/wx_fans";
	

	public void index() {//超级管理员粉丝管理
		WxFansListCon con = getModel(WxFansListCon.class);
		PageSet pageSet = getPageSet();
		setAttr("pageSet", getService().getFansList(con, pageSet));
		setAttr("taglist",getService().getWxTagList());
		setAttr("con", con);
		render("fans_list.html");
	}
	public void subindex() {//普通管理员粉丝管理
		WxFansListCon con = getModel(WxFansListCon.class);
		PageSet pageSet = getPageSet();
		setAttr("pageSet", getService().getFansList(con, pageSet));
		setAttr("taglist",getService().getWxTagList());
		setAttr("con", con);
		render("fans_list.html");
	}
	public void fanstag(){//维护粉丝标签
		String openid = getPara();
		Object[] objs = getService().getFansTag(openid);
		setAttr("fansinfo",objs[0]);
		setAttr("fanstags",objs[1]);
		setAttr("taglist",getService().getWxTagList());
		render("fans_tag.html");
	}
	public void fanstagdo(){//执行修改粉丝标签,成功后返回粉丝列表
		String openid = getPara("openid");
		String[] fantags = getRequest().getParameterValues("fantags");
		getService().updateFansTag(openid, fantags);
		redirect("/wxfans");
	}
	public void tag(){//标签管理
		String identify = getPara();//list,add,adddo,modify
		OperationUtil op = OperationUtil.getOperationUtilByIdentify(identify);
		
		List<WxTag> list = null;
		WxTag bean = null;
		String nextAction = op.getIdentify();
		String operationMsg = null;
		switch(op){
		case LIST:
			list = getService().getWxTagList();
			break;
		case ADD:
			nextAction = OperationUtil.ADDDO.getIdentify();
			break;
		case ADDDO:
			bean = getModel(WxTag.class);
			operationMsg = getService().saveWxTag(bean);
			bean = null;
			break;
		case SHOW://same with modify
			bean = getService().getWxTagById(getPara("id"));
			break;
		case MODIFY:
			bean = getService().getWxTagById(getPara("id"));
			nextAction = OperationUtil.MODIFYDO.getIdentify();
			break;
		case MODIFYDO:
			bean = getModel(WxTag.class);
			operationMsg = getService().modifyWxTag(bean);
			bean = null;
			break;
		case REMOVE:
			renderText("未开发...");
			return;
		default:
			break;
		}
		setAttr("list", list);
		setAttr(BEAN_ENTITY, bean);
		setAttr("nextAction", nextAction);
		setAttr(OPERATION_RESULT, operationMsg);
		render("tag_list.html");
	}
	public void msg(){//超级管理消息管理
		PageSet pageSet = getPageSet();
		setAttr("pageSet", getService().getFansMsgList(pageSet));
		render("msg_list.html");
	}
	public void submsg(){//普通管理消息管理
		PageSet pageSet = getPageSet();
		setAttr("pageSet", getService().getFansMsgList(pageSet));
		render("msg_list.html");
	}
	public void msgchat(){//消息聊天
		String openid = getPara("openid");
		setAttr("list", getService().getFansMsgs(openid));
		setAttr("openid", openid);
		render("msg_chat.html");
	}
	public void msgchatadd(){//聊天消息添加
		String openid = getPara("openid");
		String msg = getPara("msg");
		String operationMsg = getService().sendMsg(openid, msg);
		if(operationMsg==null){//发送成功
			redirect("/wxfans/msg");
		}else{
			setAttr(OPERATION_RESULT, operationMsg);
			render("msg_chat.html");
		}
	}
	
	private WxFansService getService(){
		return WxFansService.getInstance();
	}
}
