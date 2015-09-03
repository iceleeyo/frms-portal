Ext.define('portal.view.Navigator', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.navigator',
	requires : [ 'portal.view.ItemRenderer' ],
	iconCls : 'navigator-riskmgr',
	cls : 'common-navigator',
	layout : {
		type : 'accordion'
	},
	border : false,
	initComponent : function() {
		if ((typeof (portal.title) != "undefined") && !Ext.isEmpty(portal.title)) {
			this.title = portal.title + '相关操作';
		} else {
			this.title = '风控后台管理相关操作';
		}
		this.callParent(arguments);
	},
	onMouseLeaveFloated : function(e) {
		this.slideOutTask.delay(0);
	}
});