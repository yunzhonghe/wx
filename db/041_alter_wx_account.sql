--3、微信帐号表;建立托管账户的索引;
alter table wx_account add index wx_account_escrowuser(escrowuser);
alter table wx_account add index wx_account_account(account);