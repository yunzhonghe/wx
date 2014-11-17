package com.dragon.apps.utils;

import com.jfinal.plugin.activerecord.Model;

public class ModeUtils {
	public static void setModelProperty(Model<?> fromM, Model<?> toM,String propertyName){
		toM.set(propertyName, fromM.get(propertyName));
	}
}
