Ext.define('portal.view.countries.CountriesView', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.countriesView',
	title : '国家地区代码表',
	layout : {
		type : 'fit'
	},
	items : [ {
		xtype : 'grid',
		columnLines : true,
		store : 'countries.CountriesStore',
		columns : [ {
			text : '数字代码',
			dataIndex : 'digitalCode',
			width : '10%'
		}, {
			text : '字母代码',
			dataIndex : 'letterCode',
			width : '10%'
		}, {
			text : '简称',
			dataIndex : 'forShort',
			width : '30%'
		}, {
			text : '全称',
			dataIndex : 'fullName',
			width : '50%'
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
				name : 'forShort',
				labelAlign : 'right',
				fieldLabel : '简称',
				emptyText : '简称'
			}, {
				xtype : 'textfield',
				name : 'fullName',
				labelAlign : 'right',
				fieldLabel : '全称',
				emptyText : '全称'
			}, {
				xtype : 'button',
				iconCls : 'search-icon',
				tooltip : '搜索'
			} ]
		},
		bbar : {
			xtype : 'pagingtoolbar',
			store : 'countries.CountriesStore',
			dock : 'bottom',
			displayInfo : true
		}
	} ],
	initComponent : function() {
		this.callParent(arguments);
	}
});