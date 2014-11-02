--3、微信帐号表;建立托管账户的索引;
CREATE TABLE `wx_account` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `account` VARCHAR(50) NOT NULL,
  `name` VARCHAR(20) NULL,
  `password` VARCHAR(45) NULL,
  `typeid` INT NULL,
  `escrowuser` VARCHAR(45) NULL COMMENT '托管用户(系统管理员)',
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