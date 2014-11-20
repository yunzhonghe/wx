package com.dragon.apps.web.module.base;

import com.dragon.apps.utils.PageSet;
import com.jfinal.core.Controller;

public class BaseController extends Controller{
	protected String OPERATION_RESULT = "_opresult";//by ljsnake
	protected String BEAN_ENTITY = "_bean";//by ljsnake
	
	protected PageSet getPageSet() {//by ljsnake
		PageSet pageSet = null;
		String page_pageSize = getPara("page_pageSize");
		String page_currPage = getPara("page_currPage");
		if (page_pageSize != null && page_currPage != null) {
			pageSet = new PageSet(Integer.parseInt(page_currPage), Integer.parseInt(page_pageSize));
		} else {
			pageSet = new PageSet();
		}
		return pageSet;
	}
}
