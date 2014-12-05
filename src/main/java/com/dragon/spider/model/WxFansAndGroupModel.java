package com.dragon.spider.model;

import java.util.ArrayList;
import java.util.List;

import com.dragon.apps.model.WxFansModel;
import com.dragon.apps.model.WxGroup;

public class WxFansAndGroupModel {
	public List<WxFansModel> fans = new ArrayList<WxFansModel>();
	public List<WxGroup> groups = new ArrayList<WxGroup>();

	public List<WxFansModel> getFans() {
		return fans;
	}

	public void setFans(List<WxFansModel> fans) {
		this.fans = fans;
	}

	public List<WxGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<WxGroup> groups) {
		this.groups = groups;
	}

}
