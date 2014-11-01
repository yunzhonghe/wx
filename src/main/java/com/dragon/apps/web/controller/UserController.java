package com.dragon.apps.web.controller;

import com.jfinal.core.Controller;

public class UserController extends Controller{

	
	public void index() {
		render("user/login.html");
	}
	
	public void home(){
		render("user/home.html");
	}
}
