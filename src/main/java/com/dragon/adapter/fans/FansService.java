package com.dragon.adapter.fans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dragon.adapter.NeedFix;
import com.dragon.apps.model.WxFansInfo;
import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.service.UserHandleService;
import com.dragon.apps.utils.ModelUtils;
import com.dragon.apps.utils.StrUtils;
import com.dragon.spider.api.response.CreateGroupResponse;
import com.dragon.spider.api.response.GetGroupsResponse;
import com.dragon.spider.api.response.GetUsersResponse;

public class FansService {
	private UserHandleService service = null;
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
	 * 得到所有获取关注者列表
	 * @return 返回对应关注着的openid字符串列表
	 */
	public List<String> getAllFans(){
		List<String> result = null;
		String next_openid = "";
		GetUsersResponse res = service.getUsers(next_openid);
		if(res!=null){//获取成功
			result = new ArrayList<String>();
			//StrUtils.isNotEmpty(next)
			while(true){
				if(res==null){//拉取失败
					break;
				}
				if(res.getData()!=null){
					setOpenidsToList(res.getData().getOpenid(), result);
				}
				String next = res.getNext_openid();
				if(StrUtils.isNotEmpty(next)){//还有后续
					res = service.getUsers(next);
				}else{
					break;
				}
			}
		}
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=设置用户备注名接口
	 * 设置关注着的备注
	 * @param openid
	 * @param remark
	 * @return
	 */
	public boolean setFansRemark(String openid, String remark){
		boolean result = true;
		//FIXME 1, does service.createMenu should return error?
		service.setUserRemark(openid, remark);
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
	 * 创建粉丝分组
	 * @param groupName
	 * @return 分组的id值
	 */
	public String createFansGroup(String groupName){
		String result = null;
		CreateGroupResponse res = service.createGroup(groupName);
		if(res!=null){
			result = res.getId();
		}
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口#.E6.9F.A5.E8.AF.A2.E6.89.80.E6.9C.89.E5.88.86.E7.BB.84
	 * 获取粉丝的所有分组信息
	 * @return
	 */
	public List<Map<String,String>> getAllFansGroups(){
		List<Map<String,String>> result = null;
		GetGroupsResponse res = service.getGroups();
		if(res!=null){
			result = new ArrayList<Map<String,String>>();
			//FIXME GetGroupsResponse need to contain a collection.
		}
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口#.E6.9F.A5.E8.AF.A2.E7.94.A8.E6.88.B7.E6.89.80.E5.9C.A8.E5.88.86.E7.BB.84
	 * 根据粉丝的openid获取他所在的分组的id
	 * @param fansOpenid
	 * @return
	 */
	public String getGroupIdByFans(String fansOpenid){
		String result = null;
		result = service.getGroupIdByOpenid(fansOpenid);
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口#.E4.BF.AE.E6.94.B9.E5.88.86.E7.BB.84.E5.90.8D
	 * 修改粉丝组的名称
	 * @param groupId
	 * @param toUpdateName
	 * @return
	 */
	public boolean updateGroupName(Integer groupId,String toUpdateName){
		boolean result = true;
		//FIXME 1, does service.updateGroup should return error?
		service.updateGroup(groupId, toUpdateName);
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口#.E7.A7.BB.E5.8A.A8.E7.94.A8.E6.88.B7.E5.88.86.E7.BB.84
	 * 更改粉丝所在分组，移动粉丝到目标分组
	 * @param fansOpenid
	 * @param toGroupId
	 * @return
	 */
	public boolean moveFansToAnotherGroup(String fansOpenid, String toGroupId){
		boolean result = true;
		//FIXME 1, does service.moveGroupUser should return error?
		service.moveGroupUser(fansOpenid, toGroupId);
		return result;
	}
	/**
	 * http://mp.weixin.qq.com/wiki/index.php?title=获取用户基本信息(UnionID机制)
	 * 获取关注者的详细信息
	 * @param fansOpenid
	 * @return
	 */
	public WxFansModel getFansInfo(String fansOpenid){
		WxFansModel result = FansAdapter.getModelByResponse(service.getUserInfo(fansOpenid));
		return result;
	}
	/**
	 * 初始化粉丝信息
	 */
	public void initAllFansData(){
		List<String> openids = getAllFans();
		if(openids!=null && openids.size()>0){
			Long account_id = NeedFix.getApiAccountId(service);
			for(String openid : openids){
				WxFansModel wxFansModel = getFansInfo(openid);
				updateWxFansModelToDb(wxFansModel,account_id);
			}
		}
	}
	private void updateWxFansModelToDb(WxFansModel wxFansModel,Long account_id){
		if(wxFansModel!=null){
			String openid = wxFansModel.getOpenId();
			if(openid!=null){
				wxFansModel.setWxAccountId(account_id);
				WxFansModel existsModel = WxFansModel.dao.getByOpenId(openid);
				if(existsModel==null){
					wxFansModel.save();
				}else{
					existsModel.setSubscribe(wxFansModel.getSubscribe());
					//XXX may be has other info?.
					existsModel.update();
				}
				WxFansInfo info = wxFansModel.getWxFansInfo();
				if(info!=null){
					WxFansInfo existsInfo = WxFansInfo.dao.findByOpenId(openid);
					if(existsInfo==null){
						info.save();
					}else{
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.nickname);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.sex);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.city);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.country);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.province);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.language);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.headimgurl);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.subscribe_time);
						ModelUtils.setModelProperty(info, existsInfo, WxFansInfo.unionid);
						existsInfo.update();
					}
				}
				
			}
		}
	}
	private void setOpenidsToList(String[] openids,List<String> result){
		for(int i=0;i<openids.length;i++){
			result.add(openids[i]);
		}
	}
	private static FansService instance = null;
	public static FansService getInstance(){
		if(instance==null){
			synchronized (FansService.class) {
				if(instance==null)
					instance = new FansService();
			}
		}
		return instance;
	}
	private FansService(){
		service = new UserHandleService(NeedFix.getApiConfig());
	}
}
