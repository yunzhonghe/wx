package com.dragon.apps.web.controller;

import com.jfinal.core.Controller;

public class UserController extends Controller{

	public void index() {
		render("/test.jsp");
	}
}
