--4、微信帐号状态信息(同步时间,新用户数等);建立微信帐号索引,同步时间索引;
alter table wx_account_statusinfo add index wx_statusinfo_wxid(wxid);
alter table wx_account_statusinfo add index wx_statusinfo_syncdate(syncdate);