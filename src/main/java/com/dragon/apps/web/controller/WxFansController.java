package com.dragon.apps.web.controller;

import java.util.List;

import com.dragon.apps.model.WxTag;
import com.dragon.apps.model.bean.WxFansListCon;
import com.dragon.apps.service.WxFansService;
import com.dragon.apps.utils.PageSet;
import com.dragon.apps.utils.OperationUtil;

/**
 * 微信粉丝用户
 * @author LiuJian
 */
public class WxFansController extends BaseController {
	public static String controlerKey = "/wxfans";
	

	public void index() {//粉丝管理
		WxFansListCon con = getModel(WxFansListCon.class);
		PageSet pageSet = getPageSet();
		setAttr("pageSet", getService().getFansList(con, pageSet));
		setAttr("con", con);
		render("fans_list.html");
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
		render("fans_tag.html");
	}
	public void msg(){//消息管理
		
	}
	
	private WxFansService getService(){
		return WxFansService.getInstance();
	}
}
