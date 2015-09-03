Ext.define('portal.view.dimension.DimensionView', {
	extend : 'portal.view.panel.UpdatablePanel',
	alias : 'widget.dimensionView',
	requires : [ 'Ext.ux.grid.Printer' ],
	title : '维度信息设置',
	iconCls : 'dimension-icon',
	layout : {
		type : 'fit'
	},
	gridTitle : '维度信息设置',
	items : [ {
		xtype : 'grid',
		store : 'dimension.DimensionStore',
		selType : 'checkboxmodel',
		selModel : {
			mode : 'MULTI'
		},
		border : false,
		columnLines : true,
		columns : [ {
			text : '维度信息ID',
			dataIndex : 'id',
			hidden : true
		}, {
			text : '名称',
			dataIndex : 'name',
			cls : 'ux-grid-header',
			align : 'center',
			width : '10%'
		}, {
			text : '备注',
			dataIndex : 'memo',
			cls : 'ux-grid-header',
			align : 'center',
			flex : 1
		}, {
			text : '递归因子',
			dataIndex : 'type',
			cls : 'ux-grid-header',
			align : 'center',
			width : '10%',
			renderer : function(val) {
				if (val == 1) {
					return '是';
				} else {
					return '否';
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
				tooltip : '新建维度信息'
			}, {
				xtype : 'tbseparator',
				itemId : 'add_separator'
			}, {
				xtype : 'button',
				iconCls : 'tab-edit-icon',
				itemId : 'edit',
				text : '修改',
				tooltip : '修改选中的维度信息(s)'
			}, {
				xtype : 'tbseparator',
				itemId : 'edit_separator'
			}, {
				xtype : 'button',
				text : '删除',
				itemId : 'delete',
				iconCls : 'delete-icon',
				tooltip : '删除选中的维度信息(s)'
			}, {
				xtype : 'tbseparator',
				itemId : 'delete_separator'
			}, {
				xtype : 'textfield',
				name : 'name',
				labelAlign : 'right',
				fieldLabel : '名称'
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
			store : 'dimension.DimensionStore',
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