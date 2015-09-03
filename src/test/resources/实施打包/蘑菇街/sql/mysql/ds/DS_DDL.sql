SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `card`
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card` (
  `CARD_NO` varchar(50) NOT NULL,
  `CARD_ID` varchar(32) DEFAULT '' COMMENT '银行卡号',
  `CARD_NAME` varchar(30) DEFAULT NULL COMMENT '持卡人姓名',
  `CREDENTIALS_TYPE` varchar(20) DEFAULT NULL COMMENT '持卡人证件类型',
  `CREDENTIALS_NO` varchar(50) DEFAULT NULL COMMENT '持卡人证件号',
  `CARD_TYPE` varchar(20) DEFAULT NULL COMMENT '卡类型',
  `CARD_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '卡有效期',
  `PHONE` varchar(11) DEFAULT NULL COMMENT '预留手机号',
  `BANK` varchar(50) DEFAULT NULL COMMENT '发卡行',
  `BACK_UP` varchar(4000) DEFAULT NULL COMMENT '备用字段',
  `LAST_UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `DEPART` varchar(20) DEFAULT NULL COMMENT '部门号',
  PRIMARY KEY (`CARD_NO`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of card
-- ----------------------------

-- ----------------------------
-- Table structure for `commonkeyword`
-- ----------------------------
DROP TABLE IF EXISTS `commonkeyword`;
CREATE TABLE `commonkeyword` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `action` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '1 - 禁止购买\r\n2 - 禁止提现\r\n3 - 购买白名单\r\n4 - 提现白名单',
  `type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '1 - 银行卡号\r\n2 - 手机号码\r\n3 - 身份证号\r\n4 - 用户id\r\n5 - 地址关键字\r\n6 - 一般字符',
  `keyword` varchar(100) NOT NULL DEFAULT '',
  `comment` varchar(100) NOT NULL DEFAULT '',
  `status` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '0 - 正常 1 - 删除',
  `CREATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAST_UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_action` (`action`),
  KEY `idx_type` (`type`),
  KEY `idx_created` (`CREATE_TIME`),
  KEY `idx_updated` (`LAST_UPDATE_TIME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commonkeyword
-- ----------------------------

-- ----------------------------
-- Table structure for `oper`
-- ----------------------------
DROP TABLE IF EXISTS `oper`;
CREATE TABLE `oper` (
  `ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '操作流水号',
  `UUID` varchar(36) NOT NULL COMMENT '操作唯一编号',
  `OPER_NO` varchar(50) DEFAULT NULL COMMENT '操作编号(支付单保存)',
  `OPER_TYPE` varchar(20) NOT NULL COMMENT '操作类型',
  `BIZ_CATE` varchar(10) DEFAULT NULL COMMENT '业务组',
  `OPER_TIME` datetime NOT NULL COMMENT '操作时间',
  `OPER_STATUS` varchar(10) DEFAULT NULL COMMENT '操作状态',
  `PAY_ID` varchar(20) DEFAULT NULL COMMENT '支付标识',
  `USER_ID` varchar(20) DEFAULT NULL COMMENT '用户号',
  `USER_LOGIN` varchar(30) DEFAULT NULL COMMENT '登录号',
  `COL_CUST_ID` varchar(20) DEFAULT NULL COMMENT '收款方标志',
  `COL_CUST_NAME` varchar(50) DEFAULT NULL COMMENT '收款方名称',
  `PAY_AMT` bigint(10) DEFAULT NULL COMMENT '支付金额',
  `PAY_TIME` datetime NOT NULL COMMENT '支付时间',
  `ACCOUNT_ID` varchar(20) DEFAULT NULL COMMENT '账户号',
  `BANK_ID` varchar(10) DEFAULT NULL COMMENT '银行号',
  `CARD_NO` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `PAY_MOD` varchar(11) DEFAULT NULL COMMENT '充值手机',
  `RECHARGE_MOB` varchar(11) DEFAULT NULL COMMENT '被充值手机',
  `IP_ADDR` varchar(16) DEFAULT NULL COMMENT 'IP地址',
  `IP_PRV` varchar(20) DEFAULT NULL COMMENT 'ip所在省',
  `IP_CITY` varchar(30) DEFAULT NULL COMMENT 'ip所在市',
  `MAC` varchar(17) DEFAULT NULL COMMENT 'mac地址',
  `IMEI` varchar(15) DEFAULT NULL COMMENT '手机串号',
  `MACHINE_ID` varchar(50) DEFAULT NULL COMMENT '终端编号',
  `CPU_NO` varchar(20) DEFAULT NULL COMMENT 'cpu编号',
  `DISK_NO` varchar(20) DEFAULT NULL COMMENT '硬盘编号',
  `BROWSER_VER` varchar(10) DEFAULT NULL COMMENT '浏览器版本',
  `BROWSER_TYPE` varchar(10) DEFAULT NULL COMMENT '浏览器类型',
  `INPUT_VER` varchar(10) DEFAULT NULL COMMENT '输入法版本',
  `INPUT_TYPE` varchar(10) DEFAULT NULL COMMENT '输入法类型',
  `MACHINE_DACTYLOGRAM` varchar(20) DEFAULT NULL COMMENT '机器指纹号',
  `OPER_PERSON` varchar(30) DEFAULT NULL COMMENT '操作员',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '绑定邮箱',
  `CARD_TYPE` varchar(10) DEFAULT NULL COMMENT '银行卡类型',
  `SN` varchar(30) DEFAULT NULL COMMENT '手机序列号',
  `OS` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `SIM` varchar(20) DEFAULT NULL COMMENT '手机SIM卡号',
  `BANK_PHONE` varchar(11) DEFAULT NULL COMMENT '银行预留手机',
  `BACK_UP` varchar(4000) DEFAULT NULL COMMENT '备用字段',
  `BANK_ID_NO` varchar(20) DEFAULT NULL COMMENT '银行签约身份证',
  `ID_TYPE` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ID_NO` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `PHONE_NO` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `LAST_UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `DEPART` varchar(20) DEFAULT NULL COMMENT '部门号',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oper
-- ----------------------------

-- ----------------------------
-- Table structure for `operation`
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `ID` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '操作流水号',
  `UUID` varchar(36) NOT NULL COMMENT '操作唯一编号',
  `OPER_NO` varchar(50) DEFAULT NULL COMMENT '操作编号(支付单保存)',
  `OPER_TYPE` varchar(20) NOT NULL COMMENT '操作类型',
  `BIZ_CATE` varchar(10) DEFAULT NULL COMMENT '业务组',
  `OPER_TIME` datetime NOT NULL COMMENT '操作时间',
  `OPER_STATUS` varchar(10) DEFAULT NULL COMMENT '操作状态',
  `PAY_ID` varchar(20) DEFAULT NULL COMMENT '支付标识',
  `USER_ID` varchar(20) DEFAULT NULL COMMENT '用户号',
  `USER_LOGIN` varchar(30) DEFAULT NULL COMMENT '登录号',
  `COL_CUST_ID` varchar(20) DEFAULT NULL COMMENT '收款方标志',
  `COL_CUST_NAME` varchar(50) DEFAULT NULL COMMENT '收款方名称',
  `PAY_AMT` bigint(10) DEFAULT NULL COMMENT '支付金额',
  `PAY_TIME` datetime NOT NULL COMMENT '支付时间',
  `ACCOUNT_ID` varchar(20) DEFAULT NULL COMMENT '账户号',
  `BANK_ID` varchar(10) DEFAULT NULL COMMENT '银行号',
  `CARD_NO` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `PAY_MOD` varchar(11) DEFAULT NULL COMMENT '充值手机',
  `RECHARGE_MOB` varchar(11) DEFAULT NULL COMMENT '被充值手机',
  `IP_ADDR` varchar(16) DEFAULT NULL COMMENT 'IP地址',
  `IP_PRV` varchar(20) DEFAULT NULL COMMENT 'ip所在省',
  `IP_CITY` varchar(30) DEFAULT NULL COMMENT 'ip所在市',
  `MAC` varchar(17) DEFAULT NULL COMMENT 'mac地址',
  `IMEI` varchar(15) DEFAULT NULL COMMENT '手机串号',
  `MACHINE_ID` varchar(50) DEFAULT NULL COMMENT '终端编号',
  `CPU_NO` varchar(20) DEFAULT NULL COMMENT 'cpu编号',
  `DISK_NO` varchar(20) DEFAULT NULL COMMENT '硬盘编号',
  `BROWSER_VER` varchar(10) DEFAULT NULL COMMENT '浏览器版本',
  `BROWSER_TYPE` varchar(10) DEFAULT NULL COMMENT '浏览器类型',
  `INPUT_VER` varchar(10) DEFAULT NULL COMMENT '输入法版本',
  `INPUT_TYPE` varchar(10) DEFAULT NULL COMMENT '输入法类型',
  `MACHINE_DACTYLOGRAM` varchar(20) DEFAULT NULL COMMENT '机器指纹号',
  `OPER_PERSON` varchar(30) DEFAULT NULL COMMENT '操作员',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '绑定邮箱',
  `CARD_TYPE` varchar(10) DEFAULT NULL COMMENT '银行卡类型',
  `SN` varchar(30) DEFAULT NULL COMMENT '手机序列号',
  `OS` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `SIM` varchar(20) DEFAULT NULL COMMENT '手机SIM卡号',
  `BANK_PHONE` varchar(11) DEFAULT NULL COMMENT '银行预留手机',
  `BACK_UP` varchar(4000) DEFAULT NULL COMMENT '备用字段',
  `BANK_ID_NO` varchar(20) DEFAULT NULL COMMENT '银行签约身份证',
  `ID_TYPE` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ID_NO` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `PHONE_NO` varchar(15) DEFAULT NULL COMMENT '电话号码',
  `LAST_UPDATE_TIME` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `DEPART` varchar(20) DEFAULT NULL COMMENT '部门号',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation
-- ----------------------------

-- ----------------------------
-- Table structure for `pay_order`
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ORDER_ID` varchar(40) DEFAULT '0' COMMENT '订单号',
  `CREATE_TIME` datetime NOT NULL COMMENT '订单创建时间',
  `REQUES_ID` varchar(100) DEFAULT NULL COMMENT '请求号',
  `TRANS_TIME` datetime NOT NULL COMMENT '交易时间',
  `TRANS_AMOUNT` bigint(10) DEFAULT NULL COMMENT '交易金额',
  `SUCC_AMOUNT` bigint(10) DEFAULT NULL COMMENT '扣款金额',
  `CRY_TYPE` varchar(10) DEFAULT NULL COMMENT '币种',
  `PAY_TYPE` varchar(10) DEFAULT NULL COMMENT '支付类型',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '交易状态',
  `ORDER_CHNL` varchar(40) DEFAULT NULL COMMENT '业务渠道',
  `ORDER_TYPE` varchar(40) DEFAULT NULL COMMENT '业务类型',
  `TRADE_MODE` varchar(10) DEFAULT NULL COMMENT '交易模式',
  `TRADE_TYPE` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `ORDER_FROM` varchar(10) DEFAULT NULL COMMENT '订单来源',
  `PAY_CUST_USER_ID` varchar(20) DEFAULT NULL COMMENT '支付方用户号',
  `PAY_CUST_NAME` varchar(50) DEFAULT NULL COMMENT '支付方名称',
  `PAY_CUST_ACCT` varchar(20) DEFAULT NULL COMMENT '支付方账号',
  `PAY_CUST_CARD_NO` varchar(20) DEFAULT NULL COMMENT '支付卡号',
  `PAY_CUST_CARD_BIN` varchar(255) DEFAULT NULL COMMENT '卡bin',
  `COL_CODE_IN` varchar(255) DEFAULT NULL COMMENT '商户类型',
  `COL_CUST_USER_ID` varchar(20) DEFAULT NULL COMMENT '收款方用户号',
  `COL_CUST_NAME` varchar(50) DEFAULT NULL COMMENT '收款方名称',
  `COL_CUST_ACCT` varchar(20) DEFAULT NULL COMMENT '收款方账号',
  `COL_CUST_CARD_NO` varchar(20) DEFAULT NULL COMMENT '收款卡号',
  `TRADE_NO` varchar(20) DEFAULT NULL COMMENT '交易商品号',
  `TARDE_NAME` varchar(50) DEFAULT NULL COMMENT '交易商品名称',
  `EXT_ORDER_NO` varchar(20) DEFAULT NULL COMMENT '外部订单号',
  `FROZEN_AMOUNT` bigint(10) DEFAULT NULL COMMENT '冻结金额',
  `FROZEN_TIME` datetime DEFAULT NULL COMMENT '冻结时间',
  `REFUND_TIME` datetime DEFAULT NULL COMMENT '退款时间',
  `REFUND_AMOUNT` decimal(10,0) DEFAULT NULL COMMENT '退款金额',
  `REFUND_TIMES` decimal(10,0) DEFAULT NULL,
  `COMAMT` decimal(10,0) DEFAULT NULL COMMENT '手续费',
  `OPER_NO` varchar(50) DEFAULT NULL COMMENT '操作编号(付款方+收款方+时间+金额匹配)',
  `BACK_UP` varchar(4000) DEFAULT NULL COMMENT '备用字段',
  `LAST_UPDATE_TIME` datetime NOT NULL COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `POS_ID` varchar(30) DEFAULT NULL COMMENT '终端编号',
  `IP_ADDR` varchar(50) DEFAULT NULL COMMENT 'ip地址',
  `MAC` varchar(30) DEFAULT NULL COMMENT 'mac地址',
  `DEPART` varchar(20) DEFAULT NULL COMMENT '所属组织机构',
  `SOURCE_LOCATION` varchar(200) DEFAULT NULL COMMENT '客户端所在地区信息',
  `CARD_CLASS` varchar(255) DEFAULT NULL COMMENT '卡种',
  `BUSS_CODE` varchar(255) DEFAULT NULL COMMENT '业务代码',
  `CARD_CATE` varchar(255) DEFAULT NULL COMMENT '卡类型',
  `CARD_BRAND_ID` varchar(60) DEFAULT NULL COMMENT '卡品牌',
  `ISS_INS_ID` varchar(100) DEFAULT NULL COMMENT '发卡机构',
  `ACQ_INS_ID_CD_IN` varchar(100) DEFAULT NULL COMMENT 'ACQ_INS_ID_CD_IN',
  `ACQ_INS_ID_CD_OUT` varchar(500) DEFAULT NULL COMMENT '受理机构标识码(发送)',
  `ACQ_AREA_CODE` varchar(50) DEFAULT NULL COMMENT '受理地区代码',
  `CERTIF_TP` varchar(255) DEFAULT NULL COMMENT '证件类型',
  `CERTIF_ID` varchar(255) DEFAULT NULL COMMENT '证件编号',
  `CARD_HOLD_NAME` varchar(255) DEFAULT NULL COMMENT '持卡人姓名',
  `CARD_HOLD_SEX` varchar(255) DEFAULT NULL COMMENT '持卡人性别',
  `DIAL_NUMBER` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `TRANS_PASSWORD` varchar(255) DEFAULT NULL COMMENT '有无密码，有密：00',
  `BANK` varchar(255) DEFAULT NULL COMMENT '银行',
  `BANK_ACCOUNT` varchar(255) DEFAULT NULL COMMENT '提现银行帐号',
  `BANK_ACCOUNT_NAME` varchar(255) DEFAULT NULL COMMENT '银行开户帐户名',
  `PAY_DETAIL` varchar(255) DEFAULT NULL COMMENT '支付明细',
  `DATA_TYPE` varchar(255) DEFAULT '' COMMENT '数据类型',
  `SHIP_TO_PROVINCE` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址省份',
  `SHIP_TO_CITY` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址城市',
  `SHIP_TO_AREA` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址地区',
  `SHIP_TO_STREET` varchar(255) NOT NULL DEFAULT '' COMMENT '收货地址街道',
  `ITEM_NUM` bigint(10) DEFAULT '0' COMMENT '商品数量',
  `ITEM_AMOUNT` bigint(10) DEFAULT '0' COMMENT '商品单价',
  `SHIP_TO_NAME` varchar(255) DEFAULT '' COMMENT '收货人',
  `SHIP_TO_MOBILE` varchar(40) DEFAULT NULL COMMENT '收货人手机号码',
  `PAY_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pay_order
-- ----------------------------

-- ----------------------------
-- Table structure for `signed`
-- ----------------------------
DROP TABLE IF EXISTS `signed`;
CREATE TABLE `signed` (
  `PAY_CUST_ID` varchar(20) DEFAULT NULL COMMENT '付款方标志',
  `USER_NAME` varchar(20) DEFAULT NULL COMMENT '用户号',
  `ACCOUNT_ID` varchar(20) DEFAULT NULL COMMENT '账户号',
  `OPERS_ID` varchar(50) NOT NULL COMMENT '操作编号',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '签约状态',
  `SIGNED_TIME` datetime DEFAULT NULL COMMENT '签约时间',
  `CARD_NO` varchar(50) DEFAULT NULL COMMENT '卡号',
  `CARD_ACCOUNT_NAME` varchar(255) DEFAULT NULL COMMENT '持卡人姓名',
  `LAST_UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `CREATE_TIME` datetime DEFAULT NULL,
  `DEPART` varchar(20) DEFAULT NULL COMMENT '所属组织机构',
  `CARD_TYPE` varchar(20) DEFAULT NULL COMMENT '银行卡类型',
  `BANK_ID` varchar(30) DEFAULT NULL COMMENT '银行ID',
  `CERTIFICATE_TYPE` varchar(20) DEFAULT NULL COMMENT '身份种类',
  `CERTIFICATE_NO` varchar(50) DEFAULT NULL COMMENT '身份号码',
  `DAY_COUNT` smallint(6) DEFAULT NULL,
  `MAX_AMOUNT` decimal(20,0) DEFAULT NULL COMMENT '单笔最大金额',
  `DAY_SUM_AMOUNT` decimal(20,0) DEFAULT NULL COMMENT '日最高限额',
  `VALID_MONTH` varchar(2) DEFAULT NULL COMMENT '有效月',
  `VALID_YEAR` varchar(4) DEFAULT NULL COMMENT '有效年',
  `CREATE_IP` varchar(20) DEFAULT NULL COMMENT 'ip地址',
  `MOBILE_PHONE` varchar(30) DEFAULT NULL COMMENT '手机号码',
  `PAY_GATE` varchar(50) DEFAULT NULL COMMENT '支付网关',
  `BANK_CREATE_TIME` datetime DEFAULT NULL COMMENT '银行端创建时间',
  `CARD_FIRST_BIND_TIME` datetime DEFAULT NULL COMMENT '第一次绑定时间',
  `SIGNED_ADDR` varchar(255) DEFAULT '' COMMENT '签约地址',
  PRIMARY KEY (`OPERS_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of signed
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `USER_ID` varchar(20) NOT NULL COMMENT '用户号',
  `ACCOUNT_ID` varchar(20) DEFAULT NULL COMMENT '账户号',
  `LOGIN_ID` varchar(20) DEFAULT NULL COMMENT '登陆号',
  `USER_NAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `STATUS` varchar(10) DEFAULT NULL COMMENT '状态',
  `SEX` varchar(10) DEFAULT NULL COMMENT '性别',
  `BIRTHDAY` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `ACTIVE_TIME` timestamp NULL DEFAULT NULL COMMENT '激活时间',
  `REGISTER_TIME` timestamp NULL DEFAULT NULL COMMENT '注册时间',
  `REGISTER_CITY` varchar(20) DEFAULT NULL COMMENT '注册城市',
  `USER_TYPE` varchar(10) DEFAULT NULL COMMENT '用户类型',
  `ACCOUNT_LEVEL` varchar(10) DEFAULT NULL COMMENT '账户级别',
  `SAFE_LEVEL` varchar(10) DEFAULT NULL COMMENT '安全等级',
  `CREDIT_LEVEL` varchar(10) DEFAULT NULL COMMENT '信誉级别',
  `INDUSTRY_TYPE` varchar(10) DEFAULT NULL COMMENT '职业/行业类别',
  `LINE_AUTHORITY` varchar(10) DEFAULT NULL COMMENT '业务权限',
  `CERTIFICATE_TYPE` varchar(10) DEFAULT NULL COMMENT '证件类型',
  `CERTIFICATE_NO` varchar(50) DEFAULT NULL COMMENT '证件号',
  `EXPIRY_DATE` datetime DEFAULT NULL COMMENT '证件失效日期',
  `REGISTER_ADD` varchar(50) DEFAULT NULL COMMENT '注册地址',
  `DEFAULT_ADD` varchar(100) DEFAULT NULL COMMENT '默认收货地址',
  `SECURITY_TOOL` varchar(20) DEFAULT NULL COMMENT '安全工具',
  `AUTH_TYPE` varchar(10) DEFAULT NULL COMMENT '实名认证状态',
  `AUTH_STATUS` varchar(10) DEFAULT NULL COMMENT '数字证书状态',
  `PHONE` varchar(40) DEFAULT NULL COMMENT '绑定手机号',
  `EMAIL` varchar(50) DEFAULT NULL COMMENT '绑定邮箱',
  `RESERVED_INFO` varchar(30) DEFAULT NULL COMMENT '预留信息',
  `QUESTION` varchar(30) DEFAULT NULL COMMENT '安全问题',
  `ANSWER` varchar(30) DEFAULT NULL COMMENT '安全问题答案',
  `PASSWORD_LESS` varchar(10) DEFAULT NULL COMMENT '是否支持无密支付',
  `BACK_UP` varchar(4000) DEFAULT NULL COMMENT '备用字段',
  `LAST_UPDATE_TIME` datetime NOT NULL COMMENT '最后更新时间',
  `LAST_STATUS` varchar(10) DEFAULT NULL COMMENT '最后更新状态',
  `DEPART` varchar(20) DEFAULT NULL COMMENT '部门号',
  `SOURCE` varchar(255) DEFAULT NULL COMMENT '开户来源',
  `BALANCE` varchar(200) DEFAULT NULL COMMENT '账户余额',
  PRIMARY KEY (`USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
