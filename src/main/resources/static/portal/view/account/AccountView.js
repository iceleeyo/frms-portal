Ext.define('portal.view.account.AccountView', {
	extend : 'portal.view.panel.UpdatablePanel',
	alias : 'widget.accountView',
	title : '我的账号',
	iconCls : 'user-icon',
	autoDestroy : true,
	autoScroll : true,
	layout : {
		type : 'vbox'
	},
	width : '100%',
	items : [ {
		xtype : 'form',
		border : false,
		margin : '3 3 0 3',
		width : '100%',
		height : 300,
		frame : true,
		items : [ {
			xtype : 'fieldset',
			collapsible : true,
			fieldDefaults : {
				labelWidth : 100,
				labelAlign : 'right'
			},
			padding : 20,
			title : '账户信息',
			height : 290,
			defaultType : 'displayfield',
			items : [ {
				fieldLabel : '登录号',
				name : 'id',
				hidden : true
			}, {
				fieldLabel : '登录名',
				name : 'userName'
			}, {
				fieldLabel : '真实姓名',
				name : 'realName'
			}, {
				fieldLabel : '头衔',
				name : 'title'
			}, {
				fieldLabel : '所属角色',
				name : 'roleNames'
			}, {
				fieldLabel : '电子邮件',
				name : 'email'
			}, {
				fieldLabel : '手机号码',
				name : 'mobile'
			}, {
				fieldLabel : '创建时间',
				name : 'createTime',
				renderer : Ext.util.Format.dateRenderer('Y年m月d日 H时i分s秒')
			}, {
				fieldLabel : '更新时间',
				name : 'updateTime',
				renderer : Ext.util.Format.dateRenderer('Y年m月d日 H时i分s秒')
			} ]
		} ]
	}, {
		xtype : 'form',
		border : false,
		margin : '3 3 0 3',
		width : '100%',
		height : 300,
		frame : true,
		fieldDefaults : {
			labelWidth : 120,
			labelAlign : 'right',
			anchor : '50%'
		},
		items : [ {
			xtype : 'fieldset',
			collapsible : true,
			title : '修改资料',
			height : 290,
			padding : 20,
			items : [ {
				xtype : 'textfield',
				fieldLabel : '请输入原密码',
				name : 'originalPassword',
				inputType : 'password',
				allowBlank : false,
				blankText : '该输入项不能为空'
			}, {
				xtype : 'textfield',
				fieldLabel : '请输入新密码',
				name : 'newPassword',
				inputType : 'password',
				vtype : 'passwordNotLike',
				initialPassField : 'originalPassword',
				confirmTo : "originalPassword",
				vtypeText : '新，旧密码不一致！'
			}, {
				xtype : 'textfield',
				fieldLabel : '请再次输入新密码',
				name : 'newPasswordAgain',
				inputType : 'password',
				vtype : 'passwordLike',
				initialPassField : 'newPassword',
				vtypeText : '两次密码不一致，请重新输入！',
				confirmTo : "newPassword"
			}, {
				xtype : 'textfield',
				fieldLabel : '新手机号码',
				name : 'mobile',
				regex : /^(1(([35][0-9])|(47)|[8][01236789]))\d{8}$/i,
				regexText : '请填写正确格式的手机号码，例如13812345678'
			}, {
				xtype : 'textfield',
				fieldLabel : '新电子邮件',
				name : 'email',
				vtype : 'email'
			}, {
				xtype : 'button',
				buttonAlign : 'left',
				text : '保存',
				iconCls : 'button-ok',
				margin : '0 0 0 120'
			}, {
				xtype : 'button',
				buttonAlign : 'left',
				text : '重置',
				iconCls : 'button-cancel',
				margin : '0 0 0 20'
			} ]
		} ]
	} ]
});
Ext.apply(Ext.form.VTypes, {
	passwordLike : function(val, field) {
		var form = field.up('form');
		if (field.confirmTo) {
			var pwd = form.down('textfield[name=' + field.confirmTo + ']');
			return (val == pwd.getValue());
		}
		return false;
	},
	passwordNotLike : function(val, field) {
		var form = field.up('form');
		if (field.confirmTo) {
			var pwd = form.down('textfield[name=' + field.confirmTo + ']');
			return (val != pwd.getValue());
		}
		return true;
	}
});