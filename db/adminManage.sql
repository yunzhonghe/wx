--------------------------------------------------------------------
--管理员Table
--------------------------------------------------------------------
CREATE TABLE wx_admin (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  admin_id VARCHAR(32) NOT NULL UNIQUE,
  password VARCHAR(32) NOT NULL,
  email VARCHAR(32) NOT NULL DEFAULT '',
  telephone VARCHAR(20) DEFAULT NULL,
  name VARCHAR(20) DEFAULT NULL,
  department_id BIGINT(20) DEFAULT NULL,
  post_id BIGINT(20) DEFAULT NULL,
  wx_accont_id BIGINT(20) DEFAULT NULL,
  wx_account_name varchar(32) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM
CHECKSUM=0
DELAY_KEY_WRITE=0
PACK_KEYS=0
AUTO_INCREMENT=0
AVG_ROW_LENGTH=0
MIN_ROWS=0
MAX_ROWS=0
ROW_FORMAT=DEFAULT
KEY_BLOCK_SIZE=0
CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

----------------------------------------------------------------------