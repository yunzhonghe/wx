package com.dragon.apps.web.controller;

import com.jfinal.core.Controller;

public class WxAccountController extends Controller {
	public static String controlerKey = "/wxaccount";
    public void index() {//默认位置，当作总概况
       /**
       if(is sub account){
    	   forwardAction(controlerKey+"/subindex");
    	   return;
       }
        */
       renderText("开发中...");
//       renderNull();
    }
    public void list(){//帐号列表
    	renderText("开发中...");
    }
    public void add(){//添加帐号
    	renderText("开发中...");
    }
    public void adddo(){//保存帐号
    	renderText("开发中...");
    }
    public void modify(){//修改帐号
    	renderText("开发中...");
    }
    public void modifydo(){//保存修改
    	renderText("开发中...");
    }
    //子账号操作
    public void subindex(){
    	renderText("开发中...");
    }
    public void modifyself(){
    	renderText("开发中...");
    }
    public void modifyselfdo(){
    	renderText("开发中...");
    }
    
}
