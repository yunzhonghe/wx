package com.dragon.apps.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User>{
	
	private static final long serialVersionUID = -5641188125126550687L;

	public static User dao = new User();
	
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String MOBILE="mobile";

	public long getId() {
		return getLong(ID);
	}
	
	public User setId(Long id){
	    return set(ID,id);
	}
	
	public String getName(){
		return getStr(NAME);
	}
	
	public User setName(String name){
		return set(NAME,name);
	}
	
	public String getPassword(){
		return getStr(PASSWORD);
	}
	
	public User setPassword(String password){
		return set(PASSWORD,password);
	}
	
	public String getEmail(){
		return getStr(EMAIL);
	}
	
	public User setEmail(String email){
		return set(EMAIL,email);
	}
	
	public String getMobile(){
		return getStr(MOBILE);
	}
	
	public User setMobile(String mobile){
		return set(MOBILE,mobile);
	}
}
