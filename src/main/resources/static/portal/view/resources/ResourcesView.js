Ext.define('portal.view.resources.ResourcesView', {
	extend : 'portal.view.panel.UpdatablePanel',
	alias : 'widget.resourcesView',
	title : '资源界面',
	columnLines : true,
	layout : 'border',
	autoScroll : true,
	scrollFlags : {
		both : true
	},
	initComponent : function() {
		this.items = [ {
			xtype : 'treepanel',
			store : 'resources.ResourcesStore',
			itemId : 'auth',
			rootVisible : false,
			useArrows : true,
			title : '权限管理',
			width : '30%',
			height : '100%',
			region : 'west'
		}, {
			xtype : 'tabpanel',
			frame : true,
			region : 'east',
			title : '权限说明',
			width : '70%',
			height : '100%',
			defaults : {
				bodyPadding : 10,
				autoScroll : true
			},
			items : [ {
				title : '功能模块说明',
				html : '功能模块说明'
			} ]
		} ];
		this.callParent();
	}
});