Ext.define('portal.view.TabController', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.tabcontroller',
	id : 'tabController',
	cls : 'portal-tab-panel',
	plugins : Ext.create('Ext.ux.TabCloseMenu', {
		closeTabText : '关闭当前页',
		closeOthersTabsText : '关闭其他页',
		closeAllTabsText : '关闭所有页',
		closeTabIconCls : 'tab-close',
		closeOthersTabsIconCls : 'tab-close-other',
		closeAllTabsIconCls : 'tab-close-all'
	}),
	initComponent : function() {
		this.callParent(arguments);
	}
});