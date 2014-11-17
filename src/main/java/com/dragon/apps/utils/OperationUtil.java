package com.dragon.apps.utils;

public enum OperationUtil {
	LIST("list", "列表")
	, ADD("add", "添加")
	, ADDDO("adddo", "添加执行")
	, SHOW("show","查看")
	, MODIFY("modify", "修改")
	, MODIFYDO("modifydo", "修改执行")
	, REMOVE("remove", "删除")
	// ,ENABLE("enable","启用")
	// ,DISABLE("disable","禁用")
	;
	public static OperationUtil getOperationUtilByIdentify(String identify) {
		if (identify != null)
			for (OperationUtil op : OperationUtil.values()) {
				if (op.equals(identify)) {
					return op;
				}
			}
		return LIST;
	}

	OperationUtil(String identify, String name) {
		this.identify = identify;
		this.name = name;
	}

	private String identify;
	private String name;

	public boolean equals(String identify) {
		return this.identify == identify || this.identify.equals(identify);
	}

	public String getIdentify() {
		return identify;
	}

	public String getName() {
		return name;
	}
}
