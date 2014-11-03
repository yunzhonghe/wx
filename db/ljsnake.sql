--1、字典表-微信帐号类型;添加字典内容;
--2、字典表-地区(省市-区县);
--3、微信帐号表;建立托管账户的索引;
--4、微信帐号状态信息(同步时间,新用户数等);建立微信帐号索引,同步时间索引;
--
CREATE TABLE `wx_account_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
COMMENT = '字典表-微信帐号类型';
insert into wx_account_type(id,name) values(10,'订阅号');
insert into wx_account_type(id,name) values(11,'认证订阅号');
insert into wx_account_type(id,name) values(20,'服务号');
insert into wx_account_type(id,name) values(21,'认证服务号');
--
CREATE TABLE `wx`.`wx_dic_region` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '地区代码',
  `name` VARCHAR(50) NULL,
  `type` INT NULL COMMENT '1-省,2-市',
  `parentid` BIGINT NULL COMMENT '父地区代码，根地区(如省)该字段为null',
  PRIMARY KEY (`id`))
COMMENT = '字典表-地区(省市-区县)';
--
CREATE TABLE `wx_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(50) NOT NULL,
  `name` VARCHAR(20) NULL,
  `password` VARCHAR(45) NULL,
  `typeid` INT NULL,
  `escrowuser` BIGINT NULL COMMENT '托管用户(系统管理员)',
  `originalid` VARCHAR(45) NULL,
  `isdevmode` CHAR(1) NULL,
  `url` VARCHAR(200) NULL,
  `token` VARCHAR(100) NULL,
  `status` CHAR(1) NULL DEFAULT '1' COMMENT '状态、默认1，暂无特别使用',
  `avatar` VARCHAR(150) NULL COMMENT '头像',
  `secretset` CHAR(1) NULL COMMENT '隐私设置',
  `authstatus` CHAR(1) NULL COMMENT '认证情况',
  `region` VARCHAR(100) NULL,
  `functions` VARCHAR(200) NULL COMMENT '功能介绍',
  `qrcode` VARCHAR(150) NULL COMMENT '二维码',
  PRIMARY KEY (`id`))
COMMENT = '微信帐号表';
alter table wx_account add index wx_account_escrowuser(escrowuser);
alter table wx_account add index wx_account_account(account);
--
CREATE TABLE `wx_account_statusinfo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `wxid` BIGINT NULL,
  `syncdate` DATETIME NULL COMMENT '同步时间',
  `newmsgnum` INT NULL COMMENT '新消息数',
  `newfansnum` INT NULL COMMENT '新粉丝用户数',
  `totalfansnum` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '微信帐号状态信息(同步时间、新用户数等)';
alter table wx_account_statusinfo add index wx_statusinfo_wxid(wxid);
alter table wx_account_statusinfo add index wx_statusinfo_syncdate(syncdate);