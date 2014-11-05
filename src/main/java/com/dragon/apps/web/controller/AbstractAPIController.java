package com.dragon.apps.web.controller;


import com.jfinal.core.Controller;



/**
 * @author chenlong
 * 预留一个抽象的Controller
 * 可以把一些公用的逻辑放到里面
 */
public abstract class AbstractAPIController extends Controller{	
	
	@Override
	public void renderError(int errorCode) {
		renderText(String.valueOf(errorCode));
	}
}
