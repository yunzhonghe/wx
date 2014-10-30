package com.dragon.apps.web.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
	public static User dao = new User();
	
	private int age = 10;
	
	public int getAge(){
		return age;
	}
}
