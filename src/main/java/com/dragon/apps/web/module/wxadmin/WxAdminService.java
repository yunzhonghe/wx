package com.dragon.apps.web.module.wxadmin;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.utils.Logger;

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

        if (wxAdminList.size() > 1) {
            Logger.warn(this, String.format("the result length of get wx admin by wx account id is bigger than 1....[resultSize:%d]", wxAdminList.size()));
        }

        if(wxAdminList.size() > 0) {
            return wxAdminList.get(0);
        } else {
            return null;
        }
    }
}
