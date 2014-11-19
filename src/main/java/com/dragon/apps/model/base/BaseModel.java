package com.dragon.apps.model.base;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
/**
 * use cache to query a Model with primaryKeyValue.
 * @author LiuJian
 * @param <M>
 */
public abstract class BaseModel<M extends Model<M>> extends Model<M>{

	private static final long serialVersionUID = 1L;
	
//	protected static final String tableName;
	
	protected String getTableName(){
		throw new RuntimeException("getTableName method should be overing by subclass if you call it.");
	}
	
	protected abstract String geyPrimaryKeyName();
	
	protected M findModelByCache(Object primaryKeyValue){
		List<M> result = findByCache(this.getClass().getSimpleName(), primaryKeyValue,
				"select * from "+ getTableName() +" where "+geyPrimaryKeyName()+"=?", primaryKeyValue);
		if(result!=null && result.size()>0){
			return result.get(0);
		}
		return null;
	}
	
}
