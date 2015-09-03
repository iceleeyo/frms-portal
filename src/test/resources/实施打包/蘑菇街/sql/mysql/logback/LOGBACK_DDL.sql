-- ----------------------------
-- Table structure for logging_event
-- ----------------------------
DROP TABLE IF EXISTS `logging_event`;
CREATE TABLE `logging_event` (
  `TIMESTMP` datetime NOT NULL,
  `FORMATTED_MESSAGE` varchar(4000) NOT NULL,
  `LOGGER_NAME` varchar(255) NOT NULL,
  `LEVEL_STRING` varchar(255) NOT NULL,
  `THREAD_NAME` varchar(255) DEFAULT NULL,
  `REFERENCE_FLAG` int(255) DEFAULT NULL,
  `ARG0` varchar(255) DEFAULT NULL,
  `ARG1` varchar(255) DEFAULT NULL,
  `ARG2` varchar(255) DEFAULT NULL,
  `ARG3` varchar(255) DEFAULT NULL,
  `CALLER_FILENAME` varchar(255) NOT NULL,
  `CALLER_CLASS` varchar(255) NOT NULL,
  `CALLER_METHOD` varchar(255) NOT NULL,
  `CALLER_LINE` varchar(255) NOT NULL,
  `EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`EVENT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logging_event_exception
-- ----------------------------
DROP TABLE IF EXISTS `logging_event_exception`;
CREATE TABLE `logging_event_exception` (
  `EVENT_ID` int(11) NOT NULL,
  `I` int(11) NOT NULL,
  `TRACE_LINE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`,`I`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logging_event_property
-- ----------------------------
DROP TABLE IF EXISTS `logging_event_property`;
CREATE TABLE `logging_event_property` (
  `EVENT_ID` int(11) NOT NULL,
  `MAPPED_KEY` varchar(255) NOT NULL,
  `MAPPED_VALUE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EVENT_ID`,`MAPPED_KEY`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for regierungsbezirk_code
-- ----------------------------
DROP TABLE IF EXISTS `regierungsbezirk_code`;
CREATE TABLE `regierungsbezirk_code` (
  `ID` int(11) NOT NULL COMMENT '主键',
  `CODE` varchar(10) DEFAULT NULL COMMENT '行政区编码',
  `NAME` varchar(50) DEFAULT NULL COMMENT '行政区名',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  `MODIFER` varchar(255) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;