Ext.apply(Ext.form.VTypes, {
	passwordConfirmation : function(value, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (value == pwd.getValue());
		}
		return true;
	},
	passwordConfirmationText : '密码不一致!'
});
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('portal.view.users.UsersEditView', {
	extend : 'Ext.window.Window',
	alias : 'widget.usersEditView',
	iconCls : 'user-icon',
	modal : true,
	layout : 'border',
	height : 500,
	width : 600,
	title : '编辑用户',
	items : [ {
		xtype : 'form',
		region : 'west',
		title : '基本信息',
		width : '60%',
		height : '100%',
		items : [ {
			xtype : 'fieldset',
			border : false,
			layout : {
				type : 'table',
				columns : 1
			},
			height : '100%',
			margin : '5',
			fieldDefaults : {
				labelWidth : 80,
				labelAlign : 'right'
			},
			defaults : {
				anchor : '100%',
				width : 300
			},
			items : [ {
				xtype : 'textfield',
				fieldLabel : '用户ID',
				allowBlank : false,
				afterLabelTextTpl : required,
				name : 'id',
				margin : '5 10 5 10',
				hidden : true
			}, {
				xtype : 'textfield',
				fieldLabel : '用户名称',
				allowBlank : false,
				afterLabelTextTpl : required,
				name : 'userName',
				margin : '5 10 5 10'
			}, {
				xtype : 'textfield',
				fieldLabel : '密&nbsp; &nbsp;码',
				name : 'password',
				inputType : 'password',
				afterLabelTextTpl : required,
				id : 'pass',
				margin : '5 10 5 10',
				listeners : {
					change : function(text, newValue, oldValue, eOpts ) {
						if(newValue) {
							text.nextSibling().allowBlank = false;
						} else {
							text.nextSibling().allowBlank = true;
						}
						text.nextSibling().validate();
					}
				}
			}, {
				xtype : 'textfield',
				fieldLabel : '确认密码',
				inputType : 'password',
				afterLabelTextTpl : required,
				submitValue : false,
				vtype : 'passwordConfirmation',
				initialPassField : 'pass',
				margin : '5 10 5 10'
			}, {
				xtype : 'textfield',
				fieldLabel : '真实姓名',
				name : 'realName',
				margin : '5 10 5 10'
			}, {
				xtype : 'textfield',
				fieldLabel : '头&nbsp; &nbsp;衔',
				name : 'title',
				margin : '5 10 5 10'
			}, {
				xtype : 'textfield',
				fieldLabel : '手机号码',
				name : 'mobile',
				regex : /^(1(([35][0-9])|(47)|[8][01236789]))\d{8}$/i,
				margin : '5 10 5 10',
				regexText : '请填写正确格式的手机号码，例如13812345678'
			}, {
				xtype : 'textfield',
				fieldLabel : '电子邮件',
				name : 'email',
				vtype : 'email',
				margin : '5 10 5 10'
			}, {
				fieldLabel : '状&nbsp; &nbsp;态',
				xtype : 'checkboxfield',
				name : 'enabled',
				boxLabel : '是否启用',
				checked : true,
				margin : '5 10 5 10'
			} ]
		} ]
	}, {
		xtype : 'grid',
		store : 'users.UsersRolesStore',
		selType : 'checkboxmodel',
		title : '请选择角色',
		selModel : {
			mode : 'MULTI'
		},
		region : 'east',
		height : '80%',
		width : '40%',
		columns : [ {
			text : '角色号',
			dataIndex : 'rolesId',
			width : '50%'
		}, {
			text : '角色名',
			dataIndex : 'rolesName',
			width : '50%'
		} ]
	} ],
	buttons : [ {
		xtype : 'button',
		text : '确定'
	}, {
		xtype : 'button',
		text : '取消',
		handler : function(btn) {
			btn.up('usersEditView').close();
		}
	} ],
	buttonAlign : 'right',
	config : {
		roles : undefined
	},
	constructor : function(cfg) {
		this.callParent(arguments);
		this.initConfig(cfg);
	},
	initComponent : function() {
		this.callParent(arguments);
	}
});