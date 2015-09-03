Ext.define('portal.view.systemconfig.SystemConfigView', {
	extend : 'portal.view.panel.UpdatablePanel',
	alias : 'widget.systemConfigView',
	requires : [ 'Ext.ux.grid.Printer' ],
	title : '系统配置管理',
	iconCls : 'assign-edit-icon',
	layout : {
		type : 'fit'
	},
	gridTitle : '系统配置管理',
	items : [ {
		xtype : 'grid',
		store : 'systemconfig.SystemConfigStore',
		selType : 'checkboxmodel',
		selModel : {
			checkSelector : 'td.x-grid-cell-row-checker'
		},
		border : false,
		columnLines : true,
		columns : [ {
			text : '系统配置ID',
			dataIndex : 'id'
		}, {
			text : '类型',
			cls : 'ux-grid-header',
			dataIndex : 'type',
			align : 'center',
			width : '5%'
		}, {
			text : '类型名称',
			cls : 'ux-grid-header',
			dataIndex : 'typeName',
			align : 'center',
			width : '10%'
		}, {
			text : '代码',
			cls : 'ux-grid-header',
			dataIndex : 'code',
			align : 'center',
			width : '5%'
		}, {
			text : '名称',
			cls : 'ux-grid-header',
			dataIndex : 'value',
			align : 'center',
			width : '10%'
		}, {
			text : '备注',
			cls : 'ux-grid-header',
			dataIndex : 'remark',
			align : 'center',
			flex : 1
		}, {
			text : '排序',
			cls : 'ux-grid-header',
			dataIndex : 'orderBy',
			align : 'center',
			width : '10%'
		}, {
			text : '状态',
			align : 'center',
			cls : 'ux-grid-header',
			dataIndex : 'enabled',
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
			cls : 'ux-grid-header',
			dataIndex : 'createTime',
			width : '12%',
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s'
		}, {
			text : '最后更新时间',
			cls : 'ux-grid-header',
			dataIndex : 'updateTime',
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
			} ],
			handler : function(grid, rindex, cindex) {
				var record = grid.getStore().getAt(rindex);
			}
		} ],
		tbar : {
			xtype : 'toolbar',
			items : [ {
				xtype : 'button',
				text : '新建',
				iconCls : 'tab-add-icon',
				itemId : 'add',
				tooltip : '新建系统配置'
			}, {
				xtype : 'tbseparator',
				itemId : 'add_separator'
			}, {
				xtype : 'button',
				iconCls : 'tab-edit-icon',
				itemId : 'edit',
				text : '修改',
				tooltip : '修改系统配置'
			}, {
				xtype : 'tbseparator',
				itemId : 'edit_separator'
			}, {
				xtype : 'button',
				text : '删除',
				itemId : 'remove',
				iconCls : 'delete-icon',
				tooltip : '删除选中的系统配置(s)'
			}, {
				xtype : 'tbseparator',
				itemId : 'remove_separator'
			}, {
				fieldLabel : '类型',
				xtype : 'numberfield',
				name : 'type',
				labelAlign : "right",
				minValue : 0
			}, {
				xtype : 'textfield',
				name : 'typeName',
				labelAlign : 'right',
				fieldLabel : '类型名称'
			}, {
				xtype : 'textfield',
				name : 'code',
				labelAlign : 'right',
				fieldLabel : '代码'
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
				tooltip : '打印当前表格',
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
			store : 'systemconfig.SystemConfigStore',
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