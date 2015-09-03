var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('portal.view.systemconfig.SystemConfigAddView', {
	extend : 'Ext.window.Window',
	alias : 'widget.systemConfigAddView',
	title : '新建系统配置',
	layout : 'border',
	iconCls : 'assign-edit-icon',
	autoShow : false,
	autoScroll : true,
	autoHeight : true,
	height : 420,
	width : 400,
	draggable : true,
	modal : true,
	initComponent : function() {
		this.items = [ {
			xtype : 'form',
			bodyStyle : {
				padding : '10px'
			},
			border : false,
			region : 'center',
			width : '100%',
			height : '100%',
			items : [ {
				fieldLabel : '类型',
				xtype : 'numberfield',
				name : 'type',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				minValue : 0
			}, {
				fieldLabel : '类型名称',
				xtype : 'textfield',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				name : 'typeName'
			}, {
				fieldLabel : '代码',
				xtype : 'textfield',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				name : 'code'
			}, {
				fieldLabel : '名称',
				xtype : 'textfield',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				name : 'value'
			}, {
				fieldLabel : '排序',
				xtype : 'numberfield',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				name : 'orderBy'
			}, {
				fieldLabel : '状&nbsp; &nbsp;态',
				xtype : 'checkboxfield',
				allowBlank : false,
				labelAlign : "right",
				afterLabelTextTpl : required,
				name : 'enabled',
				boxLabel : '是否启用',
				checked : true
			}, {
				xtype : 'textareafield',
				name : 'remark',
				labelAlign : "right",
				fieldLabel : '备注',
				rows : 10
			} ]
		} ];

		this.buttons = [ {
			text : '确定',
			action : 'add'
		}, {
			text : '取消',
			scope : this,
			handler : function() {
				this.close();
			}
		} ];
		this.callParent(arguments);
	}
});