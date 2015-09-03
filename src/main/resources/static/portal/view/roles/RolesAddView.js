var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
Ext.define('portal.view.roles.RolesAddView', {
	extend : 'Ext.window.Window',
	alias : 'widget.rolesAddView',
	requires : [ 'portal.view.roles.RolesItemRenderer' ],
	title : '新建角色',
	layout : 'border',
	iconCls : 'role-icon',
	autoShow : false,
	autoScroll : true,
	autoHeight : true,
	height : 550,
	width : 600,
	draggable : true,
	modal : true,
	initComponent : function() {
		this.items = [ {
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
					fieldLabel : '角色名称',
					allowBlank : false,
					afterLabelTextTpl : required,
					name : 'roleName',
					margin : '5 10 5 10'
				}, {
					fieldLabel : '状&nbsp; &nbsp;态',
					xtype : 'checkboxfield',
					name : 'enabled',
					boxLabel : '是否启用',
					checked : true,
					margin : '5 10 5 10'
				}, {
					xtype : 'textareafield',
					name : 'roleDesc',
					fieldLabel : '角色描述',
					margin : '5 10 5 10',
					rows : 20
				} ]
			} ]
		}, {
			xtype : 'panel',
			region : 'east',
			autoScroll : true,
			title : '选择权限',
			width : '40%',
			height : '80%',
			listeners : {
				beforerender : function(tab, opts) {
					var nodes = [];
					Ext.Ajax.request({
						url : 'roles/loadRoles',
						method : 'GET',
						async : false,
						success : function(response) {
							var obj = Ext.JSON.decode(response.responseText);
							if (obj.success) {
								nodes = obj.data;
							}
						}
					});
					Ext.Array.each(nodes, function(node) {
						var treeStore = Ext.create('Ext.data.TreeStore', {
							defaultRootId : '1',
							defaultRootText : '风控后台',
							model : 'portal.store.util.MenuInfoModel',
							proxy : {
								type : 'memory'
							},
							root : {
								checked : false,
								expanded : true,
								children : node.children
							}
						});
						tab.add({
							xtype : 'rolesItemRenderer',
							iconCls : node.iconCls,
							store : treeStore
						});
				    });
				}
			}
		} ];

		this.buttons = [ {
			text : '确定',
			action : 'add'
		}, {
			text : '取消',
			scope : this,
			handler : function(btn) {
				this.close();
			}
		} ];
		this.callParent(arguments);
	}
});