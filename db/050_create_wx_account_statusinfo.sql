--4、微信帐号状态信息(同步时间,新用户数等);建立微信帐号索引,同步时间索引;
CREATE TABLE `wx_account_statusinfo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `wxid` BIGINT NULL,
  `syncdate` DATETIME NULL COMMENT '同步时间',
  `newmsgnum` INT NULL COMMENT '新消息数',
  `newfansnum` INT NULL COMMENT '新粉丝用户数',
  `totalfansnum` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '微信帐号状态信息(同步时间、新用户数等)';