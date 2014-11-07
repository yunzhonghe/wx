package com.dragon.apps.service;

import com.dragon.apps.model.WxAdmin;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liuxi
 * Date: 11/7/14
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class WxAdminService implements Serializable {
    private static final long serialVersionUID = 1L;

    public WxAdmin getWxAdminByWxAccountId(long wxAccountId) {
        List<WxAdmin> wxAdminList = WxAdmin.dao.find("select * from wx_admin where wx_account_id = " + wxAccountId);
        if(wxAdminList.size() > 0) {
            return wxAdminList.get(0);
        } else {
            return null;
        }
    }
}
