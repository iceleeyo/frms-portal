Ext.define('portal.view.regierungsbezirk.RegierungsbezirkView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.regierungsbezirkView',
	title : '行政区划分代码表',
	layout : {
		type : 'fit'
	},
	items : [ {
		xtype : 'grid',
		columnLines : true,
		store : 'regierungsbezirk.RegierungsbezirkStore',
		columns : [ {
			text : '地区代码',
			dataIndex : 'code',
			width : '50%'
		}, {
			text : '地区名称',
			dataIndex : 'name',
			width : '50%'
		}],
		tbar : {
			xtype : 'toolbar',
			cls : 'x-panel-header',
			border : false,
			items : [ {
				xtype : 'textfield',
				name : 'code',
				labelAlign : 'right',
				fieldLabel : '地区代码',
				emptyText : '地区代码'
			}, {
				xtype : 'textfield',
				name : 'name',
				labelAlign : 'right',
				fieldLabel : '地区名称',
				emptyText : '地区名称'
			}, {
				xtype : 'button',
				iconCls : 'search-icon',
				tooltip : '搜索'
			} ]
		},
		bbar : {
			xtype : 'pagingtoolbar',
			store : 'regierungsbezirk.RegierungsbezirkStore',
			dock : 'bottom',
			displayInfo : true
		}
	} ],

	initComponent : function() {
		this.callParent(arguments);
	}
});