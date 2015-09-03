Ext.define('portal.view.panel.UpdatablePanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.updatablepanel',
	autoScroll : true,
	layout : {
		type : 'vbox'
	},
	width : '100%',
	height : '100%',
	items : [ {
		selType : 'checkboxmodel',
		selModel : {
			mode : 'MULTI'
		},
		xtype : 'grid',
		margin : '3 3 3 3',
		width : '100%',
		flex : 1,
		columnLines : true,
		frame : true,
		bbar : {
			xtype : 'pagingtoolbar',
			displayInfo : true,
			displayMsg : '显示 {2} 条记录中的第 {0} 条到第 {1} 条',
			emptyMsg : "没有满足查询条件的记录"
		}
	} ],

	config : {
		gridTitle : ''
	},
	constructor : function(cfg) {
		this.callParent(arguments);
		this.initConfig(cfg);
	},
	
	initComponent : function() {
		Ext.apply(this.items[0], {
			title : this.gridTitle
		});
		this.callParent(arguments);
	},
	/**
	 * 判断2个string数组是否相同。
	 */
	stringsEquals : function(strs1, strs2) {
		if (strs1 && strs2) {
			if (strs1.length != strs2.length)
				return false;
			for (var i = 0; i < strs1.length; ++i) {
				if (strs1[i] != strs2[i])
					return false;
			}
			return true;
		} else if (strs1) {
			return false;
		} else if (strs2) {
			return false;
		} else
			return true;
	},

	setKvs : function(kvs) {
		var map = {};
		if (kvs) {
			for (var i = 0; i < kvs.length; ++i) {
				kvItem = kvs[i].split('=');
				if (kvItem.length > 1) {
					map[kvItem[0]] = kvItem[1];
				}
			}
		}
		if (!this.stringsEquals(kvs, this.kvs)) {
			this.kvs = kvs;
			this.map = map;
			this.fireEvent("kvsUpdated", this, map);
		}
	}
});