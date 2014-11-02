--1、字典表-微信帐号类型;添加字典内容;
CREATE TABLE `wx_account_type` (
  `id` INT NOT NULL,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
COMMENT = '字典表-微信帐号类型';