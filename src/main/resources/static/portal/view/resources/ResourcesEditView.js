Ext.define('portal.view.resources.ResourcesEditView', {
	extend : 'Ext.window.Window',
	alias : 'widget.resourcesEditView',
	autoShow : true,
	closable : true,
	modal : true,
	title : '编辑权限',
	width : 400,
	border : 'none',
	items : [ {
		xtype : 'form',
		frame : true,
		style : {
			border : 'none',
			padding : '10px'
		},
		fieldDefaults : {
			labelAlign : 'right',
			msgTarget : 'side'
		},
		items : [ {
			xtype : 'textfield',
			fieldLabel : '权限名称',
			name : 'text'
		}, {
			xtype : 'textfield',
			fieldLabel : '资源地址',
			name : 'qtitle',
			emptyText : '例如:usersView'
		}, {
			xtype : 'textfield',
			fieldLabel : '资源图像地址',
			name : 'iconCls',
			emptyText : '例如:tab-edit-icon'
		}, {
			fieldLabel : '菜单类型',
			xtype : 'checkboxfield',
			name : 'type',
			boxLabel : '菜单',
			checked : true
		}, {
			fieldLabel : '打开方式',
			xtype : 'checkboxfield',
			name : 'openType',
			boxLabel : '新窗口打开'
		}, {
			xtype : 'textfield',
			fieldLabel : '资源编号',
			name : 'code',
			emptyText : '例如:1010'
		}, {
			xtype : 'numberfield',
			fieldLabel : '排序字段',
			name : 'orderField'
		}, {
			xtype : 'hiddenfield',
			name : 'level',
			id : 'level'
		}, {
			xtype : 'hiddenfield',
			name : 'parentId',
			id : 'parentId'
		} ]
	} ],

	buttons : [ {
		text : '保存',
		iconCls : 'button-ok',
		action : 'edit'
	}, {
		text : '取消',
		iconCls : 'button-cancel',
		action : 'cancel',
		handler : function(btn) {
			btn.up('resourcesEditView').close();
		}
	} ],

	initComponent : function() {
		this.callParent(arguments);
	}
});