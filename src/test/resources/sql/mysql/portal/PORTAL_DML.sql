-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '用户组', '1baa263b0fb13a20768551fc6d123e68', 'admin', '管理员', '1', 'admin@qq.com', '13812345678', '2015-04-07 14:21:59', '2015-04-07 14:21:51', 'admin', null, '1');

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('1', '管理员', '', '1', '2015-04-02 10:45:16', '2015-05-04 15:12:48', 'admin');

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES ('1', '1', '1');

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('7', '1', '受理状态', '1', '待初审', '1', '案件列表', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('8', '1', '受理状态', '2', '初审中', '1', '案件列表', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('9', '3', '来源', '1', '规则触发', '1', '案件列表', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('10', '3', '来源', '2', '人工报案', '1', '案件列表', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('11', '2', '案件类型', '1', '盗账户-支出', '1', '案件列表', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('12', '2', '案件类型', '2', '盗账户-操作', '1', '案件列表', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('13', '4', '添加关联身份', 'userId', '用户', '1', '创建案件', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('14', '4', '添加关联身份', 'cardId', '银行卡', '1', '创建案件', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('15', '4', '添加关联身份', 'merchantId', '商户', '1', '创建案件', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('23', '1', '受理状态', '3', '初审完结', '1', '案件列表', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('24', '2', '案件类型', '3', '盗卡-快捷', '1', '案件列表', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('25', '6', '案件身份', '1', '被盗者', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('26', '6', '案件身份', '2', '盗用者', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('27', '7', '关联交易', '1', '交易号', '1', '关联交易', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('28', '7', '关联交易', '2', '银行支付号', '1', '关联交易', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('29', '8', '校验码泄露渠道', '1', '安装证书泄露', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('30', '8', '校验码泄露渠道', '2', '防火墙泄露', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('31', '9', '风险原因', '1', '扫号', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('32', '9', '风险原因', '2', '钓鱼', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('33', '10', '手机换绑方式', '1', '支付密码换绑', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('34', '10', '手机换绑方式', '2', '安全问题换绑', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('35', '11', '支付方式', '1', '快捷支付', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('36', '11', '支付方式', '2', '网银支付', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('37', '12', '支付渠道', '1', 'PC', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('38', '12', '支付渠道', '2', '无线', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('39', '13', '销赃渠道', '1', '外部商家', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('40', '13', '销赃渠道', '2', '站内转账', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('41', '14', '资金来源', '1', '借记卡', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('42', '14', '资金来源', '2', '信用卡', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('43', '15', '初审结案', '1', '全损结案', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('44', '15', '初审结案', '2', '其他案件', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('45', '15', '初审结案', '3', '非案件', '1', '案件详情', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('46', '16', '复审结果', '1', '退回初审', '1', '案件详情', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('47', '16', '复审结果', '2', '通过', '1', '案件详情', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('48', '1', '受理状态', '5', '复审完结', '1', '案件列表', '5', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('22', '5', '受理状态', '2', '已处理', '1', '核查单列表', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('20', '5', '受理状态', '0', '待处理', '1', '核查单列表', '1', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('21', '5', '受理状态', '1', '处理中', '1', '核查单列表', '2', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('64', '2', '案件类型', '4', '个人欺诈', '1', '案件列表', '4', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('65', '2', '案件类型', '5', '商户欺诈', '1', '案件列表', '5', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('66', '6', '案件身份', '3', '第三方', '1', '案件详情', '3', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('49', '1', '受理状态', '4', '复审中', '1', '案件列表', '4', '2015-04-08 09:57:56', '2015-04-08 09:58:02', 'admin');
INSERT INTO `system_config` VALUES ('72', '17', '线下核查单处理', '0', '待处理', '1', '', '0', '2015-05-05 11:21:51', '2015-05-05 11:21:51', 'admin');
INSERT INTO `system_config` VALUES ('73', '17', '线下核查单处理', '1', '待复核', '1', '', '1', '2015-05-05 11:22:50', '2015-05-05 11:22:50', 'admin');
INSERT INTO `system_config` VALUES ('74', '18', '线下核查单状态', '0', '待处理', '1', '', '0', '2015-05-05 11:23:43', '2015-05-05 11:23:43', 'admin');
INSERT INTO `system_config` VALUES ('75', '18', '线下核查单状态', '1', '待复核', '1', '', '1', '2015-05-05 11:24:02', '2015-05-05 11:24:46', 'admin');
INSERT INTO `system_config` VALUES ('76', '18', '线下核查单状态', '2', '已复核', '1', '', '2', '2015-05-05 11:24:36', '2015-05-05 11:24:36', 'admin');

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('85', '77', '1', '身份证黑名单', '3', '1', '', '', 'certificateblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('86', '77', '1', 'IP黑名单', '3', '1', '', '', 'ipblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('87', '78', '1', '用户白名单', '3', '1', '', '', 'userwhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('88', '78', '1', '商户白名单', '3', '1', '', '', 'merwhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('89', '78', '1', '银行卡白名单', '3', '1', '', '', 'cardwhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('90', '78', '1', '电话号码白名单', '3', '1', '', '', 'phonewhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('91', '78', '1', '身份证白名单', '3', '1', '', '', 'certificatewhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('242', '240', '1', '核查单查询', '2', '1', '', '', 'offlinechecklistqueryview', 'role-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('184', '15', '1', '复审', '3', '0', '', '8102', '', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('185', '178', '1', '任务执行管理', '2', '1', '', '', 'taskExecuteManageTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('198', '188', '1', '测试', '2', '1', '', '', 'testView', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('199', '1', '0', '新增模块', '1', '1', '', '', '', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('155', '7', '1', '核查', '3', '0', '', '1010', '', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('156', '153', '1', '反查参数配置', '2', '1', '', '', 'reverseConfigTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('182', '178', '1', '商品类型管理', '2', '1', '', '', 'CommodityTypeManageTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '3');
INSERT INTO `resources` VALUES ('226', '80', '1', '联合国实体名单新增', '3', '0', '', '', 'updateentityhorrorAdddetail', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('227', '80', '1', '联合国个人名单新增页', '3', '0', '', '', 'updatehorrorAdddetail', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('228', '80', '1', '联合国实体详情页', '3', '0', '', '', 'updateentityhorrorlistdetail', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('229', '80', '1', '联合国个人详情页', '3', '0', '', '', 'updatehorrorlistdetail', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('230', '103', '1', '公安部实体详情页', '3', '0', '', '', 'policeupdateentityhorrorlistdetail', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('231', '103', '1', '公安部个人详情页', '3', '0', '', '', 'policeUpdateIndividualHorrorListMgrDetailTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('232', '103', '1', '公安部个人新增页', '3', '0', '', '', 'policeUpdateHorrorListMgrAddTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('233', '103', '1', '公安部实体新增页', '3', '0', '', '', 'policeEntityHorrorListAddTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('234', '2', '0', '测试', '2', '1', '', '', 'mainview', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('235', '188', '1', '风险大盘', '2', '1', '', '', 'mainview', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('237', '1', '1', '风险管理平台', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'credit', '-1000');
INSERT INTO `resources` VALUES ('238', '237', '1', '风险控制', '2', '1', '', '', 'checklistview', 'role-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'credit', '0');
INSERT INTO `resources` VALUES ('14', '1', '1', '案件管理平台', '1', '1', '', '8000', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('15', '14', '1', '案件查询', '2', '1', '', '8100', 'caselistview', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('78', '76', '1', '白名单', '2', '1', '', '', 'navigator-system', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '3');
INSERT INTO `resources` VALUES ('79', '76', '1', '灰名单', '2', '1', '', '', 'newgraylistmgr', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('80', '76', '1', '联合国恐怖名单', '2', '1', '', '', 'updatehorrorlistmgr', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '4');
INSERT INTO `resources` VALUES ('73', '2', '1', '维度信息设置', '2', '1', '', '', 'dimensionView', 'dimension-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '4');
INSERT INTO `resources` VALUES ('81', '77', '1', '用户黑名单', '3', '1', '', '', 'userblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'cjq1', '0');
INSERT INTO `resources` VALUES ('77', '76', '1', '黑名单', '2', '1', '', '', 'navigator-system', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('76', '1', '1', '名单管理', '1', '1', '', '1', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '3');
INSERT INTO `resources` VALUES ('82', '77', '1', '商户黑名单', '3', '1', '', '', 'merblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('84', '77', '1', '电话号码黑名单', '3', '1', '', '', 'phoneblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('83', '77', '1', '银行卡黑名单', '3', '1', '', '', 'cardblacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('147', '140', '1', '数据包及上报内容统计', '2', '1', '', '', 'datapacketstatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('148', '140', '1', '报文类型统计', '2', '1', '', '', 'messagetypestatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('149', '140', '1', '回执解析统计', '2', '1', '', '', 'receiptstatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('150', '140', '1', '客户信息统计', '2', '1', '', '', 'customerinfotatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('180', '178', '1', '关键字管理', '2', '1', '', '', 'keywordManageTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('236', '2', '1', '用户队列管理', '2', '1', '', '', 'assignuserlisttab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('195', '185', '1', '任务执行详情页', '3', '0', '', '', 'taskExecuteDetailTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('240', '1', '1', '线下核查单管理平台', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('241', '240', '1', '待处理核查单', '2', '1', '', '', 'offlinechecklistview', 'role-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('6', '1', '1', '核查单管理平台', '1', '1', '', '0', 'www.sina.com', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('7', '6', '1', '核查单管理', '2', '1', '', '2', 'checklistview', 'role-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('158', '15', '1', '初审(案件)', '3', '0', '', '8101', '', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('191', '190', '0', '数据A', '3', '1', '', '', 'dataA', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('192', '190', '0', '数据B', '3', '1', '', '', 'dataB', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('258', '78', '1', '所有白名单', '3', '1', '', '', 'whitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('128', '2', '1', '系统配置管理', '2', '1', '', '', 'systemConfigView', 'assign-edit-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '5');
INSERT INTO `resources` VALUES ('141', '2', '1', '基础代码表', '2', '0', '', '', '', 'customers-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '6');
INSERT INTO `resources` VALUES ('142', '141', '1', '货币代码表', '3', '1', '', '', 'currencyView', 'bizreport-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('143', '141', '1', '国家地区代码表', '3', '1', '', '', 'countriesView', 'user-group-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('144', '141', '1', '行政区划分代码表', '3', '1', '', '', 'regierungsbezirkView', 'navigator-brush', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('1', '0', '1', '风控后台', '0', '1', '', '3', 'www.baidu.com', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('2', '1', '1', '系统设置', '1', '1', '', '', 'www.qq.com', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '8');
INSERT INTO `resources` VALUES ('3', '2', '1', '系统用户管理', '2', '1', '', '1001', 'usersView', 'user-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('4', '2', '1', '系统角色管理', '2', '1', '', '', 'rolesView', 'role-icon', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('5', '2', '1', '系统资源管理', '2', '1', '', '', 'resourcesView', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '7');
INSERT INTO `resources` VALUES ('178', '1', '1', '网络爬虫管理', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '5');
INSERT INTO `resources` VALUES ('72', '2', '1', '风险等级设置', '2', '1', '', '', 'riskLevelView', 'navigator-risklevel', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '3');
INSERT INTO `resources` VALUES ('196', '181', '1', '任务详情', '3', '0', '', '', 'taskDetailTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('146', '140', '1', '可疑交易定性统计', '2', '1', '', '', 'suspicioustransquastatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('92', '78', '1', 'IP白名单', '3', '1', '', '', 'ipwhitelistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('103', '76', '1', '公安部恐怖名单', '2', '1', '', '', 'policeupdatehorrorlistmgr', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '5');
INSERT INTO `resources` VALUES ('181', '178', '1', '任务管理', '2', '1', '', '', 'taskManageTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('186', '2', '0', '仪表盘', '2', '1', '', '', 'dashboard', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('187', '2', '0', '仪表盘', '2', '1', '', '', 'dashboard', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('193', '2', '1', '我的账号', '2', '0', '', '', 'accountView', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('197', '195', '1', '查看地址', '4', '0', '', '', 'localAddressTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('210', '1', '1', '报表平台', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '7');
INSERT INTO `resources` VALUES ('211', '210', '1', '可疑交易定性统计', '2', '0', '', '', 'suspicioustransquastatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('212', '210', '1', '数据包及上报内容统计', '2', '0', '', '', 'datapacketstatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('213', '210', '1', '报文类型统计', '2', '0', '', '', 'messagetypestatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('214', '210', '1', '回执解析统计', '2', '0', '', '', 'receiptstatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('215', '210', '1', '客户信息统计', '2', '1', '', '', 'customerinfotatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('216', '210', '1', '规则明细统计', '2', '1', '', '', 'rulesdetailstatementtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('217', '210', '0', '报表列表管理', '2', '1', '', '', 'reportlistview', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'cjq1', '0');
INSERT INTO `resources` VALUES ('218', '210', '1', '商户风险统计表', '2', '1', '', '', 'reportView?reportId=2', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('153', '1', '1', '反查平台', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('154', '153', '1', '反查页面', '2', '1', '', '', 'reverseTab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('219', '210', '1', '风控规则有效率表', '2', '1', '', '', 'reportView?reportId=3', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('220', '210', '1', '案件风险统计报表', '2', '1', '', '', 'reportView?reportId=4', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('221', '210', '1', '工作效率统计报表', '2', '1', '', '', 'reportView?reportId=5', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('222', '210', '1', '案件商户统计报表', '2', '1', '', '', 'reportView?reportId=6', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('223', '210', '1', '案件银行卡统计报表', '2', '1', '', '', 'reportView?reportId=7', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('224', '210', '1', '泄露渠道统计报表', '2', '1', '', '', 'reportView?reportId=8', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('225', '210', '1', '案件泄露方式统计报表', '2', '1', '', '', 'reportView?reportId=9', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('189', '188', '0', '模块一', '2', '1', '', '', 'oneView', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('190', '188', '0', '模块二', '2', '1', '', '', 'twoView', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '2');
INSERT INTO `resources` VALUES ('194', '188', '1', '图表资源管理', '2', '1', '', '', 'chatresourceView', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '1');
INSERT INTO `resources` VALUES ('200', '1', '1', '客户风险档案', '1', '1', '', '', '', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '4');
INSERT INTO `resources` VALUES ('201', '200', '1', '用户评级列表', '2', '1', '', '', 'ratinglisttab1', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('202', '200', '1', '平台商户评级列表', '2', '1', '', '', 'ratinglisttab2', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('203', '200', '1', '线下POS商户评级列表', '2', '1', '', '', 'ratinglisttab3', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('204', '200', '1', 'QPOS商户评级列表', '2', '1', '', '', 'ratinglisttab4', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('205', '200', '1', '用户评级风险子项配置', '2', '1', '', '', 'risklevelconfig1', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('206', '200', '1', '平台商户评级风险子项配置', '2', '1', '', '', 'risklevelconfig2', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('207', '200', '1', '线下POS商户评级风险子项配置', '2', '1', '', '', 'risklevelconfig3', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('208', '200', '1', 'QPOS商户评级风险子项配置', '2', '1', '', '', 'risklevelconfig4', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('188', '1', '1', '仪表盘', '1', '1', '', '', 'dashboard', 'navigator-system', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '6');
INSERT INTO `resources` VALUES ('209', '200', '1', '风险评级设置', '2', '1', '', '', 'riskcustomerleveltab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', null);
INSERT INTO `resources` VALUES ('239', '77', '0', '用户其他名单', '3', '1', '', '', '', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'cjq1', '0');
INSERT INTO `resources` VALUES ('243', '76', '0', '所有黑名单', '2', '1', '', '', 'blacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'cjq1', '0');
INSERT INTO `resources` VALUES ('244', '77', '1', '所有黑名单', '3', '1', '', '', 'blacklistmgrtab', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'cjq1', '0');
INSERT INTO `resources` VALUES ('259', '210', '1', '规则明细统计报表', '2', '1', '', '', 'reportView?reportId=1', '', '2015-05-19 10:15:11', '2015-05-19 10:15:11', 'admin', '0');
INSERT INTO `resources` VALUES ('263', '260', '1', '支付信息单查询', '2', '1', null, '', 'paylistinfocheckview', 'navigator-system', '2015-05-19 11:10:16', '2015-05-19 11:12:09', 'admin', '0');
INSERT INTO `resources` VALUES ('264', '260', '1', '签约信息查询', '2', '1', null, '', 'ContractInfoQuery', 'navigator-system', '2015-05-19 16:09:18', '2015-05-19 16:09:41', 'admin', '1');
INSERT INTO `resources` VALUES ('261', '260', '1', '物理信息查询', '2', '1', null, '', 'phyinfoview', 'navigator-system', '2015-05-19 10:23:46', '2015-05-19 11:12:25', 'admin', '3');
INSERT INTO `resources` VALUES ('260', '1', '1', '关联查询', '1', '1', null, '', '', 'navigator-system', '2015-05-19 10:23:11', '2015-05-19 14:29:57', 'admin', '10');
INSERT INTO `resources` VALUES ('262', '260', '1', '用户信息查询', '2', '1', null, '', 'userinfoview', 'navigator-system', '2015-05-19 10:26:20', '2015-05-19 11:12:15', 'admin', '1');

-- ----------------------------
-- Records of roles_resources
-- ----------------------------
INSERT INTO `roles_resources` VALUES (1, 1, 128);
INSERT INTO `roles_resources` VALUES (2, 1, 73);
INSERT INTO `roles_resources` VALUES (3, 1, 72);
INSERT INTO `roles_resources` VALUES (4, 1, 4);
INSERT INTO `roles_resources` VALUES (5, 1, 3);
INSERT INTO `roles_resources` VALUES (6, 1, 1);
INSERT INTO `roles_resources` VALUES (7, 1, 5);
INSERT INTO `roles_resources` VALUES (8, 1, 2);
