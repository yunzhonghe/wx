package com.dragon.apps.web.module.wxadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dragon.apps.model.WxAccount;
import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.web.module.base.BaseController;
import com.dragon.apps.web.module.base.IdValueBean;
import com.google.gson.Gson;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class WxAdminController extends BaseController {
	public static String controlerKey = "/wx_admin";
    public void index() {
        forwardAction("/wx_admin/list");
    }
    
    public void list() {
        List<WxAdmin> list = WxAdmin.dao.find("select * from wx_admin where issuper=0");
        setAttr("adminList",list);
//        String sql = "select a.id, a.name from wx_account a left join wx_admin d on d.wx_account_id=a.id where d.wx_account_id is null";
//        List<WxAccount> accountList = WxAccount.dao.find(sql);
//        List<WxAccount> accountList = WxAccount.dao.find("select id, name from wx_account");
//        setAttr("accountList", new Gson().toJson(accountList));
        render("list.html");
    }

    public void preAdd() {
//        List<WxAccount> list = WxAccount.dao.find("select id, name from wx_account");
//        setAttr("accountList",list);
        setAttr("accountList",getSimpleUnusedWxAccount());
        render("add.html");
    }

    public void add() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.save();
        redirect("/wx_admin/list");
    }

    public void preUpdate() {
        WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
//        List<WxAccount> list = WxAccount.dao.find("select id, name from wx_account");
//        setAttr("accountList",list);
        List<IdValueBean> ls = getSimpleUnusedWxAccount();
        if(wxAdmin.getWxAccountId()!=null){
        	WxAccount account = wxAdmin.getWxAccount();
        	if(account!=null){
        		IdValueBean cur = new IdValueBean();
        		cur.setId(wxAdmin.getWxAccountId());
        		cur.setValue(account.getName());
        		ls.add(cur);
        	}
        }
        setAttr("accountList",ls);
        setAttr("admin", wxAdmin);
        render("update.html");
    }

    public void update() {
        WxAdmin wxAdmin = getModel(WxAdmin.class);
        wxAdmin.update();
        redirect("/wx_admin/list");
    }

    public void delete() {
    	Map<String,Object> map=new HashMap<String,Object>();
        map.put("isOK",false);
    	WxAdmin wxAdmin = WxAdmin.dao.findById(getParaToLong("id"));
        wxAdmin.delete();
        map.put("isOK",true);
        this.renderJson(map);
    }
    private List<IdValueBean> getSimpleUnusedWxAccount(){
    	String sql = "select c."+WxAccount.ID+",c."+WxAccount.NAME
    			+" from "+WxAccount.tableName+" c"
    			+" left join "+WxAdmin.getTableName()+" d on c."+WxAccount.ID+"=d."+WxAdmin.WX_ACCOUNT_ID
    			+" where d."+WxAdmin.ID+" is null";
    	List<Record> ls = Db.find(sql);
    	if(ls!=null && ls.size()>0){
			List<IdValueBean> result = new ArrayList<IdValueBean>();
			for(Record r : ls){
				IdValueBean bean = new IdValueBean();
				bean.setId(r.get(WxAccount.ID));
				bean.setValue(r.get(WxAccount.NAME));
				result.add(bean);
			}
			return result;
		}
    	return null;
    }
}
