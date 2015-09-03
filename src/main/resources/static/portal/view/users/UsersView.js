Ext.define('portal.view.users.UsersView', {
	extend : 'portal.view.panel.UpdatablePanel',
	alias : 'widget.usersView',
	requires : [ 'Ext.ux.grid.Printer' ],
	title : '系统用户管理',
	iconCls : 'user-icon',
	layout : {
		type : 'fit'
	},
	gridTitle : '系统用户管理',
	items : [ {
		xtype : 'grid',
		store : 'users.UsersStore',
		selType : 'checkboxmodel',
		selModel : {
			checkSelector : 'td.x-grid-cell-row-checker'
		},
		border : false,
		columnLines : true,
		columns : [ {
			text : '用户ID',
			dataIndex : 'id',
			hidden : true
		}, {
			text : '登录名',
			dataIndex : 'userName',
			cls : 'ux-grid-header',
			align : 'center',
			width : '10%',
			renderer : function(val) {
				var value = val.replace(/\s/g, '&nbsp;');
				return value;
			}
		}, {
			text : '所属角色',
			dataIndex : 'roleNames',
			cls : 'ux-grid-header',
			align : 'center',
			flex : 1
		}, {
			text : '真实姓名',
			dataIndex : 'realName',
			cls : 'ux-grid-header',
			align : 'center',
			width : '10%'
		}, {
			text : '头衔',
			dataIndex : 'title',
			cls : 'ux-grid-header',
			align : 'center',
			width : '6%'
		}, {
			text : '手机号码',
			dataIndex : 'mobile',
			cls : 'ux-grid-header',
			align : 'center',
			width : '9%'
		}, {
			text : '电子邮件',
			dataIndex : 'email',
			cls : 'ux-grid-header',
			align : 'center',
			width : '10%'
		}, {
			text : '状态',
			align : 'center',
			dataIndex : 'enabled',
			cls : 'ux-grid-header',
			width : '5%',
			renderer : function(val) {
				if (val == 1) {
					return '<span style="color:green;">启用</span>';
				} else {
					return '<span style="color:red;">禁用</span>';
				}
				return val;
			}
		}, {
			text : '创建时间',
			dataIndex : 'createTime',
			cls : 'ux-grid-header',
			width : '12%',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			text : '最后更新时间',
			dataIndex : 'updateTime',
			cls : 'ux-grid-header',
			width : '12%',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			text : '更新者',
			cls : 'ux-grid-header',
			dataIndex : 'modifer',
			width : '5%'
		}, {
			text : '修改',
			cls : 'ux-grid-header',
			menuDisabled : true,
			sortable : false,
			xtype : 'actioncolumn',
			itemId : 'actioncolumn',
			align : 'center',
			width : 60,
			items : [ {
				iconCls : 'tab-edit-icon',
				tooltip : '修改当前记录'
			} ]
		} ],
		tbar : {
			xtype : 'toolbar',
			items : [ {
				xtype : 'button',
				iconCls : 'tab-add-icon',
				itemId : 'add',
				text : '新建',
				tooltip : '新建用户'
			}, {
				xtype : 'tbseparator',
				itemId : 'add_separator'
			}, {
				xtype : 'button',
				iconCls : 'tab-edit-icon',
				itemId : 'edit',
				text : '修改',
				tooltip : '修改选中的用户(s)'
			}, {
				xtype : 'tbseparator',
				itemId : 'edit_separator'
			}, {
				xtype : 'button',
				text : '删除',
				itemId : 'delete',
				iconCls : 'delete-icon',
				tooltip : '删除选中的用户(s)'
			}, {
				xtype : 'tbseparator',
				itemId : 'delete_separator'
			}, {
				xtype : 'textfield',
				name : 'loginName',
				labelAlign : 'right',
				fieldLabel : '登录名'
			}, {
				xtype : 'combo',
				displayField : 'roleName',
				valueField : 'id',
				store : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ {
						name : 'id',
						type : 'int'
					}, {
						name : 'roleName',
						type : 'String'
					} ],
					proxy : {
						type : 'ajax',
						url : 'roles/list',
						reader : {
							type : 'json',
							root : 'data',
							successProperty : 'success'
						}
					},
					listeners : {
						load : function(store, records, options) {
							var data = {
								"id" : "-1",
								"roleName" : "全部角色"
							};
							store.insert(0, data);
						}
					}
				}),
				multiSelect : false,
				editable : false,
				name : 'userRoles',
				labelAlign : 'right',
				fieldLabel : '所属角色'
			}, {
				xtype : 'button',
				iconCls : 'search-icon',
				action : 'search',
				tooltip : '搜索'
			}, '->', {
				xtype : 'button',
				text : '刷新',
				iconCls : 'tab-refresh-icon',
				tooltip : '刷新当前视图<br>',
				handler : function(btn) {
					btn.up('grid').getStore().load();
				}
			}, '-', {
				iconCls : 'print-icon',
				tooltip : '打印当前表格<br>',
				handler : function(btn) {
					Ext.ux.grid.Printer.printLinkText = '打印';
					Ext.ux.grid.Printer.closeLinkText = '关闭';
					Ext.ux.grid.Printer.print(btn.up('grid'));
				}
			} ]
		},
		bbar : {
			xtype : 'pagingtoolbar',
			itemId : 'pagebar',
			store : 'users.UsersStore',
			width : '100%',
			cls : 'common-paging-toolbar',
			border : true,
			displayInfo : true
		}
	} ],
	initComponent : function() {
		this.items[0].header = false;
		this.callParent(arguments);
	}
});