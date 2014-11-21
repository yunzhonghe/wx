--1、字典表-微信帐号类型;添加字典内容;
insert into wx_account_type(id,name) values(10,'订阅号');
insert into wx_account_type(id,name) values(11,'认证订阅号');
insert into wx_account_type(id,name) values(20,'服务号');
insert into wx_account_type(id,name) values(21,'认证服务号');
--系统超级管理员
insert into wx_admin(admin_id,password,name,issuper)values('admin','d','管理员','1');
