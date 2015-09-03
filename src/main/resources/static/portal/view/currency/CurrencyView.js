Ext.define('portal.view.currency.CurrencyView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.currencyView',
	title : '货币代码表',
	layout : {
		type : 'fit'
	},
	items : [ {
		xtype : 'grid',
		columnLines : true,
		store : 'currency.CurrencyStore',
		columns : [ {
			text : '数字代码',
			dataIndex : 'digitalCode',
			width : '20%'
		}, {
			text : '字母代码',
			dataIndex : 'letterCode',
			width : '20%'
		}, {
			text : '货币名称',
			dataIndex : 'name',
			width : '60%'
		} ],
		tbar : {
			xtype : 'toolbar',
			cls : 'x-panel-header',
			border : false,
			items : [ {
				xtype : 'textfield',
				name : 'digitalCode',
				labelAlign : 'right',
				fieldLabel : '数字代码',
				emptyText : '数字代码'
			}, {
				xtype : 'textfield',
				name : 'letterCode',
				labelAlign : 'right',
				fieldLabel : '字母代码',
				emptyText : '字母代码'
			}, {
				xtype : 'textfield',
				name : 'name',
				labelAlign : 'right',
				fieldLabel : '货币名称',
				emptyText : '货币名称'
			}, {
				xtype : 'button',
				iconCls : 'search-icon',
				tooltip : '搜索'
			} ]
		},
		bbar : {
			xtype : 'pagingtoolbar',
			store : 'currency.CurrencyStore',
			dock : 'bottom',
			displayInfo : true
		}
	} ],

	initComponent : function() {
		this.callParent(arguments);
	}
});