Ext.define('portal.view.resources.ResourcesMenuView', {
	extend : 'Ext.menu.Menu',
	alias : 'widget.resourcesMenuView',
	floating : true,
	items : [ {
		iconCls : 'tab-add-icon',
		name : 'addNode',
		text : '添加子权限'
	}, {
		iconCls : 'tab-edit-icon',
		name : 'editNode',
		text : '编辑权限'
	}, {
		iconCls : 'tab-remove-icon',
		name : 'deleteNode',
		text : '删除权限'
	} ],
	initComponent : function() {
		this.callParent();
	}
});