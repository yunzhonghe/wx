--2、字典表-地区(省市-区县);

CREATE TABLE `wx`.`wx_dic_region` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '地区代码',
  `name` VARCHAR(50) NULL,
  `type` INT NULL COMMENT '1-省,2-市',
  `parentid` BIGINT NULL COMMENT '父地区代码，根地区(如省)该字段为null',
  PRIMARY KEY (`id`))
COMMENT = '字典表-地区(省市-区县)';